package com.mairwunnx.projectessentialswarps.commands

import com.mairwunnx.projectessentialscooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentialscore.extensions.isPlayerSender
import com.mairwunnx.projectessentialscore.extensions.sendMsg
import com.mairwunnx.projectessentialscore.helpers.ONLY_PLAYER_CAN
import com.mairwunnx.projectessentialscore.helpers.PERMISSION_LEVEL
import com.mairwunnx.projectessentialspermissions.permissions.PermissionsAPI
import com.mairwunnx.projectessentialswarps.models.WarpModelUtils
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import org.apache.logging.log4j.LogManager

object DelWarpCommand {
    private val aliases = listOf(
        "delwarp", "edelwarp", "removewarp", "eremovewarp"
    )
    private val logger = LogManager.getLogger()

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        logger.info("    - register \"/delwarp\" command ...")
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
        try {
            Class.forName(
                "com.mairwunnx.projectessentialscooldown.essentials.CommandsAliases"
            )
            CommandsAliases.aliases["delwarp"] = aliases.toMutableList()
            logger.info("        - applying aliases: $aliases")
        } catch (_: ClassNotFoundException) {
            // ignored
        }
    }

    private fun execute(c: CommandContext<CommandSource>): Int {
        if (c.isPlayerSender()) {
            val player = c.source.asPlayer()
            if (PermissionsAPI.hasPermission(player.name.string, "ess.warp.remove")) {
                val warpName = StringArgumentType.getString(c, "warp name")
                WarpModelUtils.warpModel.warps.forEach {
                    if (it.name == warpName && it.owner != player.name.string) {
                        sendMsg("warps", c.source, "warp.remove_not_access")
                        return 0
                    }
                }
                WarpModelUtils.warpModel.warps.removeIf {
                    it.name == warpName
                }.let { result ->
                    if (result) {
                        sendMsg("warps", c.source, "warp.remove.success")
                        logger.info("Executed command \"/delwarp\" from ${player.name.string}")
                    } else {
                        sendMsg("warps", c.source, "warp.not_found")
                    }
                }
            } else {
                sendMsg("warps", c.source, "warp.remove.restricted")
                logger.info(
                    PERMISSION_LEVEL
                        .replace("%0", player.name.string)
                        .replace("%1", "delwarp")
                )
            }
        } else {
            logger.info(ONLY_PLAYER_CAN.replace("%0", "delwarp"))
        }
        return 0
    }
}
