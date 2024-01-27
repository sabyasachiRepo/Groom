package edu.student.groom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.student.groom.home.homeScreen
import edu.student.groom.home.navigateToHomeScreen
import edu.student.groom.onboarding.login.ui.navigation.loginRoute
import edu.student.groom.onboarding.login.ui.navigation.loginScreen
import edu.student.groom.onboarding.login.ui.navigation.navigateToLogin
import edu.student.groom.onboarding.signup.ui.navigateToSingUpScreenPageOne
import edu.student.groom.onboarding.signup.ui.navigateToSingUpScreenPageTwo
import edu.student.groom.onboarding.signup.ui.signUpScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                 MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        setContent {
            if(uiState is MainActivityUiState.Success){
                MainScreen((uiState as MainActivityUiState.Success).route)
            }

        }

    }

    @Composable
    fun MainScreen(route:String) {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = route) {
            loginScreen({
                navController.navigateToSingUpScreenPageOne()
            }) {
                navController.navigateToHomeScreen()
            }
            signUpScreen({ shouldClearBackStack -> navController.navigateToLogin(shouldClearBackStack) }) { eMail,fName, lName ->
                navController.navigateToSingUpScreenPageTwo(eMail,fName, lName)
            }
            homeScreen()
        }
    }
}