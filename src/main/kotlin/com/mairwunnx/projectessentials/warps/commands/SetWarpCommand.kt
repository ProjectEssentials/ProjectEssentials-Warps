package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.cooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentials.core.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.extensions.sendMsg
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
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import org.apache.logging.log4j.LogManager

object SetWarpCommand {
    private val aliases = listOf(
        "setwarp", "esetwarp", "createwarp", "ecreatewarp"
    )
    private val logger = LogManager.getLogger()

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        logger.info("Register \"/setwarp\" command")
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
            CommandsAliases.aliases["setwarp"] = aliases.toMutableList()
        }
    }

    private fun execute(c: CommandContext<CommandSource>): Int {
        if (c.isPlayerSender()) {
            val player = c.source.asPlayer()
            if (hasPermission(player, "ess.warp.set")) {
                val warpName = StringArgumentType.getString(c, "warp name")
                val clientWorld = c.source.world.worldInfo.worldName
                val worldId = c.source.world.worldType.id
                val xPos = player.posX.toInt()
                val yPos = player.posY.toInt()
                val zPos = player.posZ.toInt()
                val yaw = player.rotationYaw
                val pitch = player.rotationPitch
                WarpModelUtils.warpModel.warps.forEach {
                    if (it.name == warpName) {
                        sendMsg("warps", c.source, "warp.exist", warpName)
                        return 0
                    }
                }
                WarpModelUtils.warpModel.warps.add(
                    WarpModel.Warp(
                        warpName, clientWorld, player.name.string, worldId,
                        xPos, yPos, zPos, yaw, pitch
                    )
                )
                sendMessage(c.source, "set.success", warpName)
                logger.info("Executed command \"/setwarp\" from ${player.name.string}")
            } else {
                sendMessage(c.source, "set.restricted")
                throwPermissionLevel(player.name.string, "setwarp")
            }
        } else {
            throwOnlyPlayerCan("setwarp")
        }
        return 0
    }
}
