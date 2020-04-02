package br.com.zup.beagle.service

import br.com.zup.beagle.api.HackathonProductApi
import br.com.zup.beagle.screen.ProductScreenBuilder
import org.springframework.stereotype.Service

@Service
class ProductService(private val integrationService: IntegrationService, private val productApi: HackathonProductApi) {
	fun createProduct(id: String) =
		this.integrationService.handleIntegration { ProductScreenBuilder(this.productApi.getProduct(id))}
}