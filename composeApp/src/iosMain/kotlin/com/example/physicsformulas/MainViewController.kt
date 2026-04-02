package com.example.physicsformulas

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    val appContainer = remember {
        PhysicsAppContainer(
            driverFactory = DatabaseDriverFactory(),
        )
    }

    App(appContainer)
}
