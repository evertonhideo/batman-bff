package br.com.zup.beagle.service

import br.com.zup.beagle.api.HackathonProductApi
import br.com.zup.beagle.screen.HomeScreenBuilder
import org.springframework.stereotype.Service

@Service
class HomeService(private val integrationService: IntegrationService, private val productApi: HackathonProductApi) {
	fun createHome() =
		this.integrationService.handleIntegration { HomeScreenBuilder(this.productApi.getProducts()) }
}