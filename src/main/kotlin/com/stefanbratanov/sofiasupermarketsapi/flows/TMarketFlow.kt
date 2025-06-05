package com.stefanbratanov.sofiasupermarketsapi.flows

import com.stefanbratanov.sofiasupermarketsapi.common.Log
import com.stefanbratanov.sofiasupermarketsapi.common.Log.Companion.log
import com.stefanbratanov.sofiasupermarketsapi.interfaces.SupermarketFlow
import com.stefanbratanov.sofiasupermarketsapi.interfaces.UrlProductsExtractor
import com.stefanbratanov.sofiasupermarketsapi.links.TMarketSublinksScraper
import com.stefanbratanov.sofiasupermarketsapi.model.ProductStore
import com.stefanbratanov.sofiasupermarketsapi.model.Supermarket
import com.stefanbratanov.sofiasupermarketsapi.repository.ProductStoreRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import java.util.UUID

@Log
@Component
class TMarketFlow(
        val tmarketSublinksScraper: TMarketSublinksScraper,
        @Qualifier("TMarket") val urlProductsExtractor: UrlProductsExtractor,
        val productStoreRepository: ProductStoreRepository,
) : SupermarketFlow {

  override fun run() {
    val products = tmarketSublinksScraper.getSublinks()
            .flatMap { urlProductsExtractor.extract(it) }

    log.info("Retrieved ${products.size} products")
    log.info("Saving ${getSupermarket().title} products")

    // Generate a random UUID for `id`
    val generatedId = UUID.randomUUID().toString()

    val toSave = ProductStore(
            id = generatedId,
            supermarket = getSupermarket().title,
            products = products
    )

    productStoreRepository.saveIfProductsNotEmpty(toSave)
  }

  override fun getSupermarket(): Supermarket = Supermarket.TMARKET
}
