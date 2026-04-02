package com.example.physicsformulas

import com.example.physicsformulas.db.PhysicsDatabase

class PhysicsRepository(
    private val database: PhysicsDatabase,
) {
    fun getCatalog(): List<PhysicsSection> {
        val queries = database.physicsDataQueries
        val sections = queries.selectSections().executeAsList()

        return sections.map { section ->
            val formulas = queries.selectFormulasBySection(section.id).executeAsList().map { formula ->
                PhysicsFormula(
                    id = formula.id,
                    sectionId = formula.section_id,
                    title = formula.title,
                    label = formula.label,
                    prompt = formula.prompt,
                    formula = formula.formula,
                    explanation = formula.explanation,
                )
            }

            PhysicsSection(
                id = section.id,
                title = section.title,
                description = section.description,
                formulaCount = section.formulaCount.toInt(),
                formulas = formulas,
            )
        }
    }
}
