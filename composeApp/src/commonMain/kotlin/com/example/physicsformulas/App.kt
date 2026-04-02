package com.example.physicsformulas

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun App(appContainer: PhysicsAppContainer) {
    val viewModel = remember(appContainer) { PhysicsViewModel(appContainer.repository) }
    val uiState by viewModel.uiState.collectAsState()

    PhysicsFormulasTheme {
        PhysicsRoot(
            uiState = uiState,
            onSectionClick = viewModel::openSection,
            onBackClick = viewModel::returnToSections,
            onNextClick = viewModel::showNextFormula,
            onToggleFormula = viewModel::toggleFormulaVisibility,
        )
    }
}

@Composable
private fun PhysicsRoot(
    uiState: PhysicsUiState,
    onSectionClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onToggleFormula: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PhysicsPalette.Ink),
    ) {
        val isWide = maxWidth >= 840.dp
        val horizontalPadding = if (maxWidth < 420.dp) 12.dp else 18.dp
        val contentWidth = maxWidth - (horizontalPadding * 2)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding, vertical = 20.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            when {
                uiState.isLoading -> LoadingState()
                uiState.selectedSection == null -> SectionsScreen(
                    sections = uiState.sections,
                    isWide = isWide,
                    contentWidth = contentWidth,
                    onSectionClick = onSectionClick,
                )
                else -> FormulaScreen(
                    uiState = uiState,
                    contentWidth = contentWidth,
                    onBackClick = onBackClick,
                    onNextClick = onNextClick,
                    onToggleFormula = onToggleFormula,
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = PhysicsPalette.Ruby)
    }
}

@Composable
private fun SectionsScreen(
    sections: List<PhysicsSection>,
    isWide: Boolean,
    contentWidth: androidx.compose.ui.unit.Dp,
    onSectionClick: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(if (isWide) 2 else 1),
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = contentWidth),
        contentPadding = PaddingValues(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ScreenHeader(
                title = "Физика",
                subtitle = "Формулы",
            )
        }

        items(sections, key = { it.id }) { section ->
            SectionCard(
                section = section,
                onClick = { onSectionClick(section.id) },
            )
        }
    }
}

@Composable
private fun ScreenHeader(
    title: String,
    subtitle: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = PhysicsPalette.Paper,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = PhysicsPalette.Aluminum,
        )
    }
}

@Composable
private fun SectionCard(
    section: PhysicsSection,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = PhysicsPalette.Surface,
        border = BorderStroke(1.dp, PhysicsPalette.Border),
        tonalElevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleLarge,
                color = PhysicsPalette.Paper,
            )
            Text(
                text = section.description,
                style = MaterialTheme.typography.bodyMedium,
                color = PhysicsPalette.Aluminum,
            )
            Text(
                text = "${section.formulaCount} формул",
                style = MaterialTheme.typography.labelLarge,
                color = PhysicsPalette.Ruby,
            )
        }
    }
}

@Composable
private fun FormulaScreen(
    uiState: PhysicsUiState,
    contentWidth: androidx.compose.ui.unit.Dp,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onToggleFormula: () -> Unit,
) {
    val formula = uiState.currentFormula ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = contentWidth),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.sizeIn(minWidth = 180.dp),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, PhysicsPalette.Border),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PhysicsPalette.Surface,
                    contentColor = PhysicsPalette.Paper,
                ),
            ) {
                Text("К разделам")
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 320.dp),
            shape = RoundedCornerShape(28.dp),
            color = PhysicsPalette.SurfaceRaised,
            border = BorderStroke(1.5.dp, PhysicsPalette.BorderStrong),
            tonalElevation = 0.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (uiState.isFormulaVisible) {
                    Text(
                        text = formula.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = PhysicsPalette.Ruby,
                    )
                    Text(
                        text = formula.formula,
                        modifier = Modifier.padding(top = 14.dp),
                        style = MaterialTheme.typography.displaySmall,
                        color = PhysicsPalette.Paper,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = formula.explanation,
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = PhysicsPalette.Aluminum,
                        textAlign = TextAlign.Center,
                    )
                } else {
                    Text(
                        text = "${formula.title}\n${formula.prompt}",
                        style = MaterialTheme.typography.cardPrompt,
                        color = PhysicsPalette.Paper,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = onToggleFormula,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, PhysicsPalette.Border),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PhysicsPalette.Surface,
                    contentColor = PhysicsPalette.Paper,
                ),
            ) {
                Text(if (uiState.isFormulaVisible) "скрыть" else "покажи")
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PhysicsPalette.Surface,
                    contentColor = PhysicsPalette.Paper,
                ),
                border = BorderStroke(1.dp, PhysicsPalette.Border),
            ) {
                Text("дальше")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoot() {
    PhysicsFormulasTheme {
        PhysicsRoot(
            uiState = PhysicsUiState(
                isLoading = false,
                sections = listOf(
                    PhysicsSection(
                        id = 1,
                        title = "Механика",
                        description = "Скорость, путь и ускорение.",
                        formulaCount = 3,
                        formulas = listOf(
                            PhysicsFormula(
                                id = 1,
                                sectionId = 1,
                                title = "Второй закон Ньютона",
                                label = "Динамика",
                                prompt = "Как связаны сила, масса и ускорение?",
                                formula = "F = ma",
                                explanation = "Сила равна произведению массы на ускорение.",
                            )
                        ),
                    )
                ),
            ),
            onSectionClick = {},
            onBackClick = {},
            onNextClick = {},
            onToggleFormula = {},
        )
    }
}
