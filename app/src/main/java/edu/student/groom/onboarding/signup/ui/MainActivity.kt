package edu.student.groom.onboarding.signup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.student.groom.home.homeScreen
import edu.student.groom.home.navigateToHomeScreen
import edu.student.groom.onboarding.login.ui.LoginPage
import edu.student.groom.onboarding.login.ui.navigation.loginScreen
import edu.student.groom.onboarding.login.ui.navigation.navigateToLogin
import edu.student.groom.ui.theme.GroomTheme
import edu.student.groom.util.safeLet


class RegistrationActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ->
            GroomTheme() {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = singUpRoute) {

        signUpScreen({ shouldClearBackStack -> navController.navigateToLogin(shouldClearBackStack) }) { eMail,fName, lName ->
            navController.navigateToSingUpScreenPageTwo(eMail,fName, lName)
        }
        loginScreen {
            navController.navigateToHomeScreen()
        }
        homeScreen()

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewPageOne() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    GroomTheme() {
        ShowUIPageOne(focusManager, {}) { _, _, _ ->

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPageTwo() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    GroomTheme() {
        LoginPage {
        }
    }
}