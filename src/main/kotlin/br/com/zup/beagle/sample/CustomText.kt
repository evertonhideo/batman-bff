package br.com.zup.beagle.sample

import br.com.zup.beagle.annotation.RegisterWidget
import br.com.zup.beagle.widget.Widget

@RegisterWidget
class CustomText(text: String): Widget() {
    val content = "Hello, $text!"
}
