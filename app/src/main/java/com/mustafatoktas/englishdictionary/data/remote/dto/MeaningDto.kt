package com.mustafatoktas.englishdictionary.data.remote.dto

import com.mustafatoktas.englishdictionary.domain.model.Meaning

data class MeaningDto (
    val definitions: List<DefinitionDto>? = null,
    val partOfSpeech: String? = null,
)


fun MeaningDto.toMeaning() : Meaning {
    return Meaning(
        definition = definitionDtoToDefinition(definitions?.get(0)),
        partOfSpeech = partOfSpeech ?: "",
    )
}