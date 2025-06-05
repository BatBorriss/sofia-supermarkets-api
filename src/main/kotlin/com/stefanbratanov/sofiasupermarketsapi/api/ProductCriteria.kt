package com.stefanbratanov.sofiasupermarketsapi.api

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
data class ProductCriteria(
        @Parameter(description = "Supermarkets to get the products from")
        var supermarket: List<String>? = null,     // ← default to null

        @Parameter(description = "Show only offers")
        var offers: Boolean = false                // ← default already provided
)
