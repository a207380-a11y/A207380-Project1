package com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                EcoSwapApp()
            }
        }
    }
}

// ================= 导航主控台 =================
@Composable
fun EcoSwapApp() {
    val navController = rememberNavController()
    // 这里的 viewModel() 现在会变成正常的颜色
    val viewModel: EcoSwapViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            EcoSwapMainScreen(onNavigateToAdd = { navController.navigate("add_item") })
        }
        composable("add_item") {
            AddSwapScreen(
                viewModel = viewModel,
                onNavigateToConfirm = { navController.navigate("confirmation") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("confirmation") {
            ConfirmationScreen(
                viewModel = viewModel,
                onNavigateHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}