package com.ls.localsky.ui.components

import android.transition.Visibility
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ls.localsky.ui.app.Screen

@Composable
fun CustomNavBar(navController: NavController){
    //TODO: add Screen.WeatherScreen
    val navigate = listOf(/*Screen.WeatherScreen, */Screen.MapScreen, Screen.SettingsScreen)
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    when(currentRoute){
        Screen.LoginScreen.screen_route -> {
            bottomBarState.value = false
        }
        Screen.RegisterScreen.screen_route -> {
            bottomBarState.value = false
        }
        Screen.MapScreen.screen_route -> {
            bottomBarState.value = true
        }
        Screen.SettingsScreen.screen_route -> {
            bottomBarState.value = true
        }
        else -> {
            bottomBarState.value = true
        }
    }

    AnimatedVisibility(
        bottomBarState.value
    ){
        NavigationBar (
        ){
            navigate.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = { Text(text = item.title,
                        fontSize = 9.sp) },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}
//            NavigationBarItem(
//                selected = selectedItemIndex == index,
//                onClick = {
//                    selectedItemIndex = index
//                    LocalSkyAppRouter.navigateTo(navigate[index])
//                },
//                label = {Text(text = item.title)},
//                icon = {
//                    BadgedBox(
//                        badge = {
//                            if(item.badgeCount != null){
//                                Badge{ Text(text = item.badgeCount.toString()) }
//                            } else if (item.hasNews) Badge()
//                        }
//                    ) {
//                        Icon(
//                            icon = { Icon(item.icon, contentDescription = item.title) },
//                            contentDescription = item.title
//                        )
//                    }
//                })

//        }
//    }

//@Preview(showBackground = true)
//@Composable
//fun PreviewCustomNavBar(){ CustomNavBar() }