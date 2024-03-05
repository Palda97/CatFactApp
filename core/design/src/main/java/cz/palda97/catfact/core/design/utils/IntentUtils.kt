package cz.palda97.catfact.core.design.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import cz.palda97.catfact.core.design.R

fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    startActivityOrToast(intent, R.string.core_error_no_application_to_share)
}

private fun Context.startActivityOrToast(intent: Intent, @StringRes failTextId: Int) {
    runCatching {
        ContextCompat.startActivity(this, intent, null)
    }.getOrElse {
        Toast.makeText(
            this,
            getString(failTextId),
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.openWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivityOrToast(intent, R.string.core_error_no_web_browser)
}
