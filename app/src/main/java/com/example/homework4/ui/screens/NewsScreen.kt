package com.example.homework4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.homework4.DataLoaderViewModel
import com.example.homework4.Result
import com.example.homework4.ui.components.FilterModal
import com.example.homework4.ui.components.NewsList
import com.example.homework4.ui.components.SearchBar

@Composable
fun NewsScreen(
    navController: NavHostController, viewModel: DataLoaderViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        val newsResults = viewModel.news.observeAsState(Result.loading()).value

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color(0xFF25315D))
            ) {
                Text(
                    text = "News",
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                    modifier = Modifier.padding(15.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color(0xFFFFFFFF))
            ) {
                SearchBar(onSearch = { searchTerm -> viewModel.loadNewsWithSearch(searchTerm) })
            }
            FilterModal(innerContent = {
                NewsList(
                    newsResults, navController, viewModel
                )
            }, onFilter = { filter -> viewModel.loadNewsWithFilter(filter) })

        }
    }
}