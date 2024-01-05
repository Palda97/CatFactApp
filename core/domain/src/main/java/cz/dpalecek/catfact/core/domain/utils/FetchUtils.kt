package cz.dpalecek.catfact.core.domain.utils

import cz.dpalecek.catfact.shared.core.domain.domain.DataResource
import cz.dpalecek.catfact.shared.core.domain.domain.toDataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

suspend fun <T> fetch(
    flow: MutableStateFlow<DataResource<T>>,
    onSuccess: suspend (T) -> Unit = {},
    block: suspend () -> Result<T>,
) {
    var resource: DataResource<T>
    withContext(Dispatchers.IO) {
        flow.value = DataResource.Loading
        resource = block().toDataResource()
        flow.value = resource
    }
    resource.getDataOrNull()?.let { onSuccess(it) }
}
