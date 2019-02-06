package tp.example

import android.app.Application
import android.os.Bundle
import android.widget.TextView
import toothpick.Scope
import tp.kotlin.HasScope
import tp.kotlin.R
import tp.kotlin.androidx.inject
import javax.inject.Inject
import tp.kotlin.inject as inlineInject

class Presenter @Inject constructor(override val scope: Scope) : HasScope {

    fun init(view: PresenterActivity) {
        // Inline inject dependency to prevent leaking into presenter that doesn't need them
        val externalResult = NeedsApplication(application = inlineInject()).execute()
        view.setText(externalResult)
    }

    class NeedsApplication(private val application: Application) {
        fun execute(): String {
            // Do something with application
            return "needs app ${application.getString(R.string.app_name)}"
        }
    }
}

class PresenterActivity : BaseActivity() {

    private val presenter: Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init(this)
    }

    fun setText(text: String) {
        findViewById<TextView>(R.id.text1).text = text
    }
}
