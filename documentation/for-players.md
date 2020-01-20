> ## Installation instructions.

For start the modification, you need installed Forge, it is desirable that the version matches the supported versions. You can download Forge from the [link](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.14.4.html).
Move the downloaded mod to the `mods` folder (installation example below).

Also do not forget to install dependencies, only two types of dependencies
    - mandatory (game will not start without a mod)
    - recommended (without a mod, game can start, but I recommend using it)

Downloads: [Cooldown](https://github.com/ProjectEssentials/ProjectEssentials-Cooldown) · [Core](https://github.com/ProjectEssentials/ProjectEssentials-Core) · [Permissions](https://github.com/ProjectEssentials/ProjectEssentials-Permissions)

```
.
├── assets
├── config
├── libraries
├── mods (that's how it should be)
│   ├── Project Essentials Core-MOD-1.14.4-1.X.X.X.jar (mandatory)
│   ├── Project Essentials Cooldown-1.14.4-1.X.X.X.jar (recommended)
│   ├── Project Essentials Permissions-1.14.4-1.X.X.X.jar (recommended)
│   └── Project Essentials Warps-1.14.4-1.X.X.X.jar
└── ...
```

Now try to start the game, go to the `mods` tab, if this modification is displayed in the `mods` tab, then the mod has been successfully installed.

### Control your warps via Minecraft commands

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

### Configuration

Just in case. Default configuration.

You can get default configuration by removing file in path `.minecraft/config/ProjectEssentials/warps.json`, while mod loading if configuration file not exists it ll be recreated.

```json
{
    "addResistanceEffect": true,
    "resistanceEffectDuration": 200,
    "enableTeleportSound": true,
    "enableTeleportEffect": true,
    "warps": []
}
```

### Describing configuration

```
    Property name: addResistanceEffect

    Accepts data type: Boolean

    Description: if value true, then after teleport to any warp, you'll get resistance effect. Basically it needed for protecting player from griefing.
```

```
    Property name: resistanceEffectDuration

    Accepts data type: Int
    
    Description: make influence on resistance effect duration. (value accepts NOT AS SECONDS, in minecraft ticks)
```

```
    Property name: enableTeleportSound

    Accepts data type: Boolean
    
    Description: if value true, then after teleport to any warp, you'll hear teleport sound.
```

```
    Property name: enableTeleportEffect

    Accepts data type: Boolean
    
    Description: if value true, then after teleport to any warp, you'll see teleport effect (portal effect, like enderman teleporting effect).
```

```
    Property name: warps

    Accepts data type: Array with type Warp
    
    Description: contains all warps (Warp Model).

    See: `Describing Warp model`
```

### Describing Warp model

```
    Property name: name

    Accepts data type: String
    
    Description: warp name.
```

```
    Property name: clientWorld

    Accepts data type: String
    
    Description: client world name.
```

```
    Property name: owner

    Accepts data type: String
    
    Description: warp creator nickname.
```

```
    Property name: worldId

    Accepts data type: Int
    
    Description: minecraft world id.
```

```
    Property name: xPos

    Accepts data type: Int
    
    Description: warp position by X axis.
```

```
    Property name: yPos

    Accepts data type: Int
    
    Description: warp position by Y axis.
```

```
    Property name: zPos

    Accepts data type: Int
    
    Description: warp position by Z axis.
```

```
    Property name: yaw

    Accepts data type: Float
    
    Description: player camera rotation yaw.
```

```
    Property name: pitch

    Accepts data type: Float
    
    Description: player camera rotation pitch.
```

### Complete configuration example

It just for example, for understanding configuration structure.

```json
{
    "addResistanceEffect": true,
    "resistanceEffectDuration": 200,
    "enableTeleportSound": true,
    "enableTeleportEffect": true,
    "warps": [
        {
            "name": "MyWarp",
            "clientWorld": "New World",
            "owner": "MairwunNx",
            "worldId": 0,
            "xPos": 1094,
            "yPos": 91,
            "zPos": 520,
            "yaw": 178.35184,
            "pitch": 43.199905
        },
        {
            "name": "MyWarp1",
            "clientWorld": "New World",
            "owner": "MairwunNx",
            "worldId": 0,
            "xPos": 1093,
            "yPos": 91,
            "zPos": 520,
            "yaw": 265.3518,
            "pitch": 4.6499176
        }
    ]
}
```

### If you have any questions or encounter a problem, be sure to open an [issue](https://github.com/ProjectEssentials/ProjectEssentials-Warps/issues/new/choose)!