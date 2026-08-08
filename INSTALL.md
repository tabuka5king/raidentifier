# RaidIdentifier Mod - Telepítési Útmutató

## ✅ Telepítés Státusz

- ✓ Mod forráskód elkészült
- ✓ JAR fájl generálva
- ✓ Mods mappában telepítve
- ✓ Konfiguráció elkészítve

---

## 📦 Telepítési Helyek

### JAR Fájl
```
C:\Users\User\Desktop\rift\build\libs\raidentifier-1.0.0.jar
```

### Minecraft Mods Mappa
```
C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\raidentifier-1.0.0.jar
```

### Config Fájl
```
C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json
```

---

## 🎮 Minecraft Indítás

1. **ModrinthApp megnyitása**
2. **Profil kiválasztása**: "Fabric 1.21.11"
3. **Play gomb** kattintás
4. Minecraft betöltődik a RaidIdentifier mod-dal

---

## ⌨️ Mod Kezelés

### Insert Billentyű
- Nyomd meg az **Insert** gombot bármikor a játékban
- Megnyílik a **Raid Alert beállítási menü**
- Gombokkal állítsd a paramétereket

### Raid Alert Aktiválódása
- Ha egy játékos a **32 blokkos körzetbe** lép
- **HANGOS ALERT** játszódik le
- A konzolban megjelenik a figyelmeztetés

---

## ⚙️ Beállítható Paraméterek

| Paraméter | Min | Max | Alapértelmezett | Leírás |
|-----------|-----|-----|-----------------|--------|
| Detection Range | 5 | 128 | 32 | Detektálási távolság blokk |
| Volume | 0.0 | 1.0 | 1.0 | Hangeffekt hangereje |
| Alert Cooldown | 1 | 60 | 5 | Szünet azonos játékos között (sec) |
| Sound Type | - | - | bell | bell/alarm/beep |
| Enable/Disable | - | - | ON | Mod be/ki kapcsolása |

---

## 📋 Config Fájl Formátum

**Útvonal**: `C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json`

### Alapértelmezett Config:
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

### Hangeffektek:
- `"bell"` - Csengő hangeffekt (MC Note Block Bell)
- `"alarm"` - Sírén hangeffekt (Anvil Fall)
- `"beep"` - Rövid beep (Comparator Click)

---

## 🔧 Fejlesztés

### Projekt Struktúra
```
rift/
├── src/main/java/
│   └── dev/tabuka/raidentifier/
│       ├── RaidIdentifier.java         (Main entry point)
│       ├── RaidAlertManager.java        (Core logic)
│       ├── RaidAlertConfig.java         (Config handler)
│       └── RaidAlertScreen.java         (GUI screen)
├── src/main/resources/
│   ├── fabric.mod.json
│   └── raidentifier.mixins.json
├── build.gradle.kts
└── gradle.properties
```

### Módosítások Után
1. Szerkeszd a Java fájlokat
2. Frissítsd a JAR-t
3. Másold az új JAR-t a mods mappába
4. Indítsd újra a Minecraft-ot

---

## 🐛 Hibaelhárítás

### "Mod incompatible" hiba
- **OK**: fabric.mod.json verzió beállítás
- **Megoldás**: Ellenőrizd a verziónumerusos értékeket

### Nincsen hangeffekt
- Ellenőrizd, hogy `"playSound": true` van-e
- Állítsd fel a `"volume"` értékét
- Próbálj másik Sound Type-ot

### Config nem mentődik
- Ellenőrizd a mappa jogosultságait
- Próbálj manuálisan szerkeszteni

---

## 📞 Verzió Infó

- **Mod verzió**: 1.0.0
- **Minecraft**: 1.21.11
- **Fabric Loader**: >=0.18.4
- **Fabric API**: >=0.141.3+1.21.11
- **Java**: 21+

---

## 📝 Licensz

MIT License - Szabad felhasználás, módosítás és terjesztés.

---

**Utolsó frissítés**: 2026-08-08
