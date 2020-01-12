> ## Documentation for basic use of the Warps module.

## 1. For playing and running Minecraft:

#### 1.1 Download Warps mod module.

Visit **Warps** repository on github, visit **releases** tab and download the `.jar` files of latest _pre-release_ / release (**recommended**)

Releases page: <https://github.com/ProjectEssentials/ProjectEssentials-Warps/releases>

#### 1.2 Install Warps modification.

The Minecraft forge folder structure below will help you understand what is written below.

> ##### Important note: don't forget install mod dependencies!

- core: <https://github.com/ProjectEssentials/ProjectEssentials-Core/releases>
- permissions*: <https://github.com/ProjectEssentials/ProjectEssentials-Permissions/releases>
- cooldown*: <https://github.com/ProjectEssentials/ProjectEssentials-Cooldown/releases>

* - not mandatory dependency, but recommended.

```
.
├── assets
├── config
├── libraries
├── mods (that's how it should be)
│   ├── Project Essentials Warps-1.14.4-1.X.X.X.jar
│   ├── Project Essentials Core-MOD-1.14.4-1.X.X.X.jar.
│   └── Project Essentials Permissions-1.14.4-1.X.X.X.jar. (recommended)
│   └── Project Essentials Cooldown-1.14.4-1.X.X.X.jar. (recommended)
└── ...
```

Place your mods and Warps mods according to the structure above.

#### 1.3 Verifying mod on the correct installation.

Run the game, check the number of mods, if the list of mods contains `Project Essentials Home` mod, then the mod has successfully passed the initialization of the modification.

After that, go into a single world, then try to write the `/warp test` command, if you **get "warp not exist"**, then the modification works as it should.

#### 1.4 Control your warps via Minecraft commands.

We made the commands for you:

```
/warp <warp name>

- description: base command of warps module; just teleport you to target warp point.

- permission: ess.warp
```

```
/setwarp <warp name>

- description: set new warp point and now it warp point pnly owned by you. 

- permission: ess.warp.set
```

```
/delwarp <warp name>

- description: remove named warp point.

- note: only warp owner can remove target warp point!

- permission: ess.warp.remove
```

### If you have any questions or encounter a problem, be sure to open an issue!
