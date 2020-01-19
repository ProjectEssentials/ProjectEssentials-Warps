package com.mairwunnx.projectessentials.warps.models

import com.mairwunnx.projectessentials.core.extensions.empty
import kotlinx.serialization.Serializable

@Serializable
data class WarpModel(
    var addResistanceEffect: Boolean = true,
    var resistanceEffectDuration: Int = 200,
    var enableTeleportSound: Boolean = true,
    var enableTeleportEffect: Boolean = true,
    var warps: MutableList<Warp> = mutableListOf()
) {
    @Serializable
    data class Warp(
        var name: String = String.empty,
        var clientWorld: String = String.empty,
        var owner: String = String.empty,
        var worldId: Int = -1,
        var xPos: Int = -1,
        var yPos: Int = -1,
        var zPos: Int = -1,
        var yaw: Float = -1f,
        var pitch: Float = -1f
    )
}
