package edu.student.groom.onboarding.login.ui

import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.student.groom.R
import edu.student.groom.letters
import edu.student.groom.onboarding.login.model.LoginResponse
import edu.student.groom.ui.theme.GroomTheme
import edu.student.groom.ui.theme.orange
import edu.student.groom.util.*
import timber.log.Timber

@Composable
fun LoginPage(
    onRegisterClick: ()-> Unit,
    onLoginSuccess: () -> Unit
) {
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isInProgress by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val viewModel: LoginViewModel = hiltViewModel()

    val uiState by viewModel.loginResponseFlow.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        uiState?.let {
            when (it) {
                UiState.Loading -> {
                    isInProgress = true
                }

                is UiState.Success<LoginResponse> -> {
                    isInProgress = false
                    onLoginSuccess()
                    Timber.d("Login:", "Login success")
                }

                is UiState.Error -> {
                    isInProgress = false
                    errorMessage = it.message
                    openDialog = true
                }
            }
        }

    }


    /*
       Flag to restrict the dynamic validation logic when user starts typing works immediately
       It should work only work after the user click the CTA button for once.
     */
    var isFirstInteraction by remember { mutableStateOf(true) }

    fun validateEmail(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isEmailError = emailState.isEmpty() || !isValidEmailAddress(emailState)
        return !isEmailError
    }

    fun validatePassword(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isPasswordError = passwordState.isEmpty()
        return !isPasswordError;
    }


    fun validateLoginPage(): Boolean {
        return validateEmail() && validatePassword()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {


        if (openDialog) {
            GroomErrorAlertDialog(errorMessage) {
                openDialog = false
            }
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

                GroomTextField(
                    hint = "Email",
                    state = emailState,
                    isError = isEmailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    onStateChange = { emailState = it }) {
                    validateEmail()
                }
                if (isEmailError) {
                    GroomTextFieldError(errorMessage = stringResource(R.string.email_error_message))
                }
            }



            GroomTextField(
                hint = "Password",
                state = passwordState,
                isError = isPasswordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                onStateChange = { passwordState = it }) {
                validatePassword()
            }
            if (isPasswordError) {
                GroomTextFieldError(errorMessage = stringResource(R.string.password_error_message))
            }



            Button(
                onClick = {
                    isFirstInteraction = false;
                    if (validateLoginPage()) {
                        focusManager.clearFocus()
                        viewModel.login(emailState, passwordState)
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
            Box(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            ) {
                AnnotatedClickableTextRegister() {
                    onRegisterClick()
                }
            }

        }
        if (isInProgress) {
            GroomCircularProgressBar()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewLoginPage() {

    GroomTheme() {
        LoginPage({}) {
        }
    }
}