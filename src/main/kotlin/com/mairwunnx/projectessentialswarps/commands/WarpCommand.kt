package com.mairwunnx.projectessentialswarps.commands

import com.mairwunnx.projectessentialscooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentialscore.extensions.isPlayerSender
import com.mairwunnx.projectessentialscore.extensions.sendMsg
import com.mairwunnx.projectessentialscore.helpers.ONLY_PLAYER_CAN
import com.mairwunnx.projectessentialscore.helpers.PERMISSION_LEVEL
import com.mairwunnx.projectessentialswarps.EntryPoint
import com.mairwunnx.projectessentialswarps.EntryPoint.Companion.hasPermission
import com.mairwunnx.projectessentialswarps.models.WarpModel
import com.mairwunnx.projectessentialswarps.models.WarpModelUtils
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.dimension.DimensionType
import org.apache.logging.log4j.LogManager

object WarpCommand {
    private val aliases = listOf("warp", "ewarp")
    private val logger = LogManager.getLogger()

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        logger.info("    - register \"/warp\" command ...")
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
        applyCommandAliases()
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
                sendMsg("warps", c.source, "warp.not_found", warpName)
            } else {
                sendMsg("warps", c.source, "warp.restricted")
                logger.info(
                    PERMISSION_LEVEL
                        .replace("%0", player.name.string)
                        .replace("%1", "warp")
                )
            }
        } else {
            logger.info(ONLY_PLAYER_CAN.replace("%0", "warp"))
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
            sendMsg("warps", player.commandSource, "warp.success", warp.name)
        } else {
            sendMsg("warps", player.commandSource, "warp.not_found", warp.name)
            logger.info("Player ${player.name.string} try teleport to not exist warp ${warp.name}")
        }
    }
}
