package edu.student.groom.onboarding.signup.ui

import android.content.Context
import android.os.Build
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.student.groom.onboarding.signup.model.Responses
import edu.student.groom.ui.theme.orange

@Composable
 fun showUIPageTwo(
    focusManager: FocusManager,
    context: Context,
    singUpClick: (password: String, confirmPassword: String) -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }

    val viewModel: SignupViewModel = viewModel()

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
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            InstituteSelectionDropdown(
                viewModel.institutes.value,
                viewModel.dropDownButtonName.value
            ) {
                viewModel.selectedInstitute = it
            }

            OutlinedTextField(
                value = mobileNumber,
                label = {
                    Text(
                        text = "Mobile Number",
                        style = MaterialTheme.typography.subtitle1
                    )
                },
                onValueChange = {
                    mobileNumber = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "phone icon"
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = password,
                label = { Text(text = "Password", style = MaterialTheme.typography.subtitle1) },
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "emailIcon"
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )

            OutlinedTextField(
                value = confirmPassword,
                label = {
                    Text(
                        text = "Confirm Password",
                        style = MaterialTheme.typography.subtitle1
                    )
                },
                onValueChange = {
                    confirmPassword = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "emailIcon"
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )


            Text(
                text = "By signing up,you're agree to our Terms and Conditions and Privacy Policy",
                modifier = Modifier.padding(top = 24.dp),
                color = Color.Gray
            )

            Button(
                onClick = { singUpClick(password, confirmPassword) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(top = 24.dp)
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
}

@Composable
fun InstituteSelectionDropdown(
    instituteList: List<Responses.Institute>,
    buttonName: String,
    onInstituteSelected: (Responses.Institute) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    Box() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = !expanded },
            enabled = instituteList.isNotEmpty()
        ) {
            Text(buttonName)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            instituteList.forEach { institute ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onInstituteSelected(institute)
                    //do something ...
                }) {
                    Text(text = institute.name)
                }
            }
        }
    }

}

