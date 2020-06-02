package com.mairwunnx.projectessentials.warps

import com.mairwunnx.projectessentials.core.api.v1.module.IModule
import net.minecraftforge.fml.common.Mod

@Mod("project_essentials_warps")
class ModuleObject : IModule {
    override val name = this::class.java.`package`.implementationTitle.split(" ").last()
    override val version = this::class.java.`package`.implementationVersion!!
    override val loadIndex = 5
    override fun init() = Unit
}
