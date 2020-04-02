package br.com.zup.beagle.util

import br.com.zup.beagle.dto.response.PriceResponse
import java.math.BigDecimal

fun assemblePrice(price: PriceResponse) = assemblePrice(price.amount, price.scale, price.currencyCode)

fun assemblePrice(price: Long, scale: Int, currency: String) = "${currency}$ ${BigDecimal.valueOf(price, scale)}"

fun assembleEndpoint(baseUrl: String, params: Map<String, String>)
	= "$baseUrl?${params.map { "${it.key}=${it.value}" }.joinToString("&")}"