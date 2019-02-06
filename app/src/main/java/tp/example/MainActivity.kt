package tp.example

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import tp.kotlin.R
import tp.kotlin.androidx.inject

class MainActivity : BaseActivity() {

    private val injectedContent: InjectContentInterface by inject()
    private val prefs: SharedPreferences by inject(annotationName = NamedPrefs::class)
    private val otherPrefs: SharedPreferences by inject(name = "NamedPrefs")
    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs.edit().putString("content", injectedContent.content).apply()
        findViewById<TextView>(R.id.text1).apply {
            text = otherPrefs.getString("content", "")
            setOnClickListener { startActivity(Intent(this@MainActivity, PresenterActivity::class.java)) }
        }
        findViewById<ImageView>(R.id.image).apply {
            setImageResource(picasso.image)
            setOnClickListener { startActivity(Intent(this@MainActivity, CustomInjectActivity::class.java)) }
        }
    }
}
