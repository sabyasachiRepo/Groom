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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
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
                Toast.makeText(context, email, Toast.LENGTH_LONG).show()
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


@Composable
private fun showUIPageOne(
    focusManager: FocusManager,
    context: Context,
    continueClick: (email: String, firstname: String, lastName: String) -> Unit
) {
    var emailState by rememberSaveable { mutableStateOf("") }
    var firstNameState by rememberSaveable { mutableStateOf("") }
    var lastNameState by rememberSaveable { mutableStateOf("") }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = emailState,
                label = { Text(text = "Email", style = MaterialTheme.typography.subtitle1) },
                onValueChange = {
                    emailState = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon"
                    )
                }
            )

            OutlinedTextField(
                value = firstNameState,
                label = { Text(text = "First name", style = MaterialTheme.typography.subtitle1) },
                onValueChange = {
                    firstNameState = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "emailIcon"
                    )
                }
            )

            OutlinedTextField(
                value = lastNameState,
                label = { Text(text = "Last name", style = MaterialTheme.typography.subtitle1) },
                onValueChange = {
                    lastNameState = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "emailIcon"
                    )
                }
            )

            Text(
                text = "By signing up,you're agree to our Terms and Conditions and Privacy Policy",
                modifier = Modifier.padding(top = 24.dp),
                color = Color.Gray
            )

            Button(
                onClick = { continueClick(emailState, firstNameState, lastNameState) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orange)
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }
        }


    }
}

@Composable
private fun showUIPageTwo(
    focusManager: FocusManager,
    context: Context,
    singUpClick: (password: String, confirmPassword: String) -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    val institutes:MutableState<List<Responses.Institute>> = remember {
        mutableStateOf(emptyList())
    }
    val viewModel: SignupViewModel = viewModel()
    viewModel.getInstitutes{successResponse ->
        val instituteList= successResponse?.institutes
        institutes.value=instituteList.orEmpty()
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


            InstituteSelectionDropdown(institutes.value)

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
fun InstituteSelectionDropdown(instituteList:List<Responses.Institute>) {
    var expanded by remember { mutableStateOf(false) }


    var buttonName by remember {
        mutableStateOf("Select Institute")
    }
    Box() {
        Button(modifier = Modifier.fillMaxWidth(), onClick = { expanded = !expanded }) {
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
                    buttonName = institute.name
                    //do something ...
                }) {
                    Text(text = institute.name)
                }
            }
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