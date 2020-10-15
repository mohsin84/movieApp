package mohsin.reza.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mohsin.reza.movieapp.utils.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        App.app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.bind(
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_content_container
        )
        if (savedInstanceState == null) {
            navigator.openHome()
        }
    }

    override fun onDestroy() {
        navigator.unbind()
        super.onDestroy()
    }
}