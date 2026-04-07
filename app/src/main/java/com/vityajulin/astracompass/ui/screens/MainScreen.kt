package com.vityajulin.astracompass.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vityajulin.astracompass.R

@Composable
fun MainScreen(viewModel: CompassViewModel) {
    // Пока закладываем 2 таба: Компас и Настройки (на будущее)
    val pagerState = rememberPagerState(pageCount = { 2 })

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = pagerState.currentPage == 0,
                    onClick = { /* скролл к 0 */ },
                    label = { Text(stringResource(R.string.tab_compass)) },
                    icon = { /* иконка */ }
                )
                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = { /* скролл к 1 */ },
                    label = { Text(stringResource(R.string.tab_settings)) },
                    icon = { /* иконка */ }
                )
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) { page ->
            when (page) {
                0 -> CompassTab(viewModel)
                1 -> Text("Settings coming soon...")
            }
        }
    }
}
