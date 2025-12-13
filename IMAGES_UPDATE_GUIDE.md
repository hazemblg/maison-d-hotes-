# Guide de mise à jour des images - Maisons d'Hôtes Tunisie

## ✅ Modifications effectuées

### 1. Images locales ajoutées (19/20)
Les images ont été placées dans : `app/src/main/res/drawable/`

#### Images présentes (format PNG) :
- ✅ **Maison Tunis** : maison_tunis_1.png à maison_tunis_4.png
- ✅ **Villa Hammamet** : villa_hammamet_1.png à villa_hammamet_4.png  
- ✅ **Gîte Djerba** : gite_djerba_1.png à gite_djerba_4.png
- ⚠️ **Dar Sousse** : dar_sousse_2.png à dar_sousse_4.png (**manque dar_sousse_1.png**)
- ✅ **Riad Carthage** : riad_carthage_1.png à riad_carthage_4.png

### 2. Base de données mise à jour
- Version BD : 2 → 4 (pour forcer la réinitialisation)
- Configuration : `.fallbackToDestructiveMigration()` active
- **Pas besoin de désinstaller l'app !**

### 3. Code modifié pour charger les images locales
- ✅ `MaisonAdapter.kt` - Détecte ressources locales vs URLs
- ✅ `ImagePagerAdapter.kt` - Slider d'images avec ressources locales
- ✅ `MaisonDetailFragment.kt` - Page de détails avec images locales
- ✅ `DataInitializer.kt` - Références aux noms d'images locales
- ✅ `MaisonHotesApplication.kt` - Initialisation automatique au démarrage

### 4. Interface utilisateur améliorée
- ✅ ProgressBar pendant le chargement
- ✅ Message "Aucune maison trouvée" si base vide
- ✅ Logs de débogage pour identifier les problèmes

---

## 🚀 Comment lancer l'app avec les nouvelles images

### Étape 1 : Clean le projet (IMPORTANT)
Dans Android Studio :
- `Build` → `Clean Project`
- Attendez la fin

### Étape 2 : Rebuild le projet
- `Build` → `Rebuild Project`
- Cela peut prendre 1-2 minutes

### Étape 3 : Lancer l'application
- Cliquez sur ▶️ Run
- **Lors du premier lancement** :
  - Le SplashScreen s'affiche
  - La base de données v4 est créée
  - Les données avec les nouvelles images sont initialisées
  - Cela prend quelques secondes

### Étape 4 : Vérifier les logs
Ouvrez le Logcat et filtrez par "MaisonHotesApp" pour voir :
```
✅ Application started - Initializing database...
✅ Database initialized successfully
✅ Maisons loaded: 5 items
✅ Maisons: Maison Traditionnelle Tunis, Villa Hammamet Plage, ...
```

---

## ⚠️ Si la page d'accueil est vide

### Option 1 : Attendre 5-10 secondes
L'initialisation de la BD peut prendre du temps au premier lancement.

### Option 2 : Vérifier les logs
```
adb logcat | findstr "MaisonHotesApp MaisonListFragment DataInitializer"
```

### Option 3 : Forcer l'arrêt de l'app
Sur l'émulateur :
- `Settings` → `Apps` → `Maisons d'Hôtes Tunisie` → `Force Stop`
- Relancer l'app

### Option 4 (dernier recours) : Désinstaller l'app
Si vraiment rien ne fonctionne :
```
adb uninstall com.example.maisonhotes
```
Puis relancer depuis Android Studio.

---

## 📝 Image manquante à ajouter

**dar_sousse_1.png** est manquante dans le dossier drawable.

**Solution temporaire** : En attendant la vraie image, copier une des autres :
```powershell
Copy-Item "dar_sousse_2.png" "dar_sousse_1.png"
```

---

## 🔍 Structure technique

### Comment les images sont chargées

```kotlin
// Si c'est un nom de ressource locale (ex: "maison_tunis_1")
val resId = context.resources.getIdentifier(
    "maison_tunis_1",  // Nom sans extension
    "drawable",         // Type de ressource
    context.packageName
)

// Glide charge ensuite la ressource
Glide.with(context).load(resId).into(imageView)
```

### Fallback automatique
Si une image locale n'est pas trouvée, l'app affiche automatiquement `ic_placeholder`.

---

## ✨ Résultat attendu

Après le lancement réussi :
- ✅ 5 maisons d'hôtes affichées avec leurs vraies photos
- ✅ Chaque maison a 4 images dans son slider
- ✅ Navigation fluide entre les pages
- ✅ Images chargées instantanément (pas de délai réseau)

---

**Date de mise à jour** : 13 Décembre 2024  
**Version BD** : 4

