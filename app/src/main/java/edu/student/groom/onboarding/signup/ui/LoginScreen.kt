package edu.student.groom.onboarding.signup.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import edu.student.groom.R
import edu.student.groom.ui.theme.orange
import edu.student.groom.util.RegistrationLottieAnimation
import edu.student.groom.util.isValidEmailAddress

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(
    focusManager: FocusManager,
    context: Context,
    continueClick: () -> Unit
) {
    var emailState by rememberSaveable { mutableStateOf("") }
    var firstNameState by rememberSaveable { mutableStateOf("") }
    var lastNameState by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isFirstNameError by rememberSaveable { mutableStateOf(false) }
    var isLastNameError by rememberSaveable { mutableStateOf(false) }

    /*
       Flag to restrict the dynamic validation logic when user starts typing works immediately
       It should work only work after the user click the CTA button for once.
     */
    var isFirstInteraction by rememberSaveable { mutableStateOf(true) }

    fun validateEmail(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isEmailError = emailState.isEmpty() || !isValidEmailAddress(emailState)
        return !isEmailError
    }

    fun validateFirstName(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isFirstNameError = firstNameState.isEmpty()
        return !isFirstNameError;
    }

    fun validateLastName(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isLastNameError = lastNameState.isEmpty()
        return !isLastNameError;
    }

    fun validatePageOne(): Boolean {
        return validateEmail() && validateFirstName() && validateLastName()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .padding(24.dp, 24.dp, 24.dp, 24.dp),
    )

    {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                RegistrationLottieAnimation(true)
            }
        }

        Text(
            text = "Login",
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .wrapContentSize(),

            ) {
            OutlinedTextField(
                value = emailState,
                label = { Text(text = "Email", style = MaterialTheme.typography.subtitle1) },
                onValueChange = {
                    emailState = it
                    validateEmail()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon"
                    )
                },
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                },
                isError = isEmailError,
            )
            if (isEmailError) {
                Text(
                    text = "Please provide valid email",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = 14.dp)
                )
            }
        }

        OutlinedTextField(
            value = firstNameState,
            label = { Text(text = "Password", style = MaterialTheme.typography.subtitle1) },
            onValueChange = {
                firstNameState = it
                validateFirstName()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Password"
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            },
            isError = isFirstNameError
        )
        if (isFirstNameError) {
            Text(
                text = "Password not be empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
       

        Text(
            text = "By log in,you're agree to our Terms and Conditions and Privacy Policy",
            modifier = Modifier.padding(top = 24.dp),
            color = Color.Gray
        )

        Button(
            onClick = {
                isFirstInteraction = false;
                if (validatePageOne()) {
                    continueClick()
                }


            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = orange),
            enabled = true
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}


