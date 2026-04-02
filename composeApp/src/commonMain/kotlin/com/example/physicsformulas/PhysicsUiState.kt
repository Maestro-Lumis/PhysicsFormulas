package com.example.physicsformulas

data class PhysicsUiState(
    val isLoading: Boolean = true,
    val sections: List<PhysicsSection> = emptyList(),
    val selectedSectionId: Long? = null,
    val currentFormulaIndex: Int = 0,
    val isFormulaVisible: Boolean = false,
) {
    val selectedSection: PhysicsSection?
        get() = sections.firstOrNull { it.id == selectedSectionId }

    val currentFormula: PhysicsFormula?
        get() = selectedSection?.formulas?.getOrNull(currentFormulaIndex)
}
