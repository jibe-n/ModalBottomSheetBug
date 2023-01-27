package com.example.modalbottomsheetbug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modalbottomsheetbug.ui.theme.ModalBottomSheetBugTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModalBottomSheetBugTheme() {
                BottomSheetNavDemo()
            }
        }
    }
}

private object Destinations {
    const val ScreenA = "SCREEN_A"
    const val ScreenB = "SCREEN_B"
    const val Sheet = "SHEET"
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun BottomSheetNavDemo() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(navController, Destinations.ScreenA) {
            composable(Destinations.ScreenA) {
                ScreenA(
                    navigateToSheet = {
                        navController.navigate(Destinations.Sheet)
                    },
                    navigateToScreenB = {
                        navController.navigate(Destinations.ScreenB)
                    }
                )
            }
            composable(Destinations.ScreenB) {
                ScreenB()
            }
            bottomSheet(Destinations.Sheet) {
                BottomSheet(
                    navigateToScreenB = {
                        navController.navigate(Destinations.ScreenB)
                    }
                )
            }
        }
    }
}

@Composable
private fun ScreenA(
    navigateToSheet: () -> Unit,
    navigateToScreenB: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = navigateToSheet) {
            Text("Open Sheet")
        }
        Button(onClick = navigateToScreenB) {
            Text("Navigate to Screen B")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenB() {
    val focusRequester = remember { FocusRequester() }

    SideEffect {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester),
            value = "",
            onValueChange = {}
        )
    }
}

@Composable
private fun BottomSheet(
    navigateToScreenB: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = navigateToScreenB) {
            Text("Navigate to Screen B")
        }
    }
}