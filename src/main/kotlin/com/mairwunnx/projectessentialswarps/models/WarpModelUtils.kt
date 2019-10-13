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
            encodeDefaults = true,
            strictMode = true,
            unquoted = false,
            allowStructuredMapKeys = true,
            prettyPrint = true,
            useArrayPolymorphism = false
        )
    )

    fun loadData() {
        logger.info("    - loading world warps data ...")
        logger.debug("        - setup json configuration for parsing ...")
        if (!File(warpsConfig).exists()) {
            logger.warn("        - warps config not exist! creating it now!")
            createConfigDirs(MOD_CONFIG_FOLDER)
            val defaultConfig = json.stringify(
                WarpModel.serializer(),
                warpModel
            )
            File(warpsConfig).writeText(defaultConfig)
        }
        val warpsConfigRaw = File(warpsConfig).readText()
        warpModel = Json.parse(WarpModel.serializer(), warpsConfigRaw)
        logger.info("Warps config loaded: $warpModel")
    }

    fun saveData() {
        logger.info("    - saving warps data ...")
        createConfigDirs(MOD_CONFIG_FOLDER)
        val spawnConfig = json.stringify(
            WarpModel.serializer(),
            warpModel
        )
        File(warpsConfig).writeText(spawnConfig)
    }

    @Suppress("SameParameterValue")
    private fun createConfigDirs(path: String) {
        logger.info("        - creating config directory for warps ($path)")
        val configDirectory = File(path)
        if (!configDirectory.exists()) configDirectory.mkdirs()
    }
}
