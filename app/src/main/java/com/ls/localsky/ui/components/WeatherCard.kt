package com.ls.localsky.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable

@Composable
fun WeatherCard(
    backgroundColor: Color,
    modifier: Modifier = Modifier
){
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

}