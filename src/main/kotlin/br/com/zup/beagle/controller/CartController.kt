package br.com.zup.beagle.controller

import br.com.zup.beagle.constant.CART_ENDPOINT
import br.com.zup.beagle.constant.ID
import br.com.zup.beagle.constant.SKU_PARAM
import br.com.zup.beagle.dto.request.QuantityForm
import br.com.zup.beagle.service.CartService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(CART_ENDPOINT)
class CartController(private val cartService: CartService) {
	@PostMapping
	fun addToCart(
		@RequestParam(SKU_PARAM) sku: String,
		@RequestBody quantityForm: QuantityForm
	) = cartService.addToCart(sku, quantityForm.quantity)
}