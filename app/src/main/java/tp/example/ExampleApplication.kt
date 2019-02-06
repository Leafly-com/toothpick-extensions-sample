package tp.example

import android.app.Application
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule

class ExampleApplication : Application() {
    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()
        scope = Toothpick.openScope(this)
        scope.installModules(SmoothieApplicationModule(this), exampleModule(this))
    }
}
