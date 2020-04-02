package br.com.zup.beagle.dto.response

data class ProductResponse(
		val id: String,
		val sku: String,
		val name: String,
		val shortDescription: String,
		val longDescription: String,
		val imageUrl: String,
		val price: PriceResponse
)

data class PriceResponse(
		val amount: Long,
		val scale: Int,
		val currencyCode: String
)