package co.shara.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.shara.R
import co.shara.util.Util
import co.shara.util.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_register)

        makeStatusBarTransparent()

        textViewSignIn.text = Util.setUnderlinedSpan(
            this,
            getString(R.string.don_t_have_an_account_sign_in),
            getString(R.string.don_t_have_an_account_sign_in_underlined)
        )

    }

    fun login(view: View) {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }
}
