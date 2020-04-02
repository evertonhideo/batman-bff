package br.com.zup.beagle.controller

import br.com.zup.beagle.constant.PRODUCT_ENDPOINT
import br.com.zup.beagle.service.ProductService
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping(PRODUCT_ENDPOINT)
class ProductController(private val productService: ProductService) {
	@GetMapping
	fun getProductById(@PathVariable id: String) = productService.createProduct(id)
}