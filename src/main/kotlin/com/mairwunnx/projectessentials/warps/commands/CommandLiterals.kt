package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.api.v1.extensions.playerName
import com.mairwunnx.projectessentials.core.api.v1.permissions.hasPermission
import com.mairwunnx.projectessentials.warps.warpsConfiguration
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.ISuggestionProvider

inline val warpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = literal<CommandSource>("warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).suggests { _, builder ->
            ISuggestionProvider.suggest(
                warpsConfiguration.warps.asSequence().map { it.name }.toList(), builder
            )
        }.executes { WarpCommand.process(it) }
    )

inline val setWarpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = literal<CommandSource>("set-warp").then(
        Commands.argument(
            "warp", StringArgumentType.string()
        ).executes { SetWarpCommand.process(it) }
    )

inline val delWarpLiteral: LiteralArgumentBuilder<CommandSource>
    get() = literal<CommandSource>("del-warp").then(
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

inline val configureWarpsLiteral: LiteralArgumentBuilder<CommandSource>
    get() = literal<CommandSource>("configure-warps")
        .then(
            Commands.literal("play-sound-on-teleport").then(
                Commands.literal("set").then(
                    Commands.argument("value", BoolArgumentType.bool()).executes {
                        ConfigureWarpsCommand.playSoundOnTeleport(it)
                    }
                )
            )
        ).then(
            Commands.literal("show-effect-on-teleport").then(
                Commands.literal("set").then(
                    Commands.argument("value", BoolArgumentType.bool()).executes {
                        ConfigureWarpsCommand.showEffectOnTeleport(it)
                    }
                )
            )
        ).then(
            Commands.literal("add-resistance-effect").then(
                Commands.literal("set").then(
                    Commands.argument("value", BoolArgumentType.bool()).executes {
                        ConfigureWarpsCommand.addResistanceEffect(it)
                    }
                )
            )
        ).then(
            Commands.literal("resistance-effect-duration").then(
                Commands.literal("set").then(
                    Commands.argument("value", IntegerArgumentType.integer(1)).executes {
                        ConfigureWarpsCommand.resistanceEffectDuration(it)
                    }
                )
            )
        )

