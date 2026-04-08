package com.example.physicsformulas

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = dims.sectionSpacing,
            bottom = dims.sectionSpacing,
        ),
        verticalArrangement = Arrangement.spacedBy(dims.sectionSpacing),
    ) {
        items(sections, key = { it.id }) { section ->
            SectionButton(
                title = section.title.lowercase(),
                onClick = { onSectionClick(section.id) },
            )
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = title,
                fontSize = dims.sectionFontSize,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun FormulaScreen(
    uiState: PhysicsUiState,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onToggleFormula: () -> Unit,
    showBackButton: Boolean = true,
) {
    val dims = LocalAppDimensions.current
    val formula = uiState.currentFormula ?: return

    Column(modifier = Modifier.fillMaxSize()) {

        if (showBackButton) {
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
            Spacer(modifier = Modifier.height(14.dp))
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(dims.cardWeight),
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
                AnimatedContent(
                    targetState = uiState.isFormulaVisible,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                ) { visible ->
                    if (visible) {
                        FormulaImage(
                            imageName = formula.imageName,
                            contentDescription = formula.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        )
                    }else {
                        Text(
                            text = "${formula.title}\n\n${formula.prompt}",
                            fontSize = dims.hintFontSize,
                            color = PhysicsPalette.TextSecondary,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

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
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewSections() {
    PhysicsFormulasTheme {
        CompositionLocalProvider(
            LocalAppDimensions provides dimensionsFor(ScreenType.PHONE, Orientation.PORTRAIT)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = PhysicsPalette.Surface,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    SectionsScreen(
                        sections = listOf(
                            PhysicsSection(1, "Механика", "", 3, emptyList()),
                            PhysicsSection(2, "Молекулярная физика", "", 3, emptyList()),
                            PhysicsSection(3, "Электростатика", "", 3, emptyList()),
                            PhysicsSection(4, "Электродинамика", "", 3, emptyList()),
                            PhysicsSection(5, "Колебания и волны", "", 3, emptyList()),
                            PhysicsSection(6, "Оптика", "", 3, emptyList()),
                        ),
                        onSectionClick = {},
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewFormula() {
    PhysicsFormulasTheme {
        CompositionLocalProvider(
            LocalAppDimensions provides dimensionsFor(ScreenType.PHONE, Orientation.PORTRAIT)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = PhysicsPalette.Surface,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    FormulaScreen(
                        uiState = PhysicsUiState(
                            isLoading = false,
                            sections = listOf(
                                PhysicsSection(
                                    id = 1,
                                    title = "Механика",
                                    description = "",
                                    formulaCount = 1,
                                    formulas = listOf(
                                        PhysicsFormula(
                                            id = 1,
                                            sectionId = 1,
                                            title = "Второй закон Ньютона",
                                            label = "",
                                            prompt = "Как связаны сила, масса и ускорение?",
                                            formula = "F = ma",
                                            explanation = "сила равна произведению массы на ускорение",
                                            imageName = "formula_f_ma.png",
                                        )
                                    ),
                                )
                            ),
                            selectedSectionId = 1,
                        ),
                        onBackClick = {},
                        onNextClick = {},
                        onToggleFormula = {},
                    )
                }
            }
        }
    }
}