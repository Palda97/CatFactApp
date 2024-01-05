package cz.dpalecek.catfact.shared.core.network.domain

import java.util.Locale

actual class LocaleProvider {
    actual fun languageCode(): String = Locale.getDefault().language
}
