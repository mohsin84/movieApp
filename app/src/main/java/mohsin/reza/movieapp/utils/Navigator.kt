package mohsin.reza.movieapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.ui.HomeFragment
import mohsin.reza.movieapp.ui.MovieDetailsFragment

class Navigator {
    private var fragmentManager: FragmentManager? = null
    private var containerId: Int = -1

    fun bind(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int
    ) {
        this.fragmentManager = fragmentManager
        this.containerId = containerId
    }

    fun unbind() {
        fragmentManager = null
        containerId = -1
    }

    fun openHome() {
        switchFragment(HomeFragment::class.java.name) {
            HomeFragment()
        }
    }

    fun openMovieDetails(movie: Movie) {
        addFragment(MovieDetailsFragment::class.java.name, MovieDetailsFragment.newInstance(movie))
    }

    private inline fun <reified T : Fragment> addFragment(tag: String, fragment: T) {
        fragmentManager?.addFragment(containerId, tag, fragment)
    }

    private inline fun <reified T : Fragment> switchFragment(
        tag: String,
        changes: FragmentTransaction.() -> T
    ) {
        fragmentManager?.switchFragment(containerId, tag, changes, FragmentTransaction::replace)
    }
}