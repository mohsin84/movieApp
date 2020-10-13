package mohsin.reza.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import mohsin.reza.movieapp.ui.HomeFragment
import mohsin.reza.movieapp.utils.switchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.switchFragment(
            R.id.main_content_container,
            HomeFragment::class.java.name,
            { HomeFragment() },
            FragmentTransaction::replace
        )
    }
}