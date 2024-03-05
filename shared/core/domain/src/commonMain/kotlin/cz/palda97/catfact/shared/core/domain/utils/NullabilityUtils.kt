package cz.palda97.catfact.shared.core.domain.utils

import co.touchlab.kermit.Logger

inline fun <T, R> T?.expectNotNullLet(block: (T) -> R): R? {
    return if (this == null) {
        Logger.e("Expected not null value", NullPointerException())
        null
    } else {
        this.let(block)
    }
}
