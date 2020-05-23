package co.shara.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.shara.R
import co.shara.settings.Settings
import co.shara.ui.viewmodel.OrderViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModel()
    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


    }
}
