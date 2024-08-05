package com.mustafatoktas.englishdictionary.data.remote.dto

import com.mustafatoktas.englishdictionary.domain.model.Definition

data class DefinitionDto (
    val definition: String? = null,
    val example: String? = null,
)


fun definitionDtoToDefinition (definitionDto: DefinitionDto?) : Definition {
    return Definition(
        definition = definitionDto?.definition ?: "",
        example = definitionDto?.example ?: "",
    )
}
