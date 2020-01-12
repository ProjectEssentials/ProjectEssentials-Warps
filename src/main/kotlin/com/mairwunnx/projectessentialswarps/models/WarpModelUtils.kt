package com.mairwunnx.projectessentialswarps.models

import com.mairwunnx.projectessentialscore.helpers.MOD_CONFIG_FOLDER
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.apache.logging.log4j.LogManager
import java.io.File

@UseExperimental(UnstableDefault::class)
object WarpModelUtils {
    private val warpsConfig = MOD_CONFIG_FOLDER + File.separator + "warps.json"
    private val logger = LogManager.getLogger()
    var warpModel = WarpModel()
    private val json = Json(
        JsonConfiguration(
            strictMode = false,
            allowStructuredMapKeys = true,
            prettyPrint = true
        )
    )

    fun loadData() {
        logger.info("Loading world warps data ...")
        logger.debug("Setup json configuration for parsing ...")
        if (!File(warpsConfig).exists()) {
            logger.warn("Warps config not exist! creating it now!")
            createConfigDirs(MOD_CONFIG_FOLDER)
            val defaultConfig = json.stringify(WarpModel.serializer(), warpModel)
            File(warpsConfig).writeText(defaultConfig)
        }
        val warpsConfigRaw = File(warpsConfig).readText()
        warpModel = json.parse(WarpModel.serializer(), warpsConfigRaw)
        logger.info("Warps config loaded: $warpModel")
    }

    fun saveData() {
        createConfigDirs(MOD_CONFIG_FOLDER)
        val spawnConfig = json.stringify(WarpModel.serializer(), warpModel)
        File(warpsConfig).writeText(spawnConfig)
    }

    @Suppress("SameParameterValue")
    private fun createConfigDirs(path: String) {
        logger.info("Creating config directory for warps ($path)")
        val configDirectory = File(path)
        if (!configDirectory.exists()) configDirectory.mkdirs()
    }
}
