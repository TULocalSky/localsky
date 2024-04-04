package com.ls.localsky.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.R
import com.ls.localsky.WeatherAPI
import com.ls.localsky.models.WeatherType
import com.ls.localsky.viewmodels.WeatherViewModelLS
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModelLS = WeatherViewModelLS(),
    backgroundColor: Color
){
    viewModel.getWeatherData().value?.let { data ->
    Card(
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            /*Time of day*/
            Text(
                text = "Today ${
                    SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                }",
                modifier = Modifier.align(Alignment.End),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            /*Weather icon*/

            Image(
                painter = painterResource(id = WeatherType.fromWeatherReport(data.hourly.summary).iconRes),
                contentDescription = null,
                modifier = Modifier.width(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            /*Temperature*/
            Text(
                /*Retrieve from weather data*/
                text = "Temperature: ${
                    data.hourly.data[0].temperature
                }",
                fontSize = 50.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            /*Weather description*/
            Text(
                /*Retrieve from weather data*/
                text = "Description: ${
                    data.hourly.data[0].summary
                }",
                fontSize = 50.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            /*Weather data to display, Wind speed and precipitation probability*/
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                /*Call weather data row composable */
                WeatherDataDisplay(
                    value = data.hourly.data[0].precipProbability.toInt(),
                    unit = "%",
                    icon = ImageVector.vectorResource(R.drawable.drop),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White)
                )
                WeatherDataDisplay(
                    value = data.hourly.data[0].windSpeed.toInt(),
                    unit = "m/s",
                    icon = ImageVector.vectorResource(R.drawable.wind),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White)
                )
            }
            }
        }
    }

}

