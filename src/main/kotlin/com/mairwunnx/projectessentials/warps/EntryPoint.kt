package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.warps.commands.DelWarpCommand
import com.mairwunnx.projectessentials.warps.commands.SetWarpCommand
import com.mairwunnx.projectessentials.warps.commands.WarpCommand
import com.mairwunnx.projectessentials.warps.models.WarpModelUtils
import com.mairwunnx.projectessentialscore.EssBase
import com.mairwunnx.projectessentialspermissions.permissions.PermissionsAPI
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager

@Suppress("unused")
@Mod("project_essentials_warps")
class EntryPoint : EssBase() {
    private val logger = LogManager.getLogger()

    init {
        modInstance = this
        modVersion = "1.14.4-1.1.0.0"
        logBaseInfo()
        validateForgeVersion()
        logger.debug("Register event bus for $modName mod ...")
        MinecraftForge.EVENT_BUS.register(this)
        logger.info("Loading $modName warps config ...")
        WarpModelUtils.loadData()
    }

    @SubscribeEvent
    fun onServerStarting(event: FMLServerStartingEvent) {
        logger.info("$modName starting mod loading ...")
        registerCommands(event.server.commandManager.dispatcher)
    }

    private fun registerCommands(
        cmdDispatcher: CommandDispatcher<CommandSource>
    ) {
        logger.info("Command registering is starting ...")
        loadAdditionalModules()
        SetWarpCommand.register(cmdDispatcher)
        DelWarpCommand.register(cmdDispatcher)
        WarpCommand.register(cmdDispatcher)
    }

    private fun loadAdditionalModules() {
        try {
            Class.forName(
                "com.mairwunnx.projectessentialscooldown.essentials.CommandsAliases"
            )
            cooldownsInstalled = true
        } catch (_: ClassNotFoundException) {
            try {
                Class.forName(
                    "com.mairwunnx.projectessentials.cooldown.essentials.CommandsAliases"
                )
                cooldownsInstalled = true
                logger.info("Cooldowns module found!")
            } catch (_: ClassNotFoundException) {
                // ignored
            }
        }

        try {
            Class.forName(
                "com.mairwunnx.projectessentials.permissions.permissions.PermissionsAPI"
            )
            permissionsInstalled = true
            logger.info("Permissions module found!")
        } catch (_: ClassNotFoundException) {
            // ignored
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent
    fun onServerStopping(it: FMLServerStoppingEvent) {
        logger.info("Shutting down $modName mod ...")
        logger.info("Saving warps config ...")
        WarpModelUtils.saveData()
    }

    companion object {
        lateinit var modInstance: EntryPoint
        var cooldownsInstalled: Boolean = false
        var permissionsInstalled: Boolean = false

        fun hasPermission(player: ServerPlayerEntity, node: String): Boolean =
            if (permissionsInstalled) {
                PermissionsAPI.hasPermission(player.name.string, node)
            } else {
                player.server.opPermissionLevel >= 2
            }
    }
}
