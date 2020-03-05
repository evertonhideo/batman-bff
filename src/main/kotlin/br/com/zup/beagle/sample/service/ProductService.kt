package br.com.zup.beagle.sample.service

import br.com.zup.beagle.sample.models.Product
import br.com.zup.beagle.sample.widget.ProductWidget
import feign.QueryMap
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@FeignClient("productService", url = "https://7qsoyqlued.execute-api.us-east-1.amazonaws.com/latest")
interface ProductService {

    @GetMapping("/products", consumes = ["application/json"])
    @ResponseBody
    fun getProducts(): List<Product>

    @GetMapping("/products", consumes = ["application/json"])
    @ResponseBody
    fun getProductBySku(@RequestParam sku: String): List<Product>

}