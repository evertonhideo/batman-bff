package br.com.zup.beagle.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(private val demoService: DemoService) {
    @GetMapping("/demo")
    fun getDemo() = demoService.createDemo()

    @GetMapping("/demo/custom")
    fun getDemoCustom() = demoService.createDemoCustom()
}
