package cz.dpalecek.catfact.core.navigation.domain

interface RouteDestination {
    val name: String
    fun routeName() = "${this.javaClass.canonicalName}.$name"
}
