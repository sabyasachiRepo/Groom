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
import edu.student.groom.onboarding.login.ui.LoginPage
import edu.student.groom.ui.theme.GroomTheme
import edu.student.groom.util.safeLet

class RegistrationActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ->
            GroomTheme() {
                RegistrationScreens()
            }
        }
    }
}

@Composable
fun RegistrationScreens() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "page_one") {
        composable("page_one") {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current

            showUIPageOne(focusManager, context, {
                navController.navigate("login_page")
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
                showUIPageTwo(
                    focusManager,
                    fName,
                    lName,
                    eMail
                ) {
                    navController.navigate("login_page")
                }
            }

        }

        composable("login_page") {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current
            LoginPage(focusManager, context) {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewPageOne() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    GroomTheme() {
        showUIPageOne(focusManager, context,{}) { _, _, _ ->

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPageTwo() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    GroomTheme() {
        showUIPageTwo(focusManager, "", "", "") {
        }
    }
}