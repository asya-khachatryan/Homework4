package com.example.homework4.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homework4.entity.Article


@Composable
fun NewsDetailsScreen(
    navController: NavController, article: Article
) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Row(
                horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "News Detail", fontWeight = FontWeight.Bold)
            }
        }
    }) {
        it
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "Source: " + article.source.name, fontWeight = FontWeight.Bold)
                Text(text = "Author: " + article.author, fontWeight = FontWeight.Bold)
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(30.dp))
                        .height(100.dp)
                )

                Text(text = article.title, fontWeight = FontWeight.Bold)
                Text(text = article.description)
            }
        }
    }
}