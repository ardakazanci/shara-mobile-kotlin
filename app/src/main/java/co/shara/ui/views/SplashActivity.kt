package co.shara.ui.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.shara.R
import co.shara.settings.Settings
import co.shara.util.makeStatusBarTransparent
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        makeStatusBarTransparent()

        Handler().postDelayed(
            {
                if (settings.isLoggedIn()) {
                    val intent = Intent(applicationContext, OrderActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            },
            3000 // value in milliseconds
        )
    }

}
