package com.vityajulin.astracompass.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vityajulin.astracompass.R
import com.vityajulin.astracompass.ui.theme.AstracompassTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: CompassViewModel) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }

    // Синхронизируем selectedTab при свайпе
    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    AstracompassTheme(isAstroMode = state.isAstroMode) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.toggleAstroMode() },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(
                        imageVector = if (state.isAstroMode) Icons.Filled.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle Astro Mode"
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            scope.launch { pagerState.animateScrollToPage(0) }
                        },
                        label = { Text(stringResource(R.string.tab_compass)) },
                        icon = { /* иконка */ }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            scope.launch { pagerState.animateScrollToPage(1) }
                        },
                        label = { Text(stringResource(R.string.tab_level)) },
                        icon = { /* иконка */ }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            scope.launch { pagerState.animateScrollToPage(2) }
                        },
                        label = { Text(stringResource(R.string.tab_pitch)) },
                        icon = { /* иконка */ }
                    )
                }
            }
        ) { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> CompassTab(viewModel)
                    1 -> LevelTab(viewModel)
                    2 -> PitchTab(viewModel)
                }
            }
        }
    }
}
