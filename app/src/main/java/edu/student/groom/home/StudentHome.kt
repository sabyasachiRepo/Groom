package edu.student.groom.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(){
    Box( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    ) {
        Text(
            text = "Welcome to Groom Home Screen",
            modifier = Modifier.padding(top = 24.dp)
                .align(Alignment.Center),
            color = Color.Black
        )

    }
}