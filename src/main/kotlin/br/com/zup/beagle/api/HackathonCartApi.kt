package br.com.zup.beagle.api

import br.com.zup.beagle.dto.request.CartRequest
import br.com.zup.beagle.dto.request.CheckoutRequest
import br.com.zup.beagle.dto.request.ItemRequest
import br.com.zup.beagle.dto.response.CartResponse
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*


@FeignClient(value = "cart", url = "\${hackathon.cartservice.url}", path = "/carts")
interface HackathonCartApi {

	@PostMapping
	fun createCart(request: CartRequest): CartResponse

	@DeleteMapping("/{id}")
	fun deleteCart(@PathVariable("id") id: String): Response

	@GetMapping("/{id}")
	fun getCart(@PathVariable("id") id: String): CartResponse

	@PatchMapping("/{id}/items")
	fun addItemToCart(@PathVariable("id") id: String, request: ItemRequest): Response

	@DeleteMapping("/{id}/items/{itemId}")
	fun deleteCartItem(@PathVariable("id") id: String, @PathVariable("itemId") itemId: String): Response

	@PostMapping("/{id}/checkout")
	fun checkout(@PathVariable("id") id: String, request: CheckoutRequest, @RequestHeader("x-team-control") teamName: String): Response
}