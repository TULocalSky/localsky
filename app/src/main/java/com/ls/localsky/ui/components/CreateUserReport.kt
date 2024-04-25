package com.ls.localsky.ui.components

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType
import com.ls.localsky.viewmodels.SensorViewModelLS
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS

@Composable
fun showUserReportScreen(
    modifier: Modifier,
    sensorViewModel: SensorViewModelLS,
    showUserReportScreen: MutableState<Boolean>,
    userViewModel: UserViewModelLS,
    userReportViewModel: UserReportViewModelLS,
    database: DatabaseLS,
    currentLocation: LatLng?
) {
    if(userViewModel.getCurrentUser().userID != null){
        if(!showUserReportScreen.value){
            createUserReportButton(
                modifier = modifier,
                showUserReportScreen = showUserReportScreen
            )

        } else{
            UserReportPopup(
                sensorViewModel = sensorViewModel,
                submitAction = {
                        picture, condition ->
                    val user = userViewModel.getCurrentUser()
                    currentLocation?.latitude?.let {
                        database.uploadReport(
                            picture,
                            user,
                            it,
                            currentLocation.longitude,
                            condition.weatherSummary,
                            sensorViewModel.getAmbientTempF().toString(),
                            sensorViewModel.getRelativeHumidity(),
                            { ref, report ->
                                Log.d("UserReport","Report Uploaded $report")
                                database.getAllUserReports (currentLocation){
                                    it?.let {
                                        userReportViewModel.setUserReports(it, database)
                                    }
                                }
                            },
                            {
                                Log.d("UserReport","It didnt work")
                            }
                        )
                    }
                    showUserReportScreen.value = false
                },
                cancelAction = {
                    showUserReportScreen.value = false
                }
            )
        }
    }
}

@Composable
fun createUserReportButton(
    modifier: Modifier,
    showUserReportScreen: MutableState<Boolean>,
){
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { showUserReportScreen.value = true  },
        icon = { Icon(Icons.Filled.Edit, "Report") },
        text = { Text(text = "Report") },
    )
}

@Composable
fun UserReportPopup(
    sensorViewModel: SensorViewModelLS,
    submitAction: (Bitmap, WeatherType) -> Unit,
    cancelAction: () -> Unit
){
    val selectedWeatherItem = remember {
        mutableStateOf<WeatherItem?>(null)
    }

    //compose equivalent to using intents which launch photo taking apps
    val userImage = remember { mutableStateOf<Bitmap?>(null) }
    val userImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        userImage.value = it
    }

    val paddingForLazyColumn = 15.dp
    Card (
        modifier = Modifier
            .padding(paddingForLazyColumn),

        ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingForLazyColumn),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(userImage.value == null){
                Image(
                    painter = painterResource(id = R.drawable.no_photo_jpg ),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center
                )
            }else{
                Image(
                    bitmap = userImage.value!!.asImageBitmap(),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            FilledTonalButton(onClick = { userImageLauncher.launch() }) {
                Text("take a pic")
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = "What's the weather looking like?",
            )
            WeatherConditionButtonDisplay(
                selectedWeatherItem = selectedWeatherItem,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            UserReportField(
                title = "Temperature",
                field = sensorViewModel.getAmbientTempF()
            )
            UserReportField(
                title = "Relative Humidity",
                field = sensorViewModel.getRelativeHumidity()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row{
                FilledTonalButton(onClick = {
                    if(userImage.value != null && selectedWeatherItem.value != null){
                        submitAction(userImage.value!!, selectedWeatherItem.value!!.weatherType)
                    }
                }) {
                    Text(text = "Submit")
                }
                Spacer(modifier = Modifier.padding(20.dp))
                FilledTonalButton(onClick = {
                    cancelAction()
                }) {
                    Text(text = "Cancel")
                }
            }
        }

    }
}