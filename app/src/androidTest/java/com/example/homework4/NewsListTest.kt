package com.example.homework4

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.homework4.entity.Article
import com.example.homework4.entity.Source
import com.example.homework4.ui.components.NewsList
import org.junit.Rule
import org.junit.Test


class NewsListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = DataLoaderViewModel()

    private val newsItems = listOf(
        Article(
            source = Source("1", "Source 1"),
            title = "News 1",
            author = "Author 1",
            urlToImage = "url1",
            content = "content1",
            description = "description1",
            publishedAt = "00:00",
            url = "example1.com"
        ),
        Article(
            source = Source("2", "Source 2"),
            title = "News 2",
            author = "Author 2",
            urlToImage = "url2",
            content = "content2",
            description = "description2",
            publishedAt = "00:00",
            url = "example2.com"
        ),
        Article(
            source = Source("3", "Source 3"),
            title = "News 3",
            author = "Author 3",
            urlToImage = "url3",
            content = "content2",
            description = "description2",
            publishedAt = "00:00",
            url = "example2.com"
        )
    )

    @Test
    fun newsList_renderNewsItems() {

        composeTestRule.setContent {
            NewsList(
                newsResult = Result.Success(newsItems),
                navController = TestNavHostController(
                    ApplicationProvider.getApplicationContext()
                ),
                viewModel = viewModel
            )
        }

        newsItems.forEach { item ->
            composeTestRule.onNodeWithText(item.title)
                .assertIsDisplayed()

            composeTestRule.onNodeWithText(item.source.name)
                .assertIsDisplayed()

            item.author?.let { author ->
                composeTestRule.onNodeWithText(author)
                    .assertIsDisplayed()
            }
        }
    }

    @Test
    fun newsList_renderLoadingState() {
        composeTestRule.setContent {
            NewsList(
                newsResult = Result.Loading(""),
                navController = TestNavHostController(
                    ApplicationProvider.getApplicationContext()
                ),
                viewModel = viewModel
            )
        }

        // Assert that the CircularProgressIndicator is displayed
        composeTestRule.onNodeWithContentDescription("CircularProgressIndicator")
            .assertIsDisplayed()
    }
}