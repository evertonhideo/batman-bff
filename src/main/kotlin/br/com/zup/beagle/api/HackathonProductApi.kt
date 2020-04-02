package br.com.zup.beagle.api

import br.com.zup.beagle.dto.request.ProductRequest
import br.com.zup.beagle.dto.response.ProductResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(value = "product", url = "\${hackathon.productservice.url}", path = "/products")
interface HackathonProductApi {

	@PostMapping
	fun addProduct(request: ProductRequest): ProductResponse

	@GetMapping
	fun getProducts(): List<ProductResponse>

	@GetMapping
	fun getProduct(@RequestParam id: String): ProductResponse
}