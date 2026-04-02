package com.example.physicsformulas

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.physicsformulas.db.PhysicsDatabase

actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = PhysicsDatabase.Schema,
        context = context,
        name = "physics_formulas.db",
    )
}
