package com.mairwunnx.projectessentials.warps.configurations

import kotlinx.serialization.Serializable

@Serializable
data class WarpsConfigurationModel(
    val warps: MutableList<Warp> = mutableListOf()
) {
    @Serializable
    data class Warp(
        var name: String,
        var owner: String,
        var worldId: Int = -1,
        var xPos: Int = -1,
        var yPos: Int = -1,
        var zPos: Int = -1,
        var yaw: Float = -1f,
        var pitch: Float = -1f
    )
}
