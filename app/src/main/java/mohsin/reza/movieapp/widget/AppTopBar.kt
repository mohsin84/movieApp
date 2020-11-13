package mohsin.reza.movieapp.widget

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

import mohsin.reza.movieapp.databinding.AppTopBarBinding

class AppTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = AppTopBarBinding.inflate(LayoutInflater.from(context), this)

    var titleText: String = ""
        set(value) {
            field = value
            binding.topBarTitle.isVisible = value.isNotEmpty()
            binding.topBarTitle.text = value
        }

    var backButtonVisibility: Boolean = false
        set(value) {
            field = value
            binding.topBarBackBtn.isVisible = value
        }


    init {
        backButtonVisibility = false
        binding.topBarBackBtn.setOnClickListener {
            var currentContext = context
            while (currentContext is ContextWrapper && currentContext !is Activity) {
                currentContext = currentContext.baseContext
            }
            (currentContext as? Activity)?.onBackPressed()
        }
    }
}
