package com.mairwunnx.projectessentials.warps.models

import com.mairwunnx.projectessentials.core.helpers.MOD_CONFIG_FOLDER
import com.mairwunnx.projectessentials.core.helpers.jsonInstance
import org.apache.logging.log4j.LogManager
import java.io.File

object WarpModelUtils {
    private val warpsConfig = MOD_CONFIG_FOLDER + File.separator + "warps.json"
    private val logger = LogManager.getLogger()
    var warpModel = WarpModel()

    fun loadData() {
        logger.info("Loading world warps data")
        if (!File(warpsConfig).exists()) {
            logger.warn("Warps config not exist! creating it now!")
            File(MOD_CONFIG_FOLDER).mkdirs()
            val defaultConfig = jsonInstance.stringify(
                WarpModel.serializer(), warpModel
            )
            File(warpsConfig).writeText(defaultConfig)
        }
        val warpsConfigRaw = File(warpsConfig).readText()
        warpModel = jsonInstance.parse(
            WarpModel.serializer(), warpsConfigRaw
        )
        logger.info("Loaded warps: ${warpModel.warps.size}")
    }

    fun saveData() {
        File(MOD_CONFIG_FOLDER).mkdirs()
        val spawnConfig = jsonInstance.stringify(
            WarpModel.serializer(), warpModel
        )
        File(warpsConfig).writeText(spawnConfig)
    }
}
