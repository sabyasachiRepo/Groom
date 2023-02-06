package edu.student.groom.onboarding.signup.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import edu.student.groom.R
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.ui.theme.GroomTheme
import edu.student.groom.ui.theme.orange

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

            showUIPageOne(focusManager, context) { email, firstName, lastName ->

                navController.navigate("page_two")

            }
        }
        composable("page_two") {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current

            showUIPageTwo(focusManager, context) { password, confirmPassword ->
                Toast.makeText(context, password, Toast.LENGTH_LONG).show()
                //emailState.text="fdf"

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
        showUIPageOne(focusManager, context) { _, _, _ ->

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewPageTwo() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    GroomTheme() {
        showUIPageTwo(focusManager, context) { _, _ ->
        }
    }
}