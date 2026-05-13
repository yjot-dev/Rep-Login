package com.yjotdev.login

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RecoveryProcessTest {

    // 1. Regla de Hilt (Orden 0: se ejecuta primero para inyectar)
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // 2. Regla de Activity (Orden 1: lanza la activity después de configurar Hilt)
    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testRecoveryUser() {
        // --- 1. PANTALLA LOGIN ---
        // Verificamos estar en Login
        onView(withId(R.id.tvTitleLogin)).check(matches(isDisplayed()))

        // Navegamos mediante el menu inferior a la vista Recovery
        onView(withId(R.id.navigationRecovery)).perform(click())

        // --- 2. PANTALLA RECUPERAR ---
        // Verificamos estar en Recovery
        onView(withId(R.id.tvTitleRecovery)).check(matches(isDisplayed()))

        // Ingresamos los datos necesarios
        onView(withId(R.id.inputEmail))
            .perform(replaceText("test1@example.com"))
        onView(withId(R.id.inputPassword))
            .perform(replaceText("Test1000"))

        // Enviar codigo al email (fake) y mostrar AlertDialog
        onView(withId(R.id.btnSendCode)).perform(click())

        // Verificamos que el AlertDialog este visible
        onView(withId(R.id.inputCode)).check(matches(isDisplayed()))

        // Simula el codigo enviado
        activityRule.scenario.onActivity { activity ->
            val viewModel = ViewModelProvider(activity)[UiViewModel::class.java]
            viewModel.setRandomCode(7346)
        }

        // Ingresamos el codigo en el AlertDialog
        onView(withId(R.id.inputCode))
            .perform(replaceText("7346"))

        // Validamos el codigo
        onView(withId(R.id.btnValidate)).perform(click())

        // Verificamos que el AlertDialog este oculto
        onView(withId(R.id.inputCode)).check(doesNotExist())

        // Recuperar clave
        onView(withId(R.id.btnRecovery)).perform(click())
    }
}