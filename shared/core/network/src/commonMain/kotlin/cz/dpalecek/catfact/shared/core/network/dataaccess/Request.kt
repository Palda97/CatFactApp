package cz.dpalecek.catfact.shared.core.network.dataaccess

import kotlinx.serialization.Serializable

@Serializable
interface Request

object EmptyRequest : Request
