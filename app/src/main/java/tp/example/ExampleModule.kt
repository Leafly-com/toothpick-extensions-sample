package tp.example

import android.app.Application
import android.content.SharedPreferences
import toothpick.smoothie.provider.SharedPreferencesProvider
import tp.kotlin.R
import tp.kotlin.module
import tp.kotlin.providedBy
import javax.inject.Provider
import javax.inject.Qualifier

@Qualifier
annotation class NamedPrefs

fun exampleModule(application: Application) = module {
    // Provided by Provider
    Picasso::class providedBy PicassoProvider()         // Instance
    // Picasso::class providedBy PicassoProvider::class // or Class

    // Provide singleton by provider
    // Picasso::class singletonProvidedBy PicassoProvider()      // Instance
    // Picasso::class singletonProvidedBy PicassoProvider::class // or Class

    // Provide with instance
    // Picasso::class providedBy Picasso.Builder().build()

    // Provide interface as actual implementation
    InjectContentInterface::class providedAs InjectedContent::class

    // Provided named version
    SharedPreferences::class named NamedPrefs::class providedBy SharedPreferencesProvider(application, "NamedPrefs")
    SharedPreferences::class named "NamedPrefs" providedBy SharedPreferencesProvider(application, "NamedPrefs")
}

// Annotations here still work too
// @Singleton @ProvidesSingletonInScope
class PicassoProvider : Provider<Picasso> {
    override fun get(): Picasso {
        return Picasso.Builder().apply {
            image = R.drawable.ic_launcher_foreground
        }.build()
    }
}
