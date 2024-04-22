package com.ls.localsky.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherConditionButtonDisplay(selectedWeatherItem: MutableState<WeatherItem?>){
    val weatherItems = remember {
        WeatherType.allWeatherTypes.map {
            WeatherItem(it)
        }.toTypedArray()
    }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedWeatherItem.value?.weatherType?.weatherSummary ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                weatherItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.weatherType.weatherSummary) },
                        onClick = {
                            selectedWeatherItem.value = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}