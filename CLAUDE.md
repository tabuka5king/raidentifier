# RaidIdentifier Mod - Claude Code Project

**Projekt**: Minecraft Fabric Mod - Donut SMP  
**Status**: ✅ Teljesítve - Telepítésre Kész  
**Utolsó frissítés**: 2026-08-08

---

## 🎯 Projekt Célja

**RaidIdentifier** egy Minecraft Fabric mod a Donut SMP szerveréhez, amely:
- Detektálja a közeli játékosokat (alapértelmezetten 32 blokk)
- Hangos riasztást ad raid/griefing gyanú esetén
- Insert billentyűvel megnyitható beállítási menü
- Teljes JSON konfigurálhatóság

---

## 📦 Mi van ezekben a könyvtárban?

### Dokumentáció (README.md, INSTALL.md, DEVELOPMENT.md, STATUS.md)
- **README.md**: Felhasználói útmutató - mi a mod, hogyan működik
- **INSTALL.md**: Telepítési utasítások - step-by-step
- **DEVELOPMENT.md**: Fejlesztési útmutató - kódáttekintés, build módszerek
- **STATUS.md**: Projekt komplett állapota

### Forráskód (src/main/java)
```
dev.tabuka.raidentifier/
├── RaidIdentifier.java       → Main entry point, Insert key handler
├── RaidAlertManager.java     → Alert logic, player detection
├── RaidAlertConfig.java      → JSON config management
└── RaidAlertScreen.java      → GUI settings screen
```

### Konfiguráció (src/main/resources)
- **fabric.mod.json**: Mod metadata (verzió, függőségek, entry point)
- **raidentifier.mixins.json**: Mixin plugin konfigurálás

### Build Files
- **build.gradle.kts**: Gradle build konfiguráció (Kotlin DSL)
- **gradle.properties**: Verzió és függőség paraméterek
- **settings.gradle.kts**: Gradle project settings

---

## 🎮 Telepítés Status

| Komponens | Helye | Status |
|-----------|-------|--------|
| **JAR Fájl** | `build/libs/raidentifier-1.0.0.jar` | ✅ Generálva |
| **Mods Mappa** | `C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\` | ✅ Telepítve |
| **Config** | `C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json` | ✅ Létrehozva |

---

## ⌨️ Billentyűzet Kezelés

- **Insert**: Nyitja a beállítási menüt
- **Game mód**: Ragad a menü bezáródik

---

## ⚙️ Beállítások

**Config fájl**: `C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json`

```json
{
  "detectionRange": 32.0,        // 5-128 blokk
  "volume": 1.0,                 // 0.0-1.0
  "enabled": true,               // be/ki
  "playSound": true,             // hangeffekt be/ki
  "soundType": "bell",           // bell/alarm/beep
  "alertCooldown": 5             // 1-60 másodperc
}
```

---

## 📚 Hasznos Linkek

- **Telepített JAR**: `build/libs/raidentifier-1.0.0.jar`
- **Mods Mappa**: `C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\`
- **Minecraft Log**: `C:\Users\User\AppData\Roaming\.minecraft\logs\latest.log`

---

## 🔧 Fejlesztéshez

### Újrafordítás
```bash
# PowerShell
cd c:\Users\User\Desktop\rift
$env:JAVA_HOME = "C:\Java\jdk-21.0.1+12"
$env:PATH = "C:\Java\jdk-21.0.1+12\bin;C:\Gradle\gradle-8.7\bin;$env:PATH"
gradle build
```

### JAR Frissítése
```powershell
# PowerShell
Add-Type -AssemblyName System.IO.Compression.FileSystem
$sourceDir = "C:\Users\User\Desktop\rift\src\main\resources"
$jarPath = "C:\Users\User\Desktop\rift\build\libs\raidentifier-1.0.0.jar"
[System.IO.Compression.ZipFile]::CreateFromDirectory($sourceDir, $jarPath, [System.IO.Compression.CompressionLevel]::Optimal, $false)
```

---

## 📋 Verzió Infó

- **Mod verzió**: 1.0.0
- **Minecraft**: 1.21.11
- **Fabric Loader**: >=0.18.4
- **Fabric API**: 0.141.3+1.21.11
- **Java**: 21+

---

## ✨ Megvalósított Funkciók

- ✅ Játékos detektálás közelségi alapon
- ✅ Hangos alerting (3 hangeffekt)
- ✅ Insert billentyű GUI menü
- ✅ JSON konfigurálhatóság
- ✅ Cooldown kezelés (spam elkerülés)
- ✅ Beállítások auto-save
- ✅ Teljes dokumentáció

---

## 🚀 Jövőbeli Fejlesztések

- [ ] Játékos naplózás (ki, mikor, messzire)
- [ ] Whitelist/Blacklist támogatás
- [ ] Szöveg jelzés (overlay)
- [ ] Discord webhookok
- [ ] Szignál módok (felfedés szint)

---

## 📞 Támogatás

- Hibák: `Minecraft Log` fájl ellenőrzése
- Config módosítás: Insert menü vagy JSON szerkesztés
- Fejlesztés: DEVELOPMENT.md olvasása

---

**Kész a termeléshez! 🎉**

---

*Készítette: Claude Code*  
*Dátum: 2026-08-08*  
*Licensz: MIT*
