package com.example.physicsformulas

import com.example.physicsformulas.db.PhysicsDatabase

class PhysicsAppContainer(
    driverFactory: DatabaseDriverFactory,
) {
    private val database = PhysicsDatabase(driverFactory.createDriver())

    val repository = PhysicsRepository(database)
}
