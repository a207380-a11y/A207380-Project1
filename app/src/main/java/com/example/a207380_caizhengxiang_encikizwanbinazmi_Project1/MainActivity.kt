package com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// 注意：如果你本地有自定义主题，请确保 import 你的主题包，如果没有可以直接删掉 AppTheme 这一层
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
    val viewModel: EcoSwapViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            EcoSwapMainScreen(
                onNavigateToAdd = { navController.navigate("add_item") },
                onNavigateToSummary = { navController.navigate("summary") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
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

        composable("summary") {
            SummaryListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}