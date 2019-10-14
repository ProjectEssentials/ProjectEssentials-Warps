package com.mairwunnx.projectessentialswarps

import com.mairwunnx.projectessentialscore.EssBase
import com.mairwunnx.projectessentialswarps.commands.SetWarpCommand
import com.mairwunnx.projectessentialswarps.commands.WarpCommand
import com.mairwunnx.projectessentialswarps.models.WarpModelUtils
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
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
        modVersion = "1.14.4-1.0.0.0"
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
        SetWarpCommand.register(cmdDispatcher)
        WarpCommand.register(cmdDispatcher)
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent
    fun onServerStopping(it: FMLServerStoppingEvent) {
        logger.info("Shutting down $modName mod ...")
        logger.info("    - Saving warps config ...")
        WarpModelUtils.saveData()
    }

    companion object {
        lateinit var modInstance: EntryPoint
    }
}
