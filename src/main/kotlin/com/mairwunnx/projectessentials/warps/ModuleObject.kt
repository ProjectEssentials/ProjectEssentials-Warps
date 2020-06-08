@file:Suppress("unused")

package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.core.api.v1.localization.LocalizationAPI
import com.mairwunnx.projectessentials.core.api.v1.module.IModule
import com.mairwunnx.projectessentials.core.api.v1.providers.ProviderAPI
import com.mairwunnx.projectessentials.warps.commands.DelWarpCommand
import com.mairwunnx.projectessentials.warps.commands.SetWarpCommand
import com.mairwunnx.projectessentials.warps.commands.WarpCommand
import com.mairwunnx.projectessentials.warps.configurations.WarpsConfiguration
import com.mairwunnx.projectessentials.warps.configurations.WarpsSettingsConfiguration
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod

@Mod("project_essentials_warps")
class ModuleObject : IModule {
    override val name = this::class.java.`package`.implementationTitle.split(" ").last()
    override val version = this::class.java.`package`.implementationVersion!!
    override val loadIndex = 5
    override fun init() = Unit

    init {
        MinecraftForge.EVENT_BUS.register(this)
        initProviders().also { initLocalization() }
    }

    private fun initProviders() {
        listOf(
            SetWarpCommand::class.java,
            WarpCommand::class.java,
            DelWarpCommand::class.java,
            WarpsConfiguration::class.java,
            WarpsSettingsConfiguration::class.java,
            ModuleObject::class.java
        ).forEach(ProviderAPI::addProvider)
    }

    private fun initLocalization() {
        LocalizationAPI.apply(this.javaClass) {
            mutableListOf(
                "/assets/projectessentialswarps/lang/en_us.json",
                "/assets/projectessentialswarps/lang/ru_ru.json",
                "/assets/projectessentialswarps/lang/de_de.json",
                "/assets/projectessentialswarps/lang/zh_cn.json",
                "/assets/projectessentialswarps/lang/pt_br.json"
            )
        }
    }
}
