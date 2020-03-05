package br.com.zup.beagle.sample

import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.ui.Text
import org.springframework.stereotype.Service

@Service
class DemoService {
    fun createDemo() = Screen(content = Text("Hello, world!"))
    fun createDemoCustom() = Screen(content = CustomText("world"))
}
