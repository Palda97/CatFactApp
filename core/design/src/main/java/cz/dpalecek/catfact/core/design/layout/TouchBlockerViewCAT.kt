package cz.dpalecek.catfact.core.design.layout

import android.app.Dialog
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

@Composable
fun TouchBlockerViewCAT(
    enable: Boolean,
) {
    if (enable) {
        val view = LocalView.current
        val dialog = remember(view) {
            BlockingDialog(
                view
            )
        }
        DisposableEffect(dialog) {
            dialog.show()
            onDispose {
                dialog.dismiss()
            }
        }
    }
}

private class BlockingDialog(
    composeView: View,
) : Dialog(
    ContextThemeWrapper(composeView.context, 0)
) {

    init {
        setCancelable(false)
        val window = window ?: error("Dialog has no window")
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onTouchEvent(event: MotionEvent) = true
}
