# RaidIdentifier - Minecraft Fabric Mod

Raid alert mod a Donut SMP-hez. Hangos értesítést ad amikor játékosok a közeledbe jönnek.

## Funkciók

✅ **Játékos Detektálás** - Automatikusan detektálja ha más játékosok a közeledbe jönnek
✅ **Hangos Alert** - 3 féle hangeffekt közül választhatsz (csengő, sírén, beep)
✅ **Beállítható Hatótávolság** - 5-128 blokk közötti detekciós távolság
✅ **Hangerő Vezérlés** - Állítható hangerő szint (0.0-1.0)
✅ **Alert Cooldown** - Játékosok között állítható alert szünet
✅ **GUI Menü** - Insert billentyűvel megnyitható beállítási menü
✅ **JSON Config** - Összes beállítás elmentódik a config fájlban

## Telepítés

1. **Build a moddot** (lásd BUILD_INSTRUCTIONS.md)
2. **Másold a JAR fájlt** a mods mappába:
   ```
   C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\
   ```
3. **Indítsd el a Minecraft-ot** és élvezd az raid alert-eket!

## Használat

### Insert billentyű
- Nyomd meg az **Insert** gombot a játékban a beállítási menü megnyitásához

### Beállítási Menü
- **Detection Range**: Milyen messze detektálódnak a játékosok (alapértelmezett: 32 blokk)
- **Volume**: A hangjelzés hangereje (0.0-1.0)
- **Sound Type**: Hangeffekt típusa (bell/csengő, alarm/sírén, beep)
- **Alert Cooldown**: Mennyi ideig várakozz egy játékos között (1-60 másodperc)
- **Alert**: BE/KI kapcsoló

### Konfiguráció Fájl
A config automatikusan létrejön:
```
C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json
```

Manuális szerkesztés:
```json
{
  "detectionRange": 32.0,
  "volume": 1.0,
  "enabled": true,
  "playSound": true,
  "soundType": "bell",
  "alertCooldown": 5
}
```

## Hangeffektek

| Típus | Leírás |
|-------|--------|
| `bell` | Csengő hang (alapértelmezett) |
| `alarm` | Sírén hangeffekt |
| `beep` | Rövid beep hang |

## Fejlesztés

Igényes fejlesztőknek:
- **IDE**: IntelliJ IDEA Community Edition (ingyenes)
- **Gradle**: 8.4+
- **Java**: 21+

```bash
# Build
gradlew.bat build

# Run dev server
gradlew.bat runClient
```

## Hibajelentés

Hibákat találtál? Nézd meg a Minecraft napló fájlt:
```
C:\Users\User\AppData\Roaming\.minecraft\logs\latest.log
```

## Licenc

MIT License - Szabad felhasználás és módosítás.

---

**Version**: 1.0.0  
**Minecraft**: 1.21.1  
**Loader**: Fabric
