package cz.palda97.catfact.shared.core.network.dataaccess

import cz.palda97.catfact.shared.core.network.domain.LocaleProvider

data class NetworkConfiguration(
    val host: String,
    val timeout: Long = DEFAULT_TIMEOUT,
    val localeProvider: LocaleProvider,
)

private const val DEFAULT_TIMEOUT = 30_000L
