package com.stefanbratanov.sofiasupermarketsapi.flows

import com.stefanbratanov.sofiasupermarketsapi.brochure.FantasticoBrochureDownloader
import com.stefanbratanov.sofiasupermarketsapi.common.Log
import com.stefanbratanov.sofiasupermarketsapi.common.Log.Companion.log
import com.stefanbratanov.sofiasupermarketsapi.interfaces.PdfProductsExtractor
import com.stefanbratanov.sofiasupermarketsapi.interfaces.SupermarketFlow
import com.stefanbratanov.sofiasupermarketsapi.model.ProductStore
import com.stefanbratanov.sofiasupermarketsapi.model.Supermarket
import com.stefanbratanov.sofiasupermarketsapi.repository.ProductStoreRepository
import java.nio.file.Files
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.UUID

@Log
@Component
class FantasticoFlow(
        val fantasticoBrochureDownloader: FantasticoBrochureDownloader,
        @Qualifier("Fantastico") val pdfProductsExtractor: PdfProductsExtractor,
        val productStoreRepository: ProductStoreRepository,
) : SupermarketFlow {

  override fun run() {
    // ① Download PDF brochures
    val brochures = fantasticoBrochureDownloader.download()

    // ② Extract and tag products
    val products =
            brochures
                    .map {
                      pdfProductsExtractor.extract(it.path).map { product ->
                        product.copy(
                                validFrom = it.validFrom,
                                validUntil = it.validUntil
                        )
                      }
                    }
                    .flatten()

    // ③ Clean up local files
    brochures.forEach { Files.delete(it.path) }

    log.info("Retrieved ${products.size} products")
    log.info("Saving ${getSupermarket().title} products")

    // ④ Generate a random UUID for `id`
    val generatedId = UUID.randomUUID().toString()

    // ⑤ Now pass that `id` into the ProductStore constructor
    val toSave = ProductStore(
            id = generatedId,
            supermarket = getSupermarket().title,
            products = products
    )

    productStoreRepository.saveIfProductsNotEmpty(toSave)
  }

  override fun getSupermarket(): Supermarket = Supermarket.FANTASTICO
}
