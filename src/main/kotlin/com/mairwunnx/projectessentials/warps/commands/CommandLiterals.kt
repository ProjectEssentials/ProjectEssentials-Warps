package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.api.v1.extensions.playerName
import com.mairwunnx.projectessentials.core.api.v1.permissions.hasPermission
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
    )

inline val delWarpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = LiteralArgumentBuilder.literal<CommandSource>("del-warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).suggests { ctx, builder ->
            ISuggestionProvider.suggest(
                if (ctx.isPlayerSender()) {
                    if (hasPermission(ctx.getPlayer()!!, "ess.warp.remove.other", 4)) {
                        warpsConfiguration.warps.asSequence().map { it.name }.toList()
                    } else {
                        warpsConfiguration.warps.asSequence().filter {
                            it.owner == ctx.playerName()
                        }.map { it.name }.toList()
                    }
                } else {
                    warpsConfiguration.warps.asSequence().map { it.name }.toList()
                }, builder
            )
        }.executes { DelWarpCommand.process(it) }
    )
