package com.ls.localsky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen

data class BottomNavigationItem(
    val title: String,                  //name of the button
    val selectedIcon: ImageVector,      //icon to use when on this screen
    val unselectedIcon: ImageVector,    //icon to use when NOT on this screen
    val hasNews: Boolean,               //used to show a little red dot to notify user of change
    val badgeCount: Int? = null         //used to show number of incoming messages
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavBar(){
    //TODO: add Screen.WeatherScreen
    val navigate = listOf(/*Screen.WeatherScreen, */Screen.MapScreen, Screen.SettingsScreen)

    val navigationItem = listOf(
//        BottomNavigationItem(
//            title = "weather",
//            selectedIcon = Icons.Filled.Cloud,
//            unselectedIcon = Icons.Outlined.Cloud,
//            hasNews = false
//        ),
        BottomNavigationItem(
            title = "map",
            selectedIcon = Icons.Filled.Map,
            unselectedIcon = Icons.Outlined.Map,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = false
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        navigationItem.forEachIndexed{index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    LocalSkyAppRouter.navigateTo(navigate[index])
                },
                label = {Text(text = item.title)},
                icon = {
                    BadgedBox(
                        badge = {
                            if(item.badgeCount != null){
                                Badge{ Text(text = item.badgeCount.toString()) }
                            } else if (item.hasNews) Badge()
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomNavBar(){ CustomNavBar() }