package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.core.api.v1.IMCLocalizationMessage
import com.mairwunnx.projectessentials.core.api.v1.IMCProvidersMessage
import com.mairwunnx.projectessentials.core.api.v1.events.ModuleEventAPI
import com.mairwunnx.projectessentials.core.api.v1.events.forge.ForgeEventType
import com.mairwunnx.projectessentials.core.api.v1.events.forge.InterModEnqueueEventData
import com.mairwunnx.projectessentials.core.api.v1.module.IModule
import com.mairwunnx.projectessentials.warps.commands.DelWarpCommand
import com.mairwunnx.projectessentials.warps.commands.SetWarpCommand
import com.mairwunnx.projectessentials.warps.commands.WarpCommand
import com.mairwunnx.projectessentials.warps.configurations.WarpsSettingsConfiguration
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.common.Mod

@Mod("project_essentials_warps")
class ModuleObject : IModule {
    override val name = this::class.java.`package`.implementationTitle.split(" ").last()
    override val version = this::class.java.`package`.implementationVersion!!
    override val loadIndex = 5
    override fun init() = Unit

    init {
        MinecraftForge.EVENT_BUS.register(this)
        ModuleEventAPI.subscribeOn<InterModEnqueueEventData>(
            ForgeEventType.EnqueueIMCEvent
        ) {
            sendLocalizationRequest()
            sendProvidersRequest()
        }
    }

    private fun sendLocalizationRequest() {
        InterModComms.sendTo(
            "project_essentials_core",
            IMCLocalizationMessage
        ) {
            fun() = mutableListOf(
                "/assets/projectessentialswarps/lang/en_us.json",
                "/assets/projectessentialswarps/lang/ru_ru.json",
                "/assets/projectessentialswarps/lang/de_de.json",
                "/assets/projectessentialswarps/lang/zh_cn.json",
                "/assets/projectessentialswarps/lang/pt_br.json"
            )
        }
    }

    private fun sendProvidersRequest() {
        InterModComms.sendTo(
            "project_essentials_core",
            IMCProvidersMessage
        ) {
            fun() = listOf(
                SetWarpCommand::class.java,
                WarpCommand::class.java,
                DelWarpCommand::class.java,
                WarpConfiguration::class.java,
                WarpsSettingsConfiguration::class.java,
                ModuleObject::class.java
            )
        }
    }
}
