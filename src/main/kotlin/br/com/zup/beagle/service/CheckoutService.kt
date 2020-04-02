package br.com.zup.beagle.service

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.api.HackathonCartApi
import br.com.zup.beagle.constant.CHECKOUT_ENDPOINT
import br.com.zup.beagle.constant.PRIMARY_BUTTON
import br.com.zup.beagle.dto.request.CheckoutRequest
import br.com.zup.beagle.screen.CheckoutScreenBuilder
import br.com.zup.beagle.security.util.AuthenticationUtil
import br.com.zup.beagle.widget.ui.Button
import org.springframework.stereotype.Service

@Service
class CheckoutService(private val integrationService: IntegrationService, private val hackathonCartApi: HackathonCartApi) {
	companion object {
		private val button = Button(
			text = "Try again",
			style = PRIMARY_BUTTON,
			action = Navigate(
				path = CHECKOUT_ENDPOINT,
				type = NavigationType.SWAP_VIEW,
				shouldPrefetch = false
			)
		)
	}

	fun doCheckout() = this.integrationService.handleIntegration(button) {
		val cartId = AuthenticationUtil.getCurrentUserData().cartId
		this.hackathonCartApi.checkout(cartId, CheckoutRequest("BRL"), "\${hackathon.team.name}")
		CheckoutScreenBuilder()
	}
}