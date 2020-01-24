package ntu.mdp.android.mdptestkotlin.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import ntu.mdp.android.mdptestkotlin.App
import ntu.mdp.android.mdptestkotlin.R
import java.io.Serializable

class ActivityUtil(private val context: Context) {
    /**
     * Starts an activity.
     * @param c Activity class for the activity to be started.
     * @param extras Extras to be appended to the intent. Supports almost every supported type except Parcelable and Serializable array lists. Use traditional way for these two types.
     * @param fade Flag to use fade animation.
     * @param startNew Flag to start the activity as a new task at the top of the stack.
     */
    fun startActivity(c: Class<*>, extras: HashMap<String, Any> = hashMapOf(), fade: Boolean = false, startNew: Boolean = false) {
        val intent = Intent(context, c)

        for (extra in extras) {
            when (val value = extra.value) {
                is Boolean -> intent.putExtra(extra.key, value)
                is BooleanArray -> intent.putExtra(extra.key, value)
                is Byte -> intent.putExtra(extra.key, value)
                is ByteArray -> intent.putExtra(extra.key, value)
                is Char -> intent.putExtra(extra.key, value)
                is CharArray -> intent.putExtra(extra.key, value)
                is CharSequence -> intent.putExtra(extra.key, value)
                is Double -> intent.putExtra(extra.key, value)
                is DoubleArray -> intent.putExtra(extra.key, value)
                is Float -> intent.putExtra(extra.key, value)
                is FloatArray -> intent.putExtra(extra.key, value)
                is Int -> intent.putExtra(extra.key, value)
                is IntArray -> intent.putExtra(extra.key, value)
                is Long -> intent.putExtra(extra.key, value)
                is LongArray -> intent.putExtra(extra.key, value)
                is Short -> intent.putExtra(extra.key, value)
                is ShortArray -> intent.putExtra(extra.key, value)
                is Serializable -> intent.putExtra(extra.key, value)
                is String -> intent.putExtra(extra.key, value)
                is Parcelable -> intent.putExtra(extra.key, value)
                else -> throw RuntimeException("Invalid extra type.")
            }
        }

        if (startNew) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        (context as Activity).startActivity(intent)
        //if (fade || startNew) context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        if (startNew) context.finish()
    }

    /**
     * Finishes an activity.
     * @param fade Flag to use fade animation.
     */
    fun finishActivity(fade: Boolean = true) {
        (context as Activity).finish()
        //if (fade) context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * Sends a snackbar message.
     * @param message Message to be displayed.
     */
    fun sendSnack(message: String) {
        val windowLayout: View = (context as Activity).findViewById(R.id.window_layout)
        val snackBar: Snackbar = Snackbar.make(windowLayout, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    fun setTitle(title: String) {
        val toolbar: Toolbar? = ((context as Activity).findViewById(R.id.toolbar) as Toolbar)
        toolbar?.title = title
    }

    fun sendDialog(message: String, finish: Boolean = false) {
        val builder: AlertDialog.Builder? = (context as Activity).let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(message)
        builder?.setPositiveButton(R.string.ok) { dialog, id ->
            dialog.dismiss()

            if (finish) {
                finishActivity()
            }
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    fun toggleToolbarIcons(show1: Boolean = false, show2: Boolean = false, show3: Boolean = false, show4: Boolean = false, show5: Boolean = false) {
        (context as Activity).findViewById<ImageView>(R.id.toolbar_icon).visibility = if (show1) View.VISIBLE else View.GONE
        context.findViewById<ImageView>(R.id.toolbar_icon2).visibility = if (show2) View.VISIBLE else View.GONE
        context.findViewById<ImageView>(R.id.toolbar_icon3).visibility = if (show3) View.VISIBLE else View.GONE
        context.findViewById<ImageView>(R.id.toolbar_icon4).visibility = if (show4) View.VISIBLE else View.GONE
        context.findViewById<ImageView>(R.id.toolbar_icon5).visibility = if (show5) View.VISIBLE else View.GONE
    }

    fun enableToolbarIcons(show1: Boolean = false, show2: Boolean = false, show3: Boolean = false, show4: Boolean = false, show5: Boolean = false) {
        (context as Activity).findViewById<ImageView>(R.id.toolbar_icon).isEnabled = show1
        context.findViewById<ImageView>(R.id.toolbar_icon2).isEnabled = show2
        context.findViewById<ImageView>(R.id.toolbar_icon3).isEnabled = show3
        context.findViewById<ImageView>(R.id.toolbar_icon4).isEnabled = show4
        context.findViewById<ImageView>(R.id.toolbar_icon5).isEnabled = show5
    }

    fun getProgressStatus(): Int {
        return (context as Activity).findViewById<ProgressBar>(R.id.progress_bar).visibility
    }

    /**
     * Toggles the view state of the progress bar and tint to the visibility specified.
     * @param visibility Visibility state to toggle to.
     * @param opaque If visibility is View.VISIBLE, flag whether to set the alpha value to 1.0f or 0.5f (opaque or translucent).
     */
    fun toggleProgressBar(visibility: Int, opaque: Boolean = true) {
        val progressBar: ProgressBar = (context as Activity).findViewById(R.id.progress_bar)
        val windowTint: View = context.findViewById(R.id.window_tint)

        val animatorListener: AnimatorListenerAdapter = object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                progressBar.visibility = visibility
                windowTint.visibility = visibility
            }
        }

        if (visibility == View.VISIBLE) {
            progressBar.visibility = View.VISIBLE
            windowTint.visibility = View.VISIBLE
            progressBar.alpha = 1.0f
            windowTint.alpha = if (opaque) 1.0f else 0.5f
            return
        }

        progressBar.animate().alpha(0.0f).setDuration(App.ANIMATOR_DURATION).setListener(animatorListener)
        windowTint.animate().alpha(0.0f).setDuration(App.ANIMATOR_DURATION).setListener(animatorListener)
    }
}