package com.mairwunnx.projectessentials.warps.configurations

import kotlinx.serialization.Serializable

@Serializable
data class WarpsSettingsConfigurationModel(
    var playSoundOnTeleport: Boolean = true,
    var showEffectOnTeleport: Boolean = true,
    var addResistanceEffect: Boolean = true,
    var resistanceEffectDuration: Int = 200,
    var warpsLimitations: Map<String, String> = mapOf("default" to "6")
)
