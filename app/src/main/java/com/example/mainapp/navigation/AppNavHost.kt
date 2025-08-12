package com.example.mainapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mainapp.ui.theme.screens.SplashScreen
import com.example.mainapp.ui.theme.screens.dashboard.DashboardScreen
import com.example.mainapp.ui.theme.screens.login.loginScreen
import com.example.mainapp.ui.theme.screens.patients.AddPatientScreen
import com.example.mainapp.ui.theme.screens.patients.PatientListScreen
import com.example.mainapp.ui.theme.screens.patients.UpdatePatientScreen
import com.example.mainapp.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(), startDestination: String = ROUTE_SPLASH){
    NavHost(navController = navController, startDestination = startDestination){
        composable (ROUTE_SPLASH){ SplashScreen{navController.navigate(ROUTE_REGISTER){popUpTo(ROUTE_SPLASH){inclusive = true} } } }
        composable(ROUTE_REGISTER){ registerScreen(navController) }
        composable(ROUTE_LOGIN){ loginScreen(navController) }
        composable(ROUTE_DASHBOARD){ DashboardScreen(navController) }
        composable(ROUTE_ADDPATIENT){ AddPatientScreen(navController) }
        composable(ROUTE_VIEWPATIENT){ PatientListScreen(navController) }
        composable(ROUTE_UPDATE_PATIENT,
            arguments = listOf(navArgument("patientId")
            {type = NavType.StringType})){
            backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")!!
            UpdatePatientScreen(navController, patientId)
        }

    }

}
