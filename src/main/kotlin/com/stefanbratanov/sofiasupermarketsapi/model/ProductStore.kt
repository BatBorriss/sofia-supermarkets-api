package com.stefanbratanov.sofiasupermarketsapi.model

import jakarta.persistence.*

@Entity
@Table(name = "product_store")
data class ProductStore(
        @Id
        @Column(name = "id", nullable = false)
        val id: String,                  // ← this must be provided
        @Column(name = "supermarket", nullable = false)
        val supermarket: String,
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(
                name = "product_store_products",
                joinColumns = [JoinColumn(name = "product_store_id")]
        )
        @Column(name = "product")
        val products: List<Product> = emptyList()
)
