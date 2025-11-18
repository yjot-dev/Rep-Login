package com.yjotdev.login

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.graphics.Color
import android.os.Build
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.core.view.WindowCompat
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.ActivityMainBinding
import com.yjotdev.login.application.mvvm.view.MenuFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajusta la vista a toda la pantalla
        viewEdgeToEdge()
        // Configura la IU de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Si es la primera vez que se crea, muestra el MenuFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentMenu, MenuFragment())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasPermissions(this)) {
            requirePermission()
        }
    }

    fun replaceMenuWith(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentMenu, fragment)
            .commit()
    }

    private fun requirePermission(): Boolean{
        if (PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)){
            Toast.makeText(this, "PERMISO DE INTERNET YA OTORGADO", Toast.LENGTH_SHORT).show()
        }else{
            requestPermissionLauncher.launch(Manifest.permission.INTERNET)
        }
        if (PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)){
            Toast.makeText(this, "PERMISO DE ESTADO DE RED YA OTORGADO", Toast.LENGTH_SHORT).show()
        }else{
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_NETWORK_STATE)
        }
        if (PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)){
            Toast.makeText(this, "PERMISO DE ENVIAR SMS YA OTORGADO", Toast.LENGTH_SHORT).show()
        }else{
            requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
        }
        return true
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "PERMISO REQUERIDO OTORGADO", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "PERMISO REQUERIDO DENEGADO", Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        private val PERMISSIONS_REQUIRED = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.SEND_SMS)
        //Verifica si todos los permisos necesarios estan permitidos en esta app
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
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
}