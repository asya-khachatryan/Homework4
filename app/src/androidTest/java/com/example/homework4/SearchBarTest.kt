package com.example.homework4

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.homework4.ui.components.SearchBar
import org.junit.Rule
import org.junit.Test

class SearchBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchBar_onClearSearch() {
        activityScenarioRule.scenario.onActivity {
            composeTestRule.setContent {
                SearchBar(onSearch = { searchText ->
                    assert(searchText.isEmpty())
                })
            }
        }

        composeTestRule.onNodeWithText("Search")
            .performTextInput("King Charles")
        composeTestRule.onNodeWithContentDescription("Delete search").performClick()
    }

    @Test
    fun searchBar_onSearchClicked() {
        activityScenarioRule.scenario.onActivity {
            composeTestRule.setContent {
                SearchBar(onSearch = { searchText ->
                    assert(searchText == "King Charles")
                })
            }
        }

        composeTestRule.onNodeWithContentDescription("Search button").performClick()

        composeTestRule.onNodeWithText("Search")
            .performTextInput("King Charles")

        composeTestRule.onNodeWithText("Search")
            .performImeAction()
    }


}