package com.example.physicsformulas

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.physicsformulas.db.PhysicsDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = PhysicsDatabase.Schema,
        name = "physics_formulas.db",
    )
}
