package com.stefanbratanov.sofiasupermarketsapi.flows

import com.stefanbratanov.sofiasupermarketsapi.common.Log
import com.stefanbratanov.sofiasupermarketsapi.common.Log.Companion.log
import com.stefanbratanov.sofiasupermarketsapi.interfaces.SupermarketFlow
import com.stefanbratanov.sofiasupermarketsapi.interfaces.UrlProductsExtractor
import com.stefanbratanov.sofiasupermarketsapi.links.BillaSublinksScraper
import com.stefanbratanov.sofiasupermarketsapi.model.ProductStore
import com.stefanbratanov.sofiasupermarketsapi.model.Supermarket
import com.stefanbratanov.sofiasupermarketsapi.repository.ProductStoreRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import java.util.UUID

@Log
@Component
class BillaFlow(
        val billaSublinksScraper: BillaSublinksScraper,
        @Qualifier("Billa") val urlProductsExtractor: UrlProductsExtractor,
        val productStoreRepository: ProductStoreRepository,
) : SupermarketFlow {

  override fun run() {
    val products = billaSublinksScraper.getSublinks()
            .flatMap { urlProductsExtractor.extract(it) }

    log.info("Retrieved ${products.size} products")
    log.info("Saving ${getSupermarket().title} products")

    // ① Generate a random UUID (or any string‐based key) here
    val generatedId = UUID.randomUUID().toString()

    // ② Pass that `id` along with supermarket and products
    val toSave = ProductStore(
            id = generatedId,
            supermarket = getSupermarket().title,
            products = products
    )

    productStoreRepository.saveIfProductsNotEmpty(toSave)
  }

  override fun getSupermarket(): Supermarket {
    return Supermarket.BILLA
  }
}

