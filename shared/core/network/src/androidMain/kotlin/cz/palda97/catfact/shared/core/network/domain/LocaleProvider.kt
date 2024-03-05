package cz.palda97.catfact.shared.core.network.domain

import java.util.Locale

actual class LocaleProvider {
    actual fun languageCode(): String = Locale.getDefault().language
}
