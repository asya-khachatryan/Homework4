package com.example.homework4


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homework4.ui.screens.NewsDetailsScreen
import com.example.homework4.ui.screens.NewsScreen
import com.example.homework4.ui.screens.Screen
import com.example.homework4.ui.theme.Homework4Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNews()
        setContent {
            Homework4Theme {
                val newsList = viewModel.news.observeAsState(Result.loading()).value
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.NewsScreen.route
                ) {

                    composable(route = Screen.NewsScreen.route) {
                        NewsScreen(navController, viewModel)
                    }

                    composable(
                        route = Screen.NewsDetailsScreen.route + "/{id}",
                        arguments = listOf(navArgument(name = "id") {
                            type = NavType.IntType
                        })
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")

                        id?.let {
                            if (newsList is Result.Success) {
                                newsList.data[id]
                            } else {
                                null
                            }
                        }?.let {
                            NewsDetailsScreen(navController, it)
                        }
                    }

                }
            }
        }
    }
}