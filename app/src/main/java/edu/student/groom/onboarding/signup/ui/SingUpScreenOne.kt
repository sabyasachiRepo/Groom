package edu.student.groom.onboarding.signup.ui

import android.graphics.drawable.Icon
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.student.groom.R
import edu.student.groom.letters
import edu.student.groom.ui.theme.orange
import edu.student.groom.util.AnnotatedClickableTextLogin
import edu.student.groom.util.AnnotatedClickableTextsPrivacyAndTermsAncCondition
import edu.student.groom.util.RegistrationLottieAnimation
import edu.student.groom.util.isValidEmailAddress

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowUIPageOne(
    focusManager: FocusManager,
    onLoginLinkClick: () -> Unit,
    continueClick: (email: String, firstname: String, lastName: String) -> Unit
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
        return !isFirstNameError
    }

    fun validateLastName(): Boolean {
        if (isFirstInteraction) {
            return true
        }
        isLastNameError = lastNameState.isEmpty()
        return !isLastNameError
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
            text = stringResource(R.string.sign_up),
            modifier = Modifier.padding(bottom = 24.dp),
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .wrapContentSize(),

            ) {
            /*      OutlinedTextField(
                      value = emailState,
                      label = { Text(text = stringResource(R.string.email), style = MaterialTheme.typography.subtitle1) },
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
                  )*/
           /* GroomTextField(
                state = emailState,
                isError = isEmailError,
                onStateChange = { emailState = it }) {
                validateEmail()
            }*/

            if (isEmailError) {
                Text(
                    text = stringResource(R.string.email_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = 14.dp)
                )
            }
        }

        OutlinedTextField(
            value = firstNameState,
            label = {
                Text(
                    text = stringResource(R.string.first_name),
                    style = MaterialTheme.typography.subtitle1
                )
            },
            onValueChange = {
                firstNameState = it.letters()
                validateFirstName()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(R.string.first_name_icon_desc)
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            isError = isFirstNameError
        )
        if (isFirstNameError) {
            Text(
                text = stringResource(R.string.first_name_error_message),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
        OutlinedTextField(
            value = lastNameState,
            label = {
                Text(
                    text = stringResource(R.string.last_name),
                    style = MaterialTheme.typography.subtitle1
                )
            },
            onValueChange = {
                lastNameState = it.letters()
                validateLastName()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "emailIcon"
                )
            },
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
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
                text = stringResource(R.string.last_name_error_message),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 14.dp)
            )
        }

        Box(
            Modifier
                .align(CenterHorizontally)
                .padding(top = 24.dp)
        ) {
            AnnotatedClickableTextsPrivacyAndTermsAncCondition({}, {})
        }

        Button(
            onClick = {
                isFirstInteraction = false
                if (validatePageOne()) {
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
                text = stringResource(R.string.continue_text),
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
        Box(
            Modifier
                .align(CenterHorizontally)
                .padding(top = 15.dp)
        ) {
            AnnotatedClickableTextLogin() {
                onLoginLinkClick()
            }
        }

    }
}

@Composable
private fun GroomTextField(
    state: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions,
    leadingIcon: Icon,
    onStateChange: (String) -> Unit,
    validation: () -> Boolean
) {
    state
    OutlinedTextField(
        value = state,
        label = {
            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.subtitle1
            )
        },
        onValueChange = {
            onStateChange(it)
            validation()
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
        isError = isError,
    )
}