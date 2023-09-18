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
       /* composable("page_one") {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current

            ShowUIPageOne(focusManager, {
                navController.navigateToLogin()
            }) { email, firstName, lastName ->
                navController.navigate("page_two/$firstName/$lastName/$email")
            }
        }
        composable(
            "page_two/{firstName}/{lastName}/{email}",
            arguments = listOf(navArgument("firstName") {
                type = NavType.StringType
            }, navArgument("lastName") {
                type = NavType.StringType
            }, navArgument("email") {
                type = NavType.StringType
            })
        ) {
            val focusManager = LocalFocusManager.current
            val firstName = it.arguments?.getString("firstName")
            val lastName = it.arguments?.getString("lastName")
            val email = it.arguments?.getString("email")
            safeLet(firstName, lastName, email) { fName, lName, eMail ->
                ShowUIPageTwo(
                    fName,
                    lName,
                    eMail
                ) {
                    navController.navigateToLogin()
                }
            }
        }*/

        signUpScreen({navController.navigateToLogin()}){fName, lName, eMail ->
            navController.navigateToSingUpScreenPageTwo(fName,lName,eMail)
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