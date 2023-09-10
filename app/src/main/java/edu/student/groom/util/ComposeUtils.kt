package edu.student.groom.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import edu.student.groom.R
import edu.student.groom.onboarding.signup.model.Responses
import timber.log.Timber

@Composable
fun GroomCircularProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, // disable ripple effect
                interactionSource = remember { MutableInteractionSource() },
                onClick = { }
            )
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun GroomErrorAlertDialog(message: String, onDismiss: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Error!")
        },
        text = {
            Text(
                message
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Ok")
                }
            }
        }
    )
}


@Composable
fun GroomInstituteSelectionDropdown(
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

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun RegistrationLottieAnimation(isPlaying: Boolean) {
    val isLottiePlaying by remember {
        mutableStateOf(isPlaying)
    }
    val animationSpeed by remember {
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

@Composable
fun AnnotatedClickableTextLogin(onLoginClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("Joined us before? ")

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "LogIn",// provide tag which will then be provided when you click the text
            annotation = "LogIn"
        )
        //add text with your different color/style
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("Log In")
        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "LogIn",// tag which you used in the buildAnnotatedString
                start = offset,
                end = offset
            )[0].let { annotation ->
                //do your stuff when it gets clicked
                onLoginClick()
                Timber.d("Clicked", annotation.item)
            }
        }
    )
}

@Composable
fun AnnotatedClickableTextRegister(onRegisterClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("New to Groom? ")

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "Register",// provide tag which will then be provided when you click the text
            annotation = "Register"
        )
        //add text with your different color/style
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("Register")
        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "Register",// tag which you used in the buildAnnotatedString
                start = offset,
                end = offset
            )[0].let { annotation ->
                //do your stuff when it gets clicked
                onRegisterClick()
                Timber.d("Clicked", annotation.item)
            }
        }
    )
}


@Composable
fun AnnotatedClickableTextsPrivacyAndTermsAncCondition(
    onTermsAndConditionClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("By signing up, you're agree to our ")

        }

        pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("privacy policy")
        }
        pop()

        append(" and ")

        pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")

        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("terms of use")
        }

        pop()
    }


    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.body1,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
                .firstOrNull()?.let {
                onPrivacyPolicyClick()
            }

            annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                .firstOrNull()?.let {
                onTermsAndConditionClick()
            }
        })
}

@Composable
fun GroomTextField(
    hint: String,
    state: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit),
    onStateChange: (String) -> Unit,
    validation: () -> Boolean
) {
    OutlinedTextField(
        value = state,
        label = {
            Text(
                text = hint,
                style = MaterialTheme.typography.subtitle1
            )
        },
        onValueChange = {
            onStateChange(it)
            validation()
        },
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = leadingIcon,
        keyboardOptions = remember {
            keyboardOptions
        },
        maxLines = 1,
        visualTransformation = visualTransformation,
                keyboardActions = keyboardActions,
        isError = isError,
    )
}



