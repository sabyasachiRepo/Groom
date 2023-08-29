package edu.student.groom.onboarding.signup.ui

import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.student.groom.R
import edu.student.groom.onboarding.signup.model.Requests
import edu.student.groom.ui.theme.orange
import edu.student.groom.util.*
import timber.log.Timber


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowUIPageTwo(
    firstName: String,
    lastName: String,
    email: String,
    signUpSuccess: () -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isInstituteError by rememberSaveable { mutableStateOf(false) }
    var isMobileNumberError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordError by rememberSaveable { mutableStateOf(false) }
    var isInProgress by rememberSaveable { mutableStateOf(false) }
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    /*
       Flag to restrict the dynamic validation logic when user starts typing works immediately
       It should only work after the user click the CTA button for once.
     */
    var isFirstInteraction by rememberSaveable { mutableStateOf(true) }

    val viewModel: SignupViewModel = viewModel()
    val scrollState = rememberScrollState()
    val state: UiState<String>? by viewModel.singUpResponse.collectAsStateWithLifecycle()
    LaunchedEffect(state) {
        state?.let {
            when (it) {
                UiState.Loading -> {
                    isInProgress = true
                }
                is UiState.Success<String> -> {
                    isInProgress = false
                    signUpSuccess()
                    Timber.d("SignUp:", "SignUp success")
                }
                is UiState.Error -> {
                    isInProgress = false
                    errorMessage = it.message
                    openDialog = true
                }
            }
        }

    }



    fun validateInstitute(): Boolean {
        if (isFirstInteraction) {
            return true;
        }
        isInstituteError = viewModel.selectedInstitute == null
        return !isInstituteError
    }

    fun validateMobileNumber(): Boolean {
        if (isFirstInteraction) {
            return true;
        }
        isMobileNumberError = mobileNumber.trim().length != 10
        return !isMobileNumberError;
    }

    fun validatePassword(): Boolean {
        if (isFirstInteraction) {
            return true;
        }
        isPasswordError = password.isEmpty()
        return !isPasswordError;
    }

    fun validateConfirmPassword(): Boolean {
        if (isFirstInteraction) {
            return true;
        }
        isConfirmPasswordError = confirmPassword != password
        return !isConfirmPasswordError;
    }

    fun validatePageTwo(): Boolean {
        return validateInstitute() && validateMobileNumber() && validatePassword() && validateConfirmPassword()
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
                .padding(24.dp, 24.dp, 24.dp, 24.dp)
                .verticalScroll(scrollState)
        )

        {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)

            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    RegistrationLottieAnimation(false)
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
                    .wrapContentSize()
            ) {


                GroomInstituteSelectionDropdown(
                    viewModel.institutes.value,
                    viewModel.dropDownButtonName.value
                ) {
                    viewModel.selectedInstitute = it
                    validateInstitute()
                }
                if (isInstituteError) {
                    Text(
                        text = stringResource(R.string.please_select_institute),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }
                OutlinedTextField(
                    value = mobileNumber,
                    label = {
                        Text(
                            text = stringResource(R.string.mobile_number),
                            style = MaterialTheme.typography.subtitle1
                        )
                    },
                    onValueChange = {
                        mobileNumber = it
                        validateMobileNumber()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.phone_icon)
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                    isError = isMobileNumberError
                )
                if (isMobileNumberError) {
                    Text(
                        text = stringResource(R.string.please_enter_valid_mobile_number),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }
                OutlinedTextField(
                    value = password,
                    label = { Text(text = "Password", style = MaterialTheme.typography.subtitle1) },
                    onValueChange = {
                        password = it
                        validatePassword()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.password_icon)
                        )
                    },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    isError = isPasswordError

                )
                if (isPasswordError) {
                    Text(
                        text = stringResource(R.string.please_set_password),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }
                OutlinedTextField(
                    value = confirmPassword,
                    label = {
                        Text(
                            text = stringResource(R.string.confirm_password),
                            style = MaterialTheme.typography.subtitle1
                        )
                    },
                    onValueChange = {
                        confirmPassword = it
                        validateConfirmPassword()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.confirm_password_icon)
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    isError = isConfirmPasswordError
                )

                if (isConfirmPasswordError) {
                    Text(
                        text = stringResource(R.string.password_and_confirm_password_are_not_same),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }

                Text(
                    text = stringResource(R.string.terms_and_conditions_and_privacy_policy),
                    modifier = Modifier.padding(top = 24.dp),
                    color = Color.Gray
                )

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        isFirstInteraction = false
                        if (validatePageTwo()) {
                            val signUpRequest: Requests.SignUpRequest = Requests.SignUpRequest(
                                firstName,
                                lastName,
                                email,
                                viewModel.selectedInstitute!!.id,
                                mobileNumber,
                                password
                            )
                            viewModel.signUp(signUpRequest)
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 24.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = orange)
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }


            }


        }
        if (isInProgress) {
            GroomCircularProgressBar()
        }
    }

}






