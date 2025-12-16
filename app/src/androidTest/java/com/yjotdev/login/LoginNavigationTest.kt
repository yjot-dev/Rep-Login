package com.yjotdev.login

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginNavigationTest {

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
    fun testFullUserFlow() {
        // --- 1. PANTALLA LOGIN ---
        // Verificamos estar en Login
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))

        // Intentamos ir a registrar un nuevo usuario
        onView(withId(R.id.registerFragment)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // --- 2. PANTALLA REGISTER ---
        // Verificamos estar en Register
        onView(withId(R.id.btnRegister)).check(matches(isDisplayed()))

        // Llenamos datos de registro
        onView(withId(R.id.inputName))
            .perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail))
            .perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword))
            .perform(typeText("123456"), closeSoftKeyboard())

        // Clic en registrar
        onView(withId(R.id.btnRegister)).perform(click())

        // Volver al login desde register
        onView(withId(R.id.loginFragment)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // --- 3. PANTALLA LOGIN (De vuelta) ---
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))

        // Ingresamos credenciales correctas
        onView(withId(R.id.inputName))
            .perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword))
            .perform(typeText("123456"), closeSoftKeyboard())

        // Iniciar sesión
        onView(withId(R.id.btnLogin)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // --- 4. PANTALLA USER (Perfil) ---
        // Verificamos estar en UserFragment (por ejemplo viendo el botón de actualizar)
        onView(withId(R.id.btnUpdate)).check(matches(isDisplayed()))

        // Validamos que los datos se cargaron (el nombre debe coincidir)
        onView(withId(R.id.inputName)).check(matches(withText("Test User")))

        // Modificamos el nombre
        onView(withId(R.id.inputName))
            .perform(replaceText("Updated User"), closeSoftKeyboard())

        // Actualizamos
        onView(withId(R.id.btnUpdate)).perform(click())

        // Verificamos Toast de éxito (Opcional, es difícil capturar Toasts en API 30+,
        // pero verificamos que seguimos en la pantalla y el texto cambió)
        onView(withId(R.id.inputName)).check(matches(withText("Updated User")))

        // --- 5. LOGOUT ---
        onView(withId(R.id.btnLogout)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // Debemos haber vuelto al Login
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))

        // Intentamos ir a recuperar la cuenta del usuario
        onView(withId(R.id.recoveryFragment)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // --- 6. PANTALLA RECOVERY ---
        // Verificamos estar en Recovery
        onView(withId(R.id.btnCode)).check(matches(isDisplayed()))

        // Paso 1: Simular envío de código
        // Escribimos el correo y solicitamos código
        onView(withId(R.id.inputEmail))
            .perform(replaceText("test@example.com"), closeSoftKeyboard())

        // Clic en enviar codigo
        onView(withId(R.id.btnCode)).perform(click())

        // Paso 2: Simula envío de la nueva clave para recuperar la cuenta
        // Actualizamos randomCode con el codigo de prueba
        activityRule.scenario.onActivity { activity ->
            val viewModel = ViewModelProvider(activity)[UiViewModel::class.java]
            viewModel.setRandomCode(1001)
        }

        // Escribimos código, correo y nueva contraseña
        onView(withId(R.id.inputCode))
            .perform(replaceText("1001"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail))
            .perform(replaceText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword))
            .perform(replaceText("654321"), closeSoftKeyboard())

        // Clic en recuperar cuenta
        onView(withId(R.id.btnRecovery)).perform(click())

        // Volver al login desde recovery
        onView(withId(R.id.loginFragment)).perform(click())

        // Esperamos un momento a que la navegación ocurra
        Thread.sleep(1000)

        // --- 7. PANTALLA LOGIN (De vuelta) ---
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }
}