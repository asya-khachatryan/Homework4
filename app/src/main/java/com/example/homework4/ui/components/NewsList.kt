package com.example.homework4.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.homework4.DataLoaderViewModel
import com.example.homework4.R
import com.example.homework4.Result
import com.example.homework4.entity.Article
import com.example.homework4.ui.screens.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsList(
    newsResult: Result<List<Article>>,
    navController: NavHostController,
    viewModel: DataLoaderViewModel
) {
    val isInRefreshState by viewModel.isInRefreshState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isInRefreshState, { viewModel.loadNews() })

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        when (newsResult) {
            is Result.Success -> {
                val newsItems = newsResult.data
                LazyColumn {
                    itemsIndexed(newsItems) { index, article ->
                        Card(
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp
                        ) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = article.urlToImage,
                                    contentDescription = article.title,
                                    placeholder = painterResource(id = R.drawable.image1),
                                    modifier = Modifier
                                        .width(120.dp)
                                        .fillMaxHeight()
                                        .padding(end = 16.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(modifier = Modifier
                                    .clickable {
                                        navController.navigate(
                                            Screen.NewsDetailsScreen.route + "/$index"
                                        )
                                    }
                                    .fillMaxSize()) {
                                    Text(
                                        text = article.source.name,
                                        style = MaterialTheme.typography.caption,
                                    )
                                    Text(
                                        text = article.title,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                    )
                                    article.author?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.caption,
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
            is Result.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (newsResult.exception.message.equals("No results found")) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "No results found"
                        )
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Something went wrong"
                        )
                    }
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.semantics {
                        contentDescription = "CircularProgressIndicator"
                    })
                }
            }
        }

        PullRefreshIndicator(
            isInRefreshState, pullRefreshState, Modifier.align(Alignment.TopCenter)
        )

    }
}