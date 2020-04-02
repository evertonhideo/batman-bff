package br.com.zup.beagle.controller

import br.com.zup.beagle.constant.HOME_ENDPOINT
import br.com.zup.beagle.service.HomeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(HOME_ENDPOINT)
class HomeController(private val homeService: HomeService) {
	@GetMapping
	fun getHome() = homeService.createHome()
}