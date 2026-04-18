package com.example.physicsformulas

data class PhysicsSection(
    val id: Long,
    val title: String,
    val description: String,
    val formulaCount: Int,
    val formulas: List<PhysicsFormula>,
)

data class PhysicsFormula(
    val id: Long,
    val sectionId: Long,
    val kim: Long,
    val label: String,
    val prompt: String,
    val explanation: String,
    val imageName: String,
)
