package cz.dpalecek.catfact.shared.core.network.dataaccess

interface NetworkDto<T> {
    fun toDomainData(): T
}
