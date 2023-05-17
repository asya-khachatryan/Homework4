package com.example.homework4

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.homework4.entity.Category
import com.example.homework4.ui.components.FilterModal
import org.junit.Rule
import org.junit.Test

class FilterModalTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun filterModal_displayCategories() {
        composeTestRule.setContent {
            FilterModal(
                innerContent = {},
                onFilter = {}
            )
        }
        composeTestRule.onAllNodesWithContentDescription("categoryItem")
            .assertCountEquals(Category.values().size)
    }

    @Test
    fun filterModal_clickCategory() {
        var filterSelected: String? = null

        composeTestRule.setContent {
            FilterModal(
                innerContent = { },
                onFilter = { filterSelected = it }
            )
        }

        composeTestRule.onNodeWithText(Category.values()[0].toString())
            .performClick()

        assert(filterSelected == Category.values()[0].categoryName)
    }

    @Test
    fun filterModal_showModalOnClick() {
        composeTestRule.setContent {
            FilterModal(
                innerContent = { },
                onFilter = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("FilterModalSheet")
            .assertIsNotDisplayed()

        composeTestRule.onNodeWithText("Filter")
            .performClick()

        composeTestRule.onNodeWithContentDescription("FilterModalSheet")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(Category.values()[0].toString())
            .performClick()

        composeTestRule.onNodeWithContentDescription("FilterModalSheet")
            .assertIsNotDisplayed()
    }
}

