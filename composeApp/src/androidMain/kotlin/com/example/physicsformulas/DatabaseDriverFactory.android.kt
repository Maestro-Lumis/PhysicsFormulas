package com.example.physicsformulas

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.physicsformulas.db.PhysicsDatabase

actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver {
        val dbFile = context.getDatabasePath("physics_formulas.db")
        if (dbFile.exists()) {
            val prefs = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)
            val savedVersion = prefs.getInt("db_version", 0)
            val currentVersion = PhysicsDatabase.Schema.version.toInt()

            if (savedVersion != currentVersion) {
                context.deleteDatabase("physics_formulas.db")
                prefs.edit().putInt("db_version", currentVersion).apply()
            }
        } else {
            val prefs = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)
            prefs.edit().putInt("db_version", PhysicsDatabase.Schema.version.toInt()).apply()
        }

        return AndroidSqliteDriver(
            schema = PhysicsDatabase.Schema,
            context = context,
            name = "physics_formulas.db",
        )
    }
}