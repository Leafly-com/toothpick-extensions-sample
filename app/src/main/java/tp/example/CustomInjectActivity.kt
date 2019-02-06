package tp.example

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import tp.kotlin.R
import tp.kotlin.androidx.inject
import tp.kotlin.bind
import tp.kotlin.module
import tp.kotlin.named
import tp.kotlin.providedBy

class CustomInjectActivity : BaseActivity() {

    private lateinit var lateInitScope: Scope

    // Uses scope from base
    private val injectedContent: InjectContentInterface by inject()
    private val picasso: Picasso by inject()
    // Uses scope from base with extended base module
    private val injectString: String by inject(name = "InjectString")

    // Lazy inject with scope via lambda to use lateinit scope
    private val customClass: CustomClass by inject { scope = lateInitScope }

    // Combine known and lazy load scope via lambda to use lateinit scope
    private val namedCustomClass: CustomClass by inject(name = "WithJavaClass") { scope = lateInitScope }

    private val extendBaseModule = module {
        String::class named "InjectString" providedBy "Inject Me!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.installModules(extendBaseModule)
        lateInitScope = Toothpick.openScopes(application, this, "customscope").apply {
            installModules(CustomModule(toString()))
        }

        findViewById<TextView>(R.id.text1).text = "${injectedContent.content} with $injectString"
        findViewById<ImageView>(R.id.image).setImageResource(picasso.image)
        findViewById<TextView>(R.id.text2).text = "${customClass.name}\n${namedCustomClass.name}"
    }

    override fun onDestroy() {
        Toothpick.closeScope("customscope")
        super.onDestroy()
    }
}

class CustomModule(name: String) : Module() {
    init {
        // Infix works outside Module DSL
        bind(CustomClass::class) providedBy CustomClass(name)
        bind(CustomClass::class.java) named "WithJavaClass" providedBy CustomClass("Named $name")
    }
}

data class CustomClass(val name: String)
