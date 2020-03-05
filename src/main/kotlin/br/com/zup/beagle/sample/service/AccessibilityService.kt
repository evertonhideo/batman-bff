package br.com.zup.beagle.sample.service

import br.com.zup.beagle.action.Navigate
import br.com.zup.beagle.action.NavigationType
import br.com.zup.beagle.action.ShowNativeDialog
import br.com.zup.beagle.core.Accessibility
import br.com.zup.beagle.core.Appearance
import br.com.zup.beagle.sample.models.Product
import br.com.zup.beagle.sample.widget.CounterWidget
import br.com.zup.beagle.sample.widget.ProductWidget
import br.com.zup.beagle.widget.Widget
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.layout.*
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.NetworkImage
import br.com.zup.beagle.widget.ui.Text
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccessibilityService() {
    @Autowired
    private lateinit var productService: ProductService

    fun createAccessibilityView() = AccessibilityScreenBuilder(productService).build()
    fun createProductView(sku: String) = ProductScreenBuilder(productService, sku).build()
}

private class AccessibilityScreenBuilder(private val productService: ProductService) : ScreenBuilder {

    override fun build(): Screen {
        return Screen(
                navigationBar = NavigationBar(
                        "Coffee",
                        showBackButton = true,
                        navigationBarItems = listOf(
                                NavigationBarItem(
                                        text = "Minha lista de Compras!",
                                        image = "carrinho",
                                        action = ShowNativeDialog(
                                                title = "Carrinho de Compras",
                                                message = "Sua compra está sendo finalizada!",
                                                buttonText = "OK"
                                        )
                                )
                        )
                ),
                content = ScrollView(
                        children = listOf(
                                textAccessibility(
                                        text = "The Science of Delicious.",
                                        accessibilityLabel = "The Science of Delicious.",
                                        accessible = false
                                ),
                                textAccessibility(
                                        text = "Amazing coffees from around the world! ",
                                        accessibilityLabel = "Amazing coffees from around the world! ",
                                        accessible = false
                                ),
                                Container(
                                        /**
                                        children = listOf(
                                        ProductWidget("Espresso", descricao =  "Blue Ridge Blend", preco = "$4.35"),
                                        ProductWidget("Choco Frappe", descricao =  "Locally Roasted", preco = "$4.35")
                                        )
                                         **/
                                        children = createProductWidget(productService.getProducts())
                                ).applyFlex(Flex(
                                        flexDirection = FlexDirection.COLUMN)
                                ).applyAppearance(Appearance(backgroundColor = "#DDDDDD"))

                        )
                )
        )
    }

    private fun textAccessibility(
            text: String,
            accessibilityLabel: String,
            accessible: Boolean
    ): Widget {
        return Text(
                text = text
        ).applyAccessibility(
                accessibility = Accessibility(
                        accessible = accessible,
                        accessibilityLabel = accessibilityLabel
                )
        ).applyFlex(
                flex = Flex(
                        alignItems = Alignment.CENTER,
                        margin = EdgeValue(
                                top = UnitValue(8.0, UnitType.REAL),
                                bottom = UnitValue(8.0, UnitType.REAL)
                        )
                )
        )
    }


    private fun createProductWidget(productsMap: List<Product>): List<Widget> {
        val productList = mutableListOf<Widget>()

        for (product in productsMap) {
            productList.add(
                    Container(
                            children = listOf(
                                    createProduct(product),
                                    createProduct(product)
                            )
                    ).applyFlex(Flex(
                            flexDirection = FlexDirection.ROW
                    ))
            )
        }
        return productList
    }

    private fun createProduct(product: Product): Widget {
        return ProductWidget(
                product.id,
                product.sku,
                product.name,
                product.shortDescription,
                product.longDescription,
                product.imageUrl,
                product.price.amount.toString()
        ).applyFlex(Flex(size = Size(
                width = UnitValue(100.0, UnitType.PERCENT)),
                margin = EdgeValue(all = UnitValue(value = 10.0, type = UnitType.REAL))))
    }

}

private class ProductScreenBuilder(private val productService: ProductService, private val sku: String) : ScreenBuilder {

    override fun build(): Screen {
        val product = productService.getProductBySku(sku).first()

        return Screen(
                navigationBar = NavigationBar(
                        product.name,
                        showBackButton = true,
                        navigationBarItems = listOf(
                                NavigationBarItem(
                                        text = "Minha lista de Compras!",
                                        image = "carrinho",
                                        action = ShowNativeDialog(
                                                title = "Carrinho de Compras",
                                                message = "Sua compra está sendo finalizada!",
                                                buttonText = "OK"
                                        )
                                )
                        )
                ),
                content = ScrollView(
                        children = listOf(
                                Container(
                                        children = createProductDetails((listOf(product)))
                                ).applyFlex(Flex(
                                        flexDirection = FlexDirection.COLUMN,
                                        justifyContent = JustifyContent.FLEX_START,
                                        alignItems = Alignment.CENTER,
                                        size = Size(width = UnitValue(100.0, UnitType.PERCENT), height = UnitValue(100.0, UnitType.PERCENT)))
                                ).applyAppearance(Appearance(backgroundColor = "#FFFFFF"))

                        )
                )
        )
    }

    private fun textAccessibility(
            text: String,
            accessibilityLabel: String,
            accessible: Boolean
    ): Widget {
        return Text(
                text = text
        ).applyAccessibility(
                accessibility = Accessibility(
                        accessible = accessible,
                        accessibilityLabel = accessibilityLabel
                )
        ).applyFlex(
                flex = Flex(
                        alignItems = Alignment.CENTER,
                        margin = EdgeValue(
                                top = UnitValue(8.0, UnitType.REAL),
                                bottom = UnitValue(8.0, UnitType.REAL)
                        )
                )
        )
    }

    private fun createProductDetails(productsMap: List<Product>): List<Widget> {
        val productList = mutableListOf<Widget>()

        for (product in productsMap) {
            productList.add(
                    Container(children = listOf(
                            NetworkImage(path = product.imageUrl),
                            Container(children = listOf(
                                    Text(product.name),
                                    Spacer(20.0),
                                    Text(product.price.amount.toString())
                            )).applyFlex(Flex(flexDirection = FlexDirection.ROW,
                                    justifyContent = JustifyContent.SPACE_BETWEEN,
                                    alignItems = Alignment.CENTER,
                                    size = Size(width = UnitValue(100.0, UnitType.PERCENT)))),
                            Text(product.longDescription),
                            CounterWidget(),
                            Button(action = Navigate(
                                    path = "/xpto",
                                    type = NavigationType.ADD_VIEW
                            ), text = "Add to cart")
                    )).applyFlex(
                            flex = Flex(size = Size(width = UnitValue(100.0, UnitType.PERCENT), height = UnitValue(100.0, UnitType.PERCENT)),
                                    justifyContent = JustifyContent.SPACE_BETWEEN,
                                    alignItems = Alignment.CENTER
                            ))
            )
        }

        return productList
    }

}