package com.mairwunnx.projectessentialswarps.models

import com.mairwunnx.projectessentialscore.extensions.empty
import kotlinx.serialization.Serializable

@Serializable
data class WarpModel(
    var warps: MutableList<Warp> = mutableListOf()
) {
    @Serializable
    data class Warp(
        var name: String = String.empty,
        var clientWorld: String = String.empty,
        var worldId: Int = -1,
        var xPos: Int = -1,
        var yPos: Int = -1,
        var zPos: Int = -1,
        var yaw: Float = -1f,
        var pitch: Float = -1f
    )
}
