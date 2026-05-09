package com.yjotdev.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.SystemBarStyle
import androidx.core.view.WindowCompat
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.presentation.navigation.observeViewModelState
import com.yjotdev.login.presentation.navigation.setupAppNavigation
import com.yjotdev.login.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    internal val viewModel: UiViewModel by viewModels()
    internal lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajusta la vista a toda la pantalla
        viewEdgeToEdge()
        // Configura la IU de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Permisos de la app
        permissions()
        // Navagacion entre fragmentos
        setupAppNavigation()
        // Observacion de estados del ViewModel
        observeViewModelState()
        // Procesa el intent inicial
        intent.data?.let { uri -> handlePaypalIntent(uri) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Procesa el intent inicial
        intent.data?.let { uri -> handlePaypalIntent(uri) }
    }

    private fun viewEdgeToEdge(){
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )
        WindowCompat.getInsetsController(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    private fun permissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }

    private fun handlePaypalIntent(uri: Uri) {
        if (uri.scheme == "com.yjotdev.login" && uri.host == "paypal") {
            when (uri.path) {
                "/return" -> {
                    val orderId = uri.getQueryParameter("token")
                    if (!orderId.isNullOrEmpty()) {
                        viewModel.captureOrder(orderId)
                        viewModel.sendNotification()
                    }
                }
                "/cancel" -> {
                    val text = this.getString(R.string.toast_capture_order_error)
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}