package com.ls.localsky.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ls.localsky.R
import com.ls.localsky.convertWeatherSummary
import com.ls.localsky.models.UserReport
import com.ls.localsky.parseTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReportSheet(
    reportAndPic: Pair<UserReport, Bitmap?>,
    showBottomSheet: MutableState<Boolean>,
){
    val userImage = remember { mutableStateOf(reportAndPic.second) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(userImage.value == null){
                Image(
                    painter = painterResource(id = R.drawable.no_photo_jpg ),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center,
                    modifier = Modifier.size(500.dp)
                )
            }else{
                Image(
                    bitmap = userImage.value!!.asImageBitmap(),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center,
                    modifier = Modifier.height(100.dp)
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Today at ${parseTime(reportAndPic.first.createdTime!!)}")
            Spacer(modifier = Modifier.padding(20.dp))
            Divider()
            UserReportField(
                title = "Weather Condition:",
                field = convertWeatherSummary(reportAndPic.first.weatherCondition)
            )
            UserReportField(
                title = "Reported Weather:",
                field = reportAndPic.first.reportedTemperature
            )
            UserReportField(
                title = "Reported Relative Humidity",
                field = reportAndPic.first.reportedRelativeHumidity
            )
        }
    }
}
@Composable
fun UserReportField(
    title: String,
    field: String?,
){
    field?.let {
        Spacer(modifier = Modifier.padding(20.dp))
        Text(text = title)
        Text(text = it)
    }

}