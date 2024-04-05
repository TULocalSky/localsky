package com.ls.localsky.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.R
import com.ls.localsky.models.WeatherType
import com.ls.localsky.viewmodels.WeatherViewModelLS
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun WeatherCard(
    viewModel: WeatherViewModelLS,
){
    viewModel.weatherDataState.weatherData?.let { data ->
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(0.dp, 20.dp, 0.dp, 20.dp))
                .padding(16.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    /*.clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))*/
                    .background(brush = Brush.linearGradient(listOf(Color(0xFFADD8E6), Color.LightGray)))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                /*Time of day*/
                Text(
                    text = "Today ${
                        SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                    }",
                    modifier = Modifier.align(Alignment.End),
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
                    text = "${
                        data.hourly.data[0].temperature
                    }°",
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                /*Weather description*/
                Text(
                    /*Retrieve from weather data*/
                    text = data.hourly.data[0].summary,
                    fontSize = 30.sp,
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
                    )
                    WeatherDataDisplay(
                        value = data.hourly.data[0].windSpeed.toInt(),
                        unit = "m/s",
                        icon = ImageVector.vectorResource(R.drawable.wind),
                    )
                }
            }
        }
    }

}

