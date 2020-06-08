package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.core.api.v1.commands.back.BackLocationAPI
import com.mairwunnx.projectessentials.core.api.v1.configuration.ConfigurationAPI.getConfigurationByName
import com.mairwunnx.projectessentials.core.api.v1.extensions.playSound
import com.mairwunnx.projectessentials.warps.configurations.WarpsConfiguration
import com.mairwunnx.projectessentials.warps.configurations.WarpsConfigurationModel
import com.mairwunnx.projectessentials.warps.configurations.WarpsSettingsConfiguration
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.particles.ParticleTypes
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance
import net.minecraft.util.SoundEvents
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import kotlin.random.Random

val warpsConfiguration by lazy {
    getConfigurationByName<WarpsConfiguration>("warps").take()
}

val warpsSettingsConfiguration by lazy {
    getConfigurationByName<WarpsSettingsConfiguration>("warps-settings").take()
}

fun teleportToWarp(player: ServerPlayerEntity, warp: WarpsConfigurationModel.Warp) {
    BackLocationAPI.commit(player)
    val world = player.server.getWorld(
        DimensionType.getById(warp.worldId) ?: DimensionType.OVERWORLD
    )
    player.teleport(world, warp.xPos + 0.5, warp.yPos + 0.5, warp.zPos + 0.5, warp.yaw, warp.pitch)
    if (warpsSettingsConfiguration.playSoundOnTeleport) {
        player.playSound(player, SoundEvents.ENTITY_ENDERMAN_TELEPORT)
    }
    if (warpsSettingsConfiguration.showEffectOnTeleport) {
        DistExecutor.runWhenOn(Dist.CLIENT) {
            Runnable {
                for (i in 0..200) {
                    Minecraft.getInstance().world?.addParticle(
                        ParticleTypes.PORTAL,
                        player.positionVec.x + (random.nextDouble() - 0.5) * player.width.toDouble(),
                        player.positionVec.y + random.nextDouble() * player.height.toDouble() - 0.25,
                        player.positionVec.z + (random.nextDouble() - 0.5) * player.width.toDouble(),
                        (random.nextDouble() - 0.5) * 2.0,
                        -random.nextDouble(),
                        (random.nextDouble() - 0.5) * 2.0
                    )
                }
                if (player.world.isRemote) spawnServerParticles(player)
            }
        }
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER) { Runnable { spawnServerParticles(player) } }
    }
    if (warpsSettingsConfiguration.addResistanceEffect) {
        player.addPotionEffect(
            EffectInstance(Effect.get(11)!!, warpsSettingsConfiguration.resistanceEffectDuration, 5)
        )
    }
}

private val random = Random
private fun spawnServerParticles(player: ServerPlayerEntity) {
    for (i in 0..200) {
        player.serverWorld.spawnParticle(
            ParticleTypes.PORTAL,
            player.posX + (random.nextDouble() - 0.5) * player.width.toDouble(),
            player.posY + random.nextDouble() * player.height.toDouble() - 0.25,
            player.posZ + (random.nextDouble() - 0.5) * player.width.toDouble(),
            1,
            -0.006, -0.006, 0.0,
            (random.nextDouble() - 0.5) * 2.0
        )
    }
}
