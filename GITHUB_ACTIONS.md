# GitHub Actions - Ingyenes Cloud Build

## 🚀 Lépések

### 1. GitHub Repository Létrehozása
```bash
# Frissítsd az github.com-ot
git init
git add .
git commit -m "RaidIdentifier Minecraft mod"
git remote add origin https://github.com/YOUR_USERNAME/raidentifier.git
git push -u origin main
```

### 2. Actions Futás
- GitHub automatikusan detektálja a `.github/workflows/build.yml` fájlt
- Actions → Build RaidIdentifier Mod
- Zöld pipline = sikeres build

### 3. JAR Letöltése
```
Actions → Latest Run → Artifacts → raidentifier-mod.zip
```

### 4. Telepítés
```
build/libs/raidentifier-1.0.0.jar
↓
Copy to:
C:\Users\User\AppData\Roaming\ModrinthApp\profiles\Fabric 1.21.11\mods\
```

---

## 💡 Előnyök
- ✅ Ingyenes (GitHub Free plan)
- ✅ Internet van (GitHub szerverek)
- ✅ Automatikus build minden push után
- ✅ JAR már fordított és kész

---

## ⏱️ Idő
- ~2 perc build
- ~30 sec JAR letöltés

---

**Ennek után az Insert gomb és raid alert működni fog!**
