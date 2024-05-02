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
import com.ls.localsky.isDay
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherConditionButtonDisplay(
    selectedWeatherItem: MutableState<WeatherItem?>,
){
    val weatherItems = remember {
        WeatherType.reportWeatherTypes.map {
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
                value = convertWeatherSummary(selectedWeatherItem.value?.weatherType?.weatherSummary),
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
                        text = { Text(text = convertWeatherSummary(item.weatherType.weatherSummary)) },
                        onClick = {
                            selectedWeatherItem.value = appendTimeofDay(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun appendTimeofDay(weatherItem: WeatherItem) : WeatherItem{
    val nowTime = LocalTime.now()

    val weatherSummary = weatherItem.weatherType.weatherSummary
    if(weatherSummary.equals("clear-")){
        return if(isDay(nowTime)){
            weatherItem.copy(
                weatherType = WeatherType.ClearDay
            )
        } else {
            weatherItem.copy(
                weatherType = WeatherType.ClearNight
            )
        }
    } else if(weatherSummary.equals("partly-cloudy-")){
        return if(isDay(nowTime)){
            weatherItem.copy(
                weatherType = WeatherType.PartlyCloudyDay
            )
        } else {
            weatherItem.copy(
                weatherType = WeatherType.PartlyCloudyNight
            )
        }
    }
    return weatherItem
}

fun convertWeatherSummary(weatherSummary: String?): String{
    return if (weatherSummary == null){
        " "
    } else {
        when (weatherSummary) {
            "clear-" ->
                "Clear"
            "fog" ->
                "Foggy"
            "sleet" ->
                "Sleet"
            "snow" ->
                "Snowy"
            "rain" ->
                "Rainy"
            "wind" ->
                "Windy"
            "partly-cloudy-" ->
                "Partly Cloudy"
            "cloudy" ->
                "Cloudy"
            else ->
                "Error"
        }
    }
}