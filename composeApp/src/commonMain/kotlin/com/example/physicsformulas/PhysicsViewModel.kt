package com.example.physicsformulas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhysicsViewModel(
    private val repository: PhysicsRepository,
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _uiState = MutableStateFlow(PhysicsUiState())

    val uiState: StateFlow<PhysicsUiState> = _uiState.asStateFlow()

    init {
        loadCatalog()
    }

    fun openSection(sectionId: Long) {
        _uiState.update { state ->
            state.copy(
                selectedSectionId = sectionId,
                currentFormulaIndex = 0,
                isFormulaVisible = false,
            )
        }
    }

    fun returnToSections() {
        _uiState.update { state ->
            state.copy(
                selectedSectionId = null,
                currentFormulaIndex = 0,
                isFormulaVisible = false,
            )
        }
    }

    fun showNextFormula() {
        _uiState.update { state ->
            val section = state.selectedSection ?: return@update state
            val nextIndex = (state.currentFormulaIndex + 1) % section.formulas.size

            state.copy(
                currentFormulaIndex = nextIndex,
                isFormulaVisible = false,
            )
        }
    }

    fun toggleFormulaVisibility() {
        _uiState.update { state ->
            state.copy(isFormulaVisible = !state.isFormulaVisible)
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }

    private fun loadCatalog() {
        scope.launch(Dispatchers.Default) {
            val sections = repository.getCatalog()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    sections = sections,
                )
            }
        }
    }
}
