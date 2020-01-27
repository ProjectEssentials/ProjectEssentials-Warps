# Configuration

## Just in case. Default configuration.

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

## Describing configuration

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

## Describing Warp model

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

## Complete configuration example

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
