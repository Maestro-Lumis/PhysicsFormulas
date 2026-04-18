package com.example.physicsformulas

import com.example.physicsformulas.db.PhysicsDatabase

class PhysicsRepository(
    private val database: PhysicsDatabase,
) {
    fun getCatalog(): List<PhysicsSection> {
        val queries = database.physicsDataQueries
        val sections = queries.selectSections().executeAsList()

        return sections.map { section ->

            val formulas = queries
                .selectFormulasBySection(section.id)
                .executeAsList()
                .map { formula ->

                    PhysicsFormula(
                        id = formula.id,
                        sectionId = formula.section_id,
                        kim = formula.kim,
                        label = formula.label,
                        prompt = formula.prompt,
                        explanation = formula.explanation,
                        imageName = formula.image_name
                    )
                }

            PhysicsSection(
                id = section.id,
                title = section.title,
                description = section.description,
                formulaCount = section.formulaCount.toInt(),
                formulas = formulas
            )
        }
    }
}