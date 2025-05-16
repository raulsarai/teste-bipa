package com.bipa.teste.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bipa.teste.R

@Composable
fun OfflineScreen(onRetry: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.no_connectivity)
                ,color = Color.White
            )
            Spacer(modifier = Modifier
                .height(12.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = onRetry) {
                Text(
                    text=stringResource(R.string.try_again),
                    color= Color.Black
                )
            }
        }
    }
}