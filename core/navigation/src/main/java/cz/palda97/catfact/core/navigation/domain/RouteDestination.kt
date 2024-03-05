package cz.palda97.catfact.core.navigation.domain

interface RouteDestination {
    val name: String
    fun routeName() = "${this.javaClass.canonicalName}.$name"
}
