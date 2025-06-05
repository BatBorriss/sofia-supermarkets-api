package com.stefanbratanov.sofiasupermarketsapi.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

@Embeddable
data class Product(
        @Column(name = "name", nullable = false)
        val name: String,

        @Column(name = "quantity")
        val quantity: String? = null,

        @Column(name = "price")
        val price: Double? = null,

        @Column(name = "old_price")
        val oldPrice: Double? = null,

        @Column(name = "category")
        val category: String? = null,

        @Column(name = "pic_url")
        val picUrl: String? = null,

        @JsonDeserialize(using = LocalDateDeserializer::class)
        @JsonSerialize(using = LocalDateSerializer::class)
        @Column(name = "valid_from")
        val validFrom: LocalDate? = null,

        @JsonDeserialize(using = LocalDateDeserializer::class)
        @JsonSerialize(using = LocalDateSerializer::class)
        @Column(name = "valid_until")
        val validUntil: LocalDate? = null
)
