package br.com.zup.beagle.controller

import br.com.zup.beagle.constant.SUMMARY_ENDPOINT
import br.com.zup.beagle.service.SummaryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(SUMMARY_ENDPOINT)
class SummaryController(private val summaryService: SummaryService) {

	@GetMapping
	fun getSummary() = this.summaryService.getSummary()
}