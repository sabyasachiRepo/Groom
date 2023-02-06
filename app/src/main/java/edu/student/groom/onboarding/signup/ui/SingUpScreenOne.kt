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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun showUIPageOne(
    focusManager: FocusManager,
    context: Context,
    continueClick: (email: String, firstname: String, lastName: String) -> Unit
) {
    var emailState by rememberSaveable { mutableStateOf("") }
    var firstNameState by rememberSaveable { mutableStateOf("") }
    var lastNameState by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isFirstNameError by rememberSaveable { mutableStateOf(false) }
    var isLastNameError by rememberSaveable { mutableStateOf(false) }
    fun  validateEmail():Boolean {
        isEmailError =emailState.isEmpty()
        return !isEmailError
    }
    fun validateFirstName():Boolean {
        isFirstNameError =firstNameState.isEmpty()
        return !isFirstNameError;
    }
    fun validateLastName():Boolean {
        isLastNameError =lastNameState.isEmpty()
        return !isLastNameError;
    }

    fun validatePageOne():Boolean{
        return validateEmail() && validateFirstName() &&  validateLastName()
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
            text = "Sign Up",
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
            label = { Text(text = "First name", style = MaterialTheme.typography.subtitle1) },
            onValueChange = {
                firstNameState = it
                validateFirstName()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "First name icon"
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            },
            isError = isFirstNameError
        )
        if (isFirstNameError) {
            Text(
                text = "First name can not be empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
        OutlinedTextField(
            value = lastNameState,
            label = { Text(text = "Last name", style = MaterialTheme.typography.subtitle1) },
            onValueChange = {
                lastNameState = it
                validateLastName()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "emailIcon"
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            isError = isLastNameError
        )
        if (isLastNameError) {
            Text(
                text = "Last name can not be empty",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 14.dp)
            )
        }

        Text(
            text = "By signing up,you're agree to our Terms and Conditions and Privacy Policy",
            modifier = Modifier.padding(top = 24.dp),
            color = Color.Gray
        )

        Button(
            onClick = {
                if(validatePageOne()){
                    continueClick(emailState, firstNameState, lastNameState)
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
                text = "Continue",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun RegistrationLottieAnimation(isPlaying: Boolean) {
    var isLottiePlaying by remember {
        mutableStateOf(isPlaying)
    }
    var animationSpeed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.happy_coding)
    )

    // to control the lottie animation
    val lottieAnimation by animateLottieCompositionAsState(
        // pass the composition created above
        composition,
        // Iterates Forever

        // Lottie and pause/play
        isPlaying = isLottiePlaying,
        // Increasing the speed of change Lottie
        speed = animationSpeed,
        restartOnPlay = false
    )
    if (isPlaying) {
        LottieAnimation(composition, progress = lottieAnimation)
    } else {
        LottieAnimation(composition, progress = 0.5F)
    }

}