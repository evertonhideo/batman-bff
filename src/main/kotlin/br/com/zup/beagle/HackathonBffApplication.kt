package br.com.zup.beagle

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableAutoConfiguration
@SpringBootApplication
class HackathonBffApplication

fun main(args: Array<String>) {
	runApplication<HackathonBffApplication>(*args)
}
