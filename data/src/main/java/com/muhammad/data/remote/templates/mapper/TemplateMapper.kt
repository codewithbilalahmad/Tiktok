package com.muhammad.data.remote.templates.mapper

import com.muhammad.data.domain.model.Template
import com.muhammad.data.remote.templates.dto.UnsplashItem

fun UnsplashItem.toTemplate() : Template {
    return Template(
        id = id.orEmpty(),
        name = alt_description.orEmpty().ifBlank { description.orEmpty() },
        hint = description.orEmpty(),
        imageUrl = urls?.regular.orEmpty()
    )
}