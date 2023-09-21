package edu.student.groom.onboarding.signup.ui

import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import edu.student.groom.util.safeLet

const val singUpRoute = "signup_route"
const val singUpRoutePageOne = "signup_route_page_one"
const val pageTwo = "signup_route_page_two"
const val firstNameArg="firstName"
const val lastNameArg="lastName"
const val emailArg="email"
const val singUpRoutePageTwo = "$pageTwo/{$firstNameArg}/{$lastNameArg}/{$emailArg}"

fun NavController.navigateToSingUpScreenPageOne() {
    this.navigate(singUpRoutePageOne)
}

fun NavController.navigateToSingUpScreenPageTwo(firstName: String, lastName: String,email: String) {
    this.navigate("$pageTwo/$firstName/$lastName/$email")
}

fun NavGraphBuilder.signUpScreen(
    goToLoginScreenOptionalBackStack: (Boolean) -> Unit,
    continueClick: (email: String, firstname: String, lastName: String) -> Unit
) {

    navigation(startDestination = singUpRoutePageOne, route = singUpRoute) {
        composable(route = singUpRoutePageOne) {

            ShowUIPageOne(LocalFocusManager.current, {
                goToLoginScreenOptionalBackStack(false)
            }) { email, firstName, lastName ->
                continueClick(email,firstName,lastName)

            }
        }
        composable(route = singUpRoutePageTwo,arguments = listOf(navArgument(firstNameArg) {
            type = NavType.StringType
        }, navArgument(lastNameArg) {
            type = NavType.StringType
        }, navArgument(emailArg) {
            type = NavType.StringType
        })) {
            val firstName = it.arguments?.getString(
                firstNameArg)
            val lastName = it.arguments?.getString(lastNameArg)
            val email = it.arguments?.getString(emailArg)
            safeLet(firstName, lastName, email) { fName, lName, eMail ->
                ShowUIPageTwo(
                    fName,
                    lName,
                    eMail
                ) {
                    goToLoginScreenOptionalBackStack(true)
                }
            }
        }
    }

}
