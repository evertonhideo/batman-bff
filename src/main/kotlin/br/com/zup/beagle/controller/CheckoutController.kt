package br.com.zup.beagle.controller

import br.com.zup.beagle.constant.CHECKOUT_ENDPOINT
import br.com.zup.beagle.service.CheckoutService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(CHECKOUT_ENDPOINT)
class CheckoutController(private val checkoutService: CheckoutService) {

	@GetMapping
	fun doCheckout() = this.checkoutService.doCheckout()
}