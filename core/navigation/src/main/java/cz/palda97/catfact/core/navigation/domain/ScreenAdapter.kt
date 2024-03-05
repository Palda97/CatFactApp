package cz.palda97.catfact.core.navigation.domain

class ScreenAdapter(private val mappers: List<(RouteDestination) -> Screen>) {

    fun adapt(destination: RouteDestination): Screen {
        mappers.forEach { adapter ->
            runCatching { adapter(destination) }.getOrNull()?.let {
                return it
            }
        }
        throw IllegalStateException("Missing adapter for $destination")
    }
}
