package br.com.zup.beagle.configuration

import br.com.zup.beagle.serialization.jackson.BeagleActionSerializer
import br.com.zup.beagle.serialization.jackson.BeagleComponentSerializer
import br.com.zup.beagle.serialization.jackson.BeagleScreenBuilderSerializer
import br.com.zup.beagle.serialization.jackson.BeagleScreenSerializer
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {
	@Bean
	fun objectMapper(): ObjectMapper {
		val module = SimpleModule().run {
			this.addSerializer(BeagleComponentSerializer())
			this.addSerializer(BeagleActionSerializer())
			this.addSerializer(BeagleScreenSerializer())
			this.addSerializer(BeagleScreenBuilderSerializer())
		}

		return jacksonObjectMapper().run {
			this.registerModule(module)
			this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			this.enable(SerializationFeature.INDENT_OUTPUT)
		}
	}
}