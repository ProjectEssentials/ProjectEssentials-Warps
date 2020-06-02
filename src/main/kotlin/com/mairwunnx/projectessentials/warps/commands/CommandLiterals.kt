package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.warps.warpsConfiguration
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
                warpsConfiguration.warps.asSequence().map { it.name }.toList(), builder
            )
        }.executes { WarpCommand.process(it) }
    )

inline val setWarpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = LiteralArgumentBuilder.literal<CommandSource>("set-warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).executes { SetWarpCommand.process(it) }
    ).executes { SetWarpCommand.process(it) }

inline val delWarpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = LiteralArgumentBuilder.literal<CommandSource>("del-warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).suggests { _, builder ->
            ISuggestionProvider.suggest(
                warpsConfiguration.warps.asSequence().map { it.name }.toList(), builder
            )
        }.executes { DelWarpCommand.process(it) }
    ).executes { DelWarpCommand.process(it) }
