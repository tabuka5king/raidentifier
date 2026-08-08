# RaidIdentifier Mod - Build Instructions

## Előfeltételek
- **Java 21+** (OpenJDK vagy Oracle JDK)
  - Ellenőrizd: `java -version` a parancssorban
- **Gradle 8.4+** vagy **IntelliJ IDEA / Eclipse IDE**

## Build módszer 1: IntelliJ IDEA (Ajánlott)
1. Nyisd meg az projektet az IntelliJ IDEA-ban
2. Jobb kattintás az `build.gradle.kts` fájlra → "Import Gradle Project"
3. Várj amíg az indexálás befejeződik
4. Terminál: `./gradlew build` (Windows: `gradlew.bat build`)
5. Kész JAR: `build/libs/raidentifier-1.0.0.jar`

## Build módszer 2: Gradle parancssor
```bash
cd c:\Users\User\Desktop\rift
gradlew.bat build
```

Kész JAR: `build/libs/raidentifier-1.0.0.jar`

## Build módszer 3: Visual Studio Code + Gradle Extension
1. Nyisd meg a mappát VS Code-ban
2. Telepítsd az "Gradle for Java" extension-t (vscode-gradle)
3. VS Code parancspalettában (`Ctrl+Shift+P`): `Gradle: Build`
4. Kész JAR: `build/libs/raidentifier-1.0.0.jar`

## JAR telepítése
A build után másold az JAR fájlt a Minecraft mod mappába:
```
Forrás: c:\Users\User\Desktop\rift\build\libs\raidentifier-1.0.0.jar
Cél: C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\raidentifier-1.0.0.jar
```

## Troubleshooting
- Ha Gradle-related hiba: Delete `build/` és `.gradle/` mappákat, újra próbálkoZz
- Ha Java version hiba: Telepítsd a Java 21-et
- Ha network hiba: Ellenőrizd az internet kapcsolat és próbálkozz proxy-val ha szükséges

## Konfiguráció
A mod után az első futáskor létrehoz egy config fájlt:
`C:\Users\User\AppData\Roaming\.minecraft\config\raidentifier.json`

Ez szerkeszthető vagy az Insert billentyűvel a játékban megnyitható a GUI.
