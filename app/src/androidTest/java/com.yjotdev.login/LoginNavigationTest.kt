package com.yjotdev.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
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
    fun testLoginToDashboardAndOthersViews() {
        // --- 1. PANTALLA LOGIN ---
        // Verificamos estar en Login
        onView(withId(R.id.tvTitleLogin)).check(matches(isDisplayed()))

        // Ingresamos los datos necesarios
        onView(withId(R.id.inputNameOrEmail))
            .perform(typeText("Invitado"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword))
            .perform(typeText("Invitado1000"), closeSoftKeyboard())

        // Iniciamos sesion
        onView(withId(R.id.btnLogin)).perform(click())

        // --- 2. PANTALLA DASHBOARD ---
        // Verificamos estar en Dashboard
        onView(withId(R.id.tvTitleDashboard)).check(matches(isDisplayed()))

        // Navegamos mediante el menu inferior a la vista User
        onView(withId(R.id.navigationUser)).perform(click())

        // --- 3. PANTALLA USUARIO ---
        // Verificamos estar en User
        onView(withId(R.id.tvTitleUser)).check(matches(isDisplayed()))

        // Navegamos mediante el menu inferior a la vista Payments
        onView(withId(R.id.navigationPayments)).perform(click())

        // --- 4. PANTALLA PAGOS ---
        // Verificamos estar en Payments
        onView(withId(R.id.tvTitlePayments)).check(matches(isDisplayed()))

        // Navegamos mediante el menu inferior a la vista PaymentsHistory
        onView(withId(R.id.navigationPaymentsHistory)).perform(click())

        // --- 5. PANTALLA HISTORIAL DE PAGOS ---
        // Verificamos estar en PaymentsHistory
        onView(withId(R.id.tvTitlePaymentsHistory)).check(matches(isDisplayed()))

        // Navegamos mediante el menu inferior a la vista Notifications
        onView(withId(R.id.navigationNotifications)).perform(click())

        // --- 6. PANTALLA NOTIFICACIONES ---
        // Verificamos estar en Notifications
        onView(withId(R.id.tvTitleNotifications)).check(matches(isDisplayed()))

        // Hace click en el recyclerview de Notifications
        onView(withId(R.id.rvNotifications)).perform(click())
    }
}