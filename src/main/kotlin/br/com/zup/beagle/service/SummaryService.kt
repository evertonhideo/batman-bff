package br.com.zup.beagle.service

import br.com.zup.beagle.api.HackathonCartApi
import br.com.zup.beagle.screen.SummaryScreenBuilder
import br.com.zup.beagle.security.util.AuthenticationUtil
import org.springframework.stereotype.Service

@Service
class SummaryService(private val integrationService: IntegrationService, private val hackathonCartApi: HackathonCartApi) {

	fun getSummary() = this.integrationService.handleIntegration {
		val cartId = AuthenticationUtil.getCurrentUserData().cartId
		SummaryScreenBuilder(this.hackathonCartApi.getCart(cartId))
	}
}