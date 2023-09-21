package edu.student.groom.onboarding.login.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import edu.student.groom.onboarding.login.ui.LoginPage


const val loginRoute = "login_route"

fun NavController.navigateToLogin(clearBackStack: Boolean = false) {
    if (clearBackStack) {
        this.popBackStack(graph.startDestinationId, true)
    }
    this.navigate(loginRoute)
}

fun NavGraphBuilder.loginScreen(onLoginSuccess: () -> Unit) {
    composable(route = loginRoute) {
        LoginPage(onLoginSuccess)
    }
}

