package cz.palda97.catfact.shared.core.network.dataaccess

interface NetworkDto<T> {
    fun toDomainData(): T
}
