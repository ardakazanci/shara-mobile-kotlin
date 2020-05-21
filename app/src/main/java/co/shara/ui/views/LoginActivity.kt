package co.shara.ui.views

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.shara.R
import co.shara.util.Util
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        textViewSignUp.text = Util.setUnderlinedSpan(
            this,
            getString(R.string.don_t_have_an_account_sign_up),
            getString(R.string.don_t_have_an_account_sign_up_underlined)
        )
    }
}
