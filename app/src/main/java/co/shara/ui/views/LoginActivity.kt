package co.shara.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.shara.R
import co.shara.data.retrofit.UserLogin
import co.shara.network.NetworkResult
import co.shara.settings.Settings
import co.shara.ui.viewmodel.UserViewModel
import co.shara.util.Util
import co.shara.util.makeSnackbar
import co.shara.util.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModel()
    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        makeStatusBarTransparent()

        textViewSignUp.text = Util.setUnderlinedSpan(
            this,
            getString(R.string.don_t_have_an_account_sign_up),
            getString(R.string.don_t_have_an_account_sign_up_underlined)
        )
    }

    fun validateUser(view: View) {

        val email = etEmail.text.toString().trim()
        val password = etPin.text.toString().trim()

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPin.error = "Password is required"
            etPin.requestFocus()
            return
        }

        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String) {

//        val progressDialog = makeProgressDialog("Signing In...")
//        progressDialog.show()

        lifecycleScope.launch {
            when (val loginResult =
                userViewModel.loginUser(UserLogin(email, password))) {
                is NetworkResult.Success -> {

                    // update the shared pref here
                    settings.setIsLoggedIn(true)

                    val intent = Intent(applicationContext, OrderActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                is NetworkResult.ServerError -> {
                    makeSnackbar(loginResult.errorBody?.message ?: "Failed to login user.")
                }
                NetworkResult.NetworkError -> {
                    makeSnackbar("A network error occurred. Please try again later.")
                }
                NetworkResult.Loading -> {
                }
            }
        }
    }

    fun register(view: View) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }
}
