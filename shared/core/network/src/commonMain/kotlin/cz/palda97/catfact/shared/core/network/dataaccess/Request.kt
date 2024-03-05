package cz.palda97.catfact.shared.core.network.dataaccess

import kotlinx.serialization.Serializable

@Serializable
interface Request

object EmptyRequest : Request
