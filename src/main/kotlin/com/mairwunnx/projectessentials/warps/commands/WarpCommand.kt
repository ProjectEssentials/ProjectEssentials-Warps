package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.cooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentials.core.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.helpers.throwOnlyPlayerCan
import com.mairwunnx.projectessentials.core.helpers.throwPermissionLevel
import com.mairwunnx.projectessentials.warps.EntryPoint
import com.mairwunnx.projectessentials.warps.EntryPoint.Companion.hasPermission
import com.mairwunnx.projectessentials.warps.models.WarpModel
import com.mairwunnx.projectessentials.warps.models.WarpModelUtils
import com.mairwunnx.projectessentials.warps.sendMessage
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.Minecraft
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.particles.ParticleTypes
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectInstance
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import org.apache.logging.log4j.LogManager
import kotlin.random.Random

object WarpCommand {
    private val aliases = listOf("warp", "ewarp")
    private val logger = LogManager.getLogger()
    private val random = Random

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        logger.info("Register \"/warp\" command")
        applyCommandAliases()

        aliases.forEach { command ->
            dispatcher.register(
                literal<CommandSource>(command).then(
                    Commands.argument(
                        "warp name", StringArgumentType.string()
                    ).executes {
                        return@executes execute(it)
                    }
                )
            )
        }
    }

    private fun applyCommandAliases() {
        if (EntryPoint.cooldownsInstalled) {
            CommandsAliases.aliases["warp"] = aliases.toMutableList()
        }
    }

    private fun execute(c: CommandContext<CommandSource>): Int {
        if (c.isPlayerSender()) {
            val player = c.source.asPlayer()
            if (hasPermission(player, "ess.warp")) {
                val warpName = StringArgumentType.getString(c, "warp name")
                WarpModelUtils.warpModel.warps.firstOrNull {
                    it.name == warpName
                }?.let {
                    moveToWarp(player, it)
                    logger.info("Executed command \"/warp\" from ${player.name.string}")
                    return 0
                }
                sendMessage(c.source, "not_found", warpName)
            } else {
                sendMessage(c.source, "restricted")
                throwPermissionLevel(player.name.string, "warp")
            }
        } else {
            throwOnlyPlayerCan("warp")
        }
        return 0
    }

    private fun moveToWarp(player: ServerPlayerEntity, warp: WarpModel.Warp) {
        val xPos = warp.xPos.toDouble()
        val yPos = warp.yPos.toDouble()
        val zPos = warp.zPos.toDouble()
        val yaw = warp.yaw
        val pitch = warp.pitch
        val dimId = warp.worldId
        val clientWorld = warp.clientWorld
        val targetWorld = player.server.getWorld(
            DimensionType.getById(dimId) ?: DimensionType.OVERWORLD
        )
        if (player.world.worldInfo.worldName == clientWorld) {
            player.teleport(targetWorld, xPos, yPos, zPos, yaw, pitch)
            sendMessage(player.commandSource, "success", warp.name)
        } else {
            sendMessage(player.commandSource, "not_found", warp.name)
            logger.info("Player ${player.name.string} try teleport to not exist warp ${warp.name}")
        }
        val effectEnabled = WarpModelUtils.warpModel.addResistanceEffect
        val effectDuration = WarpModelUtils.warpModel.resistanceEffectDuration
        if (effectEnabled) {
            val effect = Effect.get(11)!!
            val effectInstance = EffectInstance(effect, effectDuration, 5)
            player.addPotionEffect(effectInstance)
        }

        if (WarpModelUtils.warpModel.enableTeleportSound) {
            DistExecutor.runWhenOn(Dist.CLIENT) {
                Runnable {
                    Minecraft.getInstance().world.playSound(
                        xPos, yPos + player.eyeHeight.toDouble(), zPos,
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                        SoundCategory.HOSTILE,
                        1.0f, 1.0f, false
                    )
                    player.entity.playSound(
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                        1.0f, 1.0f
                    )
                }
            }

            DistExecutor.runWhenOn(Dist.DEDICATED_SERVER) {
                Runnable {
                    player.world.playSound(
                        null, xPos, yPos + player.eyeHeight.toDouble(), zPos,
                        SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                        SoundCategory.HOSTILE,
                        1.0f, 1.0f
                    )
                }
            }
        }

        if (WarpModelUtils.warpModel.enableTeleportEffect) {
            DistExecutor.runWhenOn(Dist.CLIENT) {
                Runnable {
                    for (i in 0..200) {
                        Minecraft.getInstance().world.addParticle(
                            ParticleTypes.PORTAL,
                            xPos + (random.nextDouble() - 0.5) * player.width.toDouble(),
                            yPos + random.nextDouble() * player.height.toDouble() - 0.25,
                            zPos + (random.nextDouble() - 0.5) * player.width.toDouble(),
                            (random.nextDouble() - 0.5) * 2.0,
                            -random.nextDouble(),
                            (random.nextDouble() - 0.5) * 2.0
                        )
                    }
                    if (player.world.isRemote) spawnServerParticles(player)
                }
            }
            DistExecutor.runWhenOn(Dist.DEDICATED_SERVER) {
                Runnable {
                    spawnServerParticles(player)
                }
            }
        }
    }

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
}
