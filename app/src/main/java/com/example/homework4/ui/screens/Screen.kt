package com.example.homework4.ui.screens

sealed class Screen(val route: String) {
    object NewsScreen : Screen("news")
    object NewsDetailsScreen : Screen("details")
}