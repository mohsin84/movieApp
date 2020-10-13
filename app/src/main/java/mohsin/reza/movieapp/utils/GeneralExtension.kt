package mohsin.reza.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.math.roundToInt

inline fun <reified T : Fragment> FragmentManager.switchFragment(
    @IdRes containerId: Int,
    fragmentTag: String? = null,
    changes: FragmentTransaction.() -> T,
    mainAction: FragmentTransaction.(containerId: Int, fragment: Fragment, tag: String) -> FragmentTransaction
): T {
    val tag = fragmentTag ?: T::class.java.name
    val oldFragment = findFragmentByTag(tag)
    if (oldFragment != null && oldFragment is T) {
        val id = (oldFragment.view?.parent as? ViewGroup)?.id
        if (id == containerId) {
            return oldFragment
        }
    }
    val transaction = this.beginTransaction()
    val fragment = (oldFragment as? T)?.takeIf { it.view == null } ?: changes(transaction)
    transaction.mainAction(containerId, fragment, tag).commit()
    return fragment
}

val <T> List<T>?.safeSize: Int
    get() {
        return this?.size ?: 0
    }

fun px3to2HeightFromWidth(widthPx: Int): Int = (widthPx * 1.5).roundToInt()

val Context.isInternetConnected: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
