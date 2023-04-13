package com.example.homework4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homework4.entity.Article
import com.example.homework4.ui.theme.Homework4Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<DataLoaderViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNews()
        setContent {
            Homework4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
                            SearchBar(onSearch = { })

                        }
                        NewsList(newsResults)
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(searchText) }
        ),
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search button")
        },
        trailingIcon = {
            Icon(Icons.Filled.List, contentDescription = "Search filter")
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun NewsList(newsResult: Result<List<Article>>) {
    when (newsResult) {
        is Result.Success -> {
            val newsItems = newsResult.data
            LazyColumn {
                items(newsItems) { article ->
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
                            Column(modifier = Modifier.fillMaxSize()) {
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
                Text(text = "Something went wrong", modifier = Modifier.align(Alignment.Center))

            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
