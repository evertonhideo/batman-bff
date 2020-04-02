package br.com.zup.beagle.dto.request

data class ProductRequest(
		val sku: String,
		val name: String,
		val shortDescription: String,
		val longDescription: String,
		val imageUrl: String,
		val price: PriceRequest
)

data class PriceRequest(
		val amount: Long,
		val scale: Int,
		val currencyCode: String
)