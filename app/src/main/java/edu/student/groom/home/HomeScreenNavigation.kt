package edu.student.groom.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val homeScreenRoute="home_route"
fun NavController.navigateToHomeScreen(){
    this.navigate(homeScreenRoute)
}

fun NavGraphBuilder.homeScreen(){
    composable(route= homeScreenRoute){
        HomeScreen()
    }
}