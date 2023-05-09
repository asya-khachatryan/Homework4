package com.example.homework4.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.homework4.entity.Category
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun FilterModal(
    innerContent: @Composable () -> Unit,
    onFilter: (String) -> Unit
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            LazyColumn {
                items(items = Category.values().toList()) {
                    ListItem(
                        text = { Text(it.toString()) },
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                run { onFilter(it.categoryName) }
                                scope.launch { state.hide() }
                            }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { scope.launch { state.show() } }, Modifier.fillMaxWidth()) {
                Text("Filter")
            }
            innerContent()
        }

    }
}