package com.example.mainapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mainapp.ui.theme.screens.dashboard.DashboardScreen
import com.example.mainapp.ui.theme.screens.login.loginScreen
import com.example.mainapp.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(), startDestination: String = ROUTE_REGISTER){
    NavHost(navController = navController, startDestination = startDestination){
        composable(ROUTE_REGISTER){ registerScreen(navController) }
        composable(ROUTE_LOGIN){ loginScreen(navController) }
        composable(ROUTE_DASHBOARD){ DashboardScreen(navController) }

    }

}
