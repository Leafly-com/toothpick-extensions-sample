package tp.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import toothpick.Scope
import toothpick.Toothpick
import tp.kotlin.HasScope
import tp.kotlin.inject
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasScope {

    override lateinit var scope: Scope
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = Toothpick.openScopes(application, this)
    }

    override fun onDestroy() {
        Toothpick.closeScope(this)
        super.onDestroy()
    }
}
