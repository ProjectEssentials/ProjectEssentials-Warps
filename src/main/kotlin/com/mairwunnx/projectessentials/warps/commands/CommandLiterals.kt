package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.configuration.ConfigurationAPI
import com.mairwunnx.projectessentials.warps.configurations.WarpsConfiguration
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.ISuggestionProvider

val warpLiteral: LiteralArgumentBuilder<CommandSource> =
    LiteralArgumentBuilder.literal<CommandSource>("warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).suggests { _, builder ->
            ISuggestionProvider.suggest(
                ConfigurationAPI.getConfigurationByName<WarpsConfiguration>(
                    "warps"
                ).take().warps.asSequence().map { it.name }.toList(), builder
            )
        }
    )
