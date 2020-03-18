package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.core.EssBase
import com.mairwunnx.projectessentials.permissions.permissions.PermissionsAPI
import com.mairwunnx.projectessentials.warps.commands.DelWarpCommand
import com.mairwunnx.projectessentials.warps.commands.SetWarpCommand
import com.mairwunnx.projectessentials.warps.commands.WarpCommand
import com.mairwunnx.projectessentials.warps.models.WarpModelUtils
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
        modVersion = "1.14.4-1.2.3"
        logBaseInfo()
        validateForgeVersion()
        MinecraftForge.EVENT_BUS.register(this)
        WarpModelUtils.loadData()
    }

    @SubscribeEvent
    fun onServerStarting(event: FMLServerStartingEvent) {
        registerCommands(event.server.commandManager.dispatcher)
    }

    private fun registerCommands(
        cmdDispatcher: CommandDispatcher<CommandSource>
    ) {
        loadAdditionalModules()
        SetWarpCommand.register(cmdDispatcher)
        DelWarpCommand.register(cmdDispatcher)
        WarpCommand.register(cmdDispatcher)
    }

    private fun loadAdditionalModules() {
        try {
            Class.forName(cooldownAPIClassPath)
            cooldownsInstalled = true
        } catch (_: ClassNotFoundException) {
            // ignored
        }

        try {
            Class.forName(permissionAPIClassPath)
            permissionsInstalled = true
        } catch (_: ClassNotFoundException) {
            // ignored
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent
    fun onServerStopping(it: FMLServerStoppingEvent) {
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
                player.hasPermissionLevel(0)
            }
    }
}
