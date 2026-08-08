# RaidIdentifier - Fejlesztési Útmutató

## Projekt Áttekintés

**RaidIdentifier** egy Minecraft Fabric mod amely hangos értesítést ad játékosok közelségéről az SMP szervereken (Donut SMP).

---

## 🛠️ Fejlesztési Környezet Beállítása

### Szükséges Eszközök
- **Java 21+** (Microsoft OpenJDK 21.0.1)
- **Gradle 8.7+**
- **IntelliJ IDEA** vagy VS Code
- **Git** (opcionális)

### Telepítés
```bash
# 1. Repository klónozása vagy letöltése
cd C:\Users\User\Desktop\rift

# 2. Projekt megnyitása IDE-ben
# IntelliJ IDEA: Open -> Select rift folder
# VS Code: File -> Open Folder

# 3. Gradle szinkronizálása
# IDE automatikusan szinkronizál
```

---

## 📂 Projekt Struktúra

```
rift/
├── src/
│   ├── main/
│   │   ├── java/dev/tabuka/raidentifier/
│   │   │   ├── RaidIdentifier.java           [Main entry point]
│   │   │   ├── RaidAlertManager.java         [Alert logic]
│   │   │   ├── RaidAlertConfig.java          [JSON config]
│   │   │   └── RaidAlertScreen.java          [GUI]
│   │   └── resources/
│   │       ├── fabric.mod.json               [Mod metadata]
│   │       └── raidentifier.mixins.json      [Mixins config]
├── build/
│   └── libs/
│       └── raidentifier-1.0.0.jar           [Built mod]
├── build.gradle.kts                          [Gradle config]
├── gradle.properties                         [Version config]
└── settings.gradle.kts
```

---

## 🔨 Build Folyamat

### Gradle Build
```bash
# Full build
gradle build

# Csak JAR
gradle jar

# Clean build
gradle clean build

# Dev environment
gradle runClient
```

### Manual JAR Creation
```powershell
Add-Type -AssemblyName System.IO.Compression.FileSystem

$sourceDir = "C:\Users\User\Desktop\rift\src\main\resources"
$jarPath = "C:\Users\User\Desktop\rift\build\libs\raidentifier-1.0.0.jar"

[System.IO.Compression.ZipFile]::CreateFromDirectory($sourceDir, $jarPath, [System.IO.Compression.CompressionLevel]::Optimal, $false)
```

---

## 📝 Kód Áttekintés

### RaidIdentifier.java
**Fő entry point**
- Mod inicializálása
- Insert billentyű regisztrálása (GLFW key binding)
- Client tick event handler

```java
public void onInitializeClient() {
    RaidAlertConfig.loadConfig();
    RaidAlertManager.init();
    // Insert key binding
    openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.raidentifier.open_menu",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_INSERT,
        "category.raidentifier.main"
    ));
}
```

### RaidAlertManager.java
**Alert logika**
- Tick-enkénti játékos pozíció ellenőrzés
- Közelségi detektálás (euclidean distance)
- Cooldown kezelés (avoid spam)
- Hangeffekt lejátszás

```java
private static void tick() {
    // Játékosok iterálása
    for (PlayerEntity player : world.getPlayers()) {
        double distance = playerPos.distanceTo(player.getPos());
        
        if (distance <= detectionRange) {
            // Cooldown ellenőrzés
            if (currentTime - lastAlert >= alertCooldown) {
                triggerAlert(playerName, distance);
            }
        }
    }
}
```

### RaidAlertConfig.java
**JSON konfiguráció**
- GSON library használata
- Auto-save beállítások
- Config mappában tárolás

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

### RaidAlertScreen.java
**GUI menü**
- Insert gombra megnyílik
- TextFieldWidget beállítások
- Toggle gombok (ON/OFF)
- SaveConfig & Close gombok

---

## 🔊 Hangeffektek

### Elérhető Hangok
| Sound | Minecraft Event |
|-------|-----------------|
| Bell | `SoundEvents.BLOCK_NOTE_BLOCK_BELL` |
| Alarm | `SoundEvents.BLOCK_ANVIL_FALL` |
| Beep | `SoundEvents.BLOCK_COMPARATOR_CLICK` |

### Hangeffekt Lejátszás
```java
client.player.playSound(soundEvent, volume, pitch);
```

---

## 🎮 Minecraft Event Kezelés

### Client Tick Event
```java
ClientTickEvents.END_CLIENT_TICK.register(client -> {
    // Insert key ellenőrzés
    if (openMenuKey.wasPressed()) {
        client.setScreen(new RaidAlertScreen());
    }
});
```

### Player Rendering
A mod nem módosít semmilyen rendering-et, csak üres Mixin pluginok vannak.

---

## 📦 Függőségek

### Fabric API
```gradle
modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
```

### Minecraft Official Mappings
```gradle
mappings loom.officialMojangMappings()
```

---

## 🐛 Debug & Testing

### Minecraft Log
```
C:\Users\User\AppData\Roaming\.minecraft\logs\latest.log
```

### Config Fájl Hely
```
C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json
```

### Logger Üzenetek
```java
RaidIdentifier.LOGGER.info("Message");
RaidIdentifier.LOGGER.warn("RAID ALERT!");
RaidIdentifier.LOGGER.error("Error message");
```

---

## 📋 Fejlesztési Checklist

- [ ] Fordítás sikeres
- [ ] JAR fájl létrehozva
- [ ] Mods mappában telepítve
- [ ] Minecraft indul
- [ ] Insert gomb megnyitja a menüt
- [ ] Beállítások mentódnek
- [ ] Hangeffekt működik
- [ ] Játékos detektálódik

---

## 🚀 Verzió Frissítése

### 1.0.0 → 1.1.0
1. `gradle.properties` verzió frissítése
2. `fabric.mod.json` verzió frissítése
3. Kód módosítások
4. Build
5. JAR másolása mods mappába
6. Tesztelés

---

## 📚 Hasznos Linkek

- [Fabric Documentation](https://docs.fabricmc.net/)
- [Minecraft Wiki](https://minecraft.wiki/)
- [Gradle Docs](https://docs.gradle.org/)
- [SLF4J Logger](https://www.slf4j.org/)

---

## 📝 Megjegyzések

- **Thread-safe**: Az PlayerEntity iterálás csak client thread-ben történik
- **Performance**: Tick-enkénti megjelenítés (60 FPS-sel szinkron)
- **Compatibility**: Fabric 1.21.11 + Minecraft 1.21.11

---

**Készült**: 2026-08-08
**Utolsó módosítás**: 2026-08-08
