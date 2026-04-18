package com.example.physicsformulas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun App(appContainer: PhysicsAppContainer) {
    val viewModel = remember(appContainer) { PhysicsViewModel(appContainer.repository) }
    val uiState by viewModel.uiState.collectAsState()
    val dimensions = rememberAppDimensions()

    PhysicsFormulasTheme {
        CompositionLocalProvider(LocalAppDimensions provides dimensions) {
            PhysicsRoot(
                uiState = uiState,
                onSectionClick = viewModel::openSection,
                onBackClick = viewModel::returnToSections,
                onNextClick = viewModel::showNextFormula,
                onPrevClick = viewModel::showPrevFormula,
                onToggleFormula = viewModel::toggleFormulaVisibility,
            )
        }
    }
}

@Composable
private fun PhysicsRoot(
    uiState: PhysicsUiState,
    onSectionClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onToggleFormula: () -> Unit,
) {
    val dims = LocalAppDimensions.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PhysicsPalette.Surface,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dims.screenHorizontalPadding,
                    vertical = dims.screenVerticalPadding,
                ),
            contentAlignment = Alignment.TopCenter,
        ) {
            when {
                uiState.isLoading -> LoadingState()
                uiState.selectedSection == null -> SectionsScreen(
                    sections = uiState.sections,
                    onSectionClick = onSectionClick,
                )
                else -> FormulaScreen(
                    uiState = uiState,
                    onBackClick = onBackClick,
                    onNextClick = onNextClick,
                    onPrevClick = onPrevClick,
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
        CircularProgressIndicator(color = PhysicsPalette.Accent)
    }
}

@Composable
private fun SectionsScreen(
    sections: List<PhysicsSection>,
    onSectionClick: (Long) -> Unit,
) {
    val dims = LocalAppDimensions.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = dims.sectionSpacing),
            verticalArrangement = Arrangement.spacedBy(dims.sectionSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(sections, key = { it.id }) { section ->
                SectionButton(
                    title = section.title,
                    onClick = { onSectionClick(section.id) },
                )
            }
        }
    }
}

@Composable
private fun SectionButton(
    title: String,
    onClick: () -> Unit,
) {
    val dims = LocalAppDimensions.current

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(dims.sectionButtonHeight),
        shape = RoundedCornerShape(dims.buttonCorner),
        border = BorderStroke(2.dp, PhysicsPalette.Accent),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = PhysicsPalette.Card,
            contentColor = PhysicsPalette.TextPrimary,
        ),
    ) {
        Text(
            text = title,
            fontSize = dims.sectionFontSize,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun FormulaScreen(
    uiState: PhysicsUiState,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onToggleFormula: () -> Unit,
    showBackButton: Boolean = true,
) {
    val dims = LocalAppDimensions.current
    val formula = uiState.currentFormula ?: return

    Column(modifier = Modifier.fillMaxSize()) {

        if (showBackButton) {
            Spacer(modifier = Modifier.height(dims.cardTopSpacing))
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(dims.backButtonFraction)
                    .height(dims.buttonHeight),
                shape = RoundedCornerShape(dims.buttonCorner),
                border = BorderStroke(2.dp, PhysicsPalette.Accent),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PhysicsPalette.Card,
                    contentColor = PhysicsPalette.Accent,
                ),
            ) {
                Text(
                    text = "к разделам",
                    fontSize = dims.buttonFontSize,
                    fontStyle = FontStyle.Italic,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { onToggleFormula() })
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {},
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            if (dragAmount < -40f) onNextClick()
                            else if (dragAmount > 40f) onPrevClick()
                        }
                    )
                },
            shape = RoundedCornerShape(dims.cardCorner),
            color = PhysicsPalette.Card,
            border = BorderStroke(2.dp, PhysicsPalette.Accent),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dims.cardInnerPadding),
                contentAlignment = Alignment.Center,
            ) {
                if (uiState.isFormulaVisible) {
                    FormulaImage(
                        imageName = formula.imageName,
                        contentDescription = "${formula.label}. ${formula.prompt}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "КИМ ${formula.kim}",
                            fontSize = dims.kimFontSize,
                            color = PhysicsPalette.Accent,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(dims.cardLabelSpacing))
                        Text(
                            text = formula.label,
                            fontSize = dims.labelFontSize,
                            color = Color(0xFFB97040),
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(dims.cardLabelSpacing))
                        Text(
                            text = formula.prompt,
                            fontSize = dims.hintFontSize,
                            color = PhysicsPalette.Accent,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(dims.cardBottomSpacing))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dims.buttonSpacing),
        ) {
            OutlinedButton(
                onClick = onNextClick,
                modifier = Modifier
                    .weight(1f)
                    .height(dims.buttonHeight),
                shape = RoundedCornerShape(dims.buttonCorner),
                border = BorderStroke(2.dp, PhysicsPalette.Accent),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PhysicsPalette.Card,
                    contentColor = PhysicsPalette.Accent,
                ),
            ) {
                Text(
                    text = "дальше",
                    fontSize = dims.buttonFontSize,
                    fontStyle = FontStyle.Italic,
                )
            }

            Button(
                onClick = onToggleFormula,
                modifier = Modifier
                    .weight(1f)
                    .height(dims.buttonHeight),
                shape = RoundedCornerShape(dims.buttonCorner),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PhysicsPalette.Accent,
                    contentColor = PhysicsPalette.Card,
                ),
            ) {
                Text(
                    text = if (uiState.isFormulaVisible) "скрыть" else "покажи",
                    fontSize = dims.buttonFontSize,
                    fontStyle = FontStyle.Italic,
                )
            }
        }

        Spacer(modifier = Modifier.height(dims.cardBottomSpacing))
    }
}