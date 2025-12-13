# 🔴 GUIDE DE DÉBOGAGE - APP CRASHES

## 📋 Étapes de Diagnostic

### **ÉTAPE 1 : Vérifier les Logs Logcat**

1. Ouvrez **Logcat** dans Android Studio
2. Filtrez par "MainActivity" ou "SplashActivity"
3. Recherchez les messages avec tag "ERROR" (rouge)

**Logs à rechercher :**
```
MainActivity: onCreate started
MainActivity: Binding initialized
MainActivity: Background applied
MainActivity: Navigation setup successful
MainActivity: Database initialized
MainActivity: onCreate completed successfully
```

**Si vous voyez une erreur, notez :**
- Le nom de l'exception (ex: NullPointerException, ClassNotFoundException)
- Le message d'erreur
- La ligne qui cause le problème

---

### **ÉTAPE 2 : Problèmes Courants et Solutions**

#### ❌ **Crash au démarrage du Splash**

**Symptômes :** L'app crash immédiatement après le lancement

**Causes possibles :**
1. Fichier `activity_splash.xml` corrompu
2. `bg_gradient_blue_dynamic.xml` introuvable
3. ViewBinding non activé

**Solutions :**
```bash
# Nettoyez et rebuild
cd C:\Users\pc\AndroidStudioProjects\MaisonHotesApp
.\gradlew clean
.\gradlew build
```

#### ❌ **Crash après le Splash (lors du passage à MainActivity)**

**Symptômes :** Le splash s'affiche mais crash au passage à MainActivity

**Causes possibles :**
1. NavHostFragment introuvable
2. nav_graph.xml incorrect
3. Fragments manquants

**Solution IMMÉDIATE :**
Modifiez `AndroidManifest.xml` pour utiliser MainActivitySimple :

```xml
<!-- Dans AndroidManifest.xml, remplacez -->
<activity
    android:name=".MainActivity"
    android:exported="true" />

<!-- PAR -->
<activity
    android:name=".MainActivitySimple"
    android:exported="true" />
```

Puis dans `SplashActivity.kt`, changez :
```kotlin
// Ligne 73 : remplacez
val intent = Intent(this, MainActivity::class.java)
// PAR
val intent = Intent(this, MainActivitySimple::class.java)
```

#### ❌ **Crash avec "NavHostFragment not found"**

**Solution :**
Vérifiez `activity_main.xml` :
```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/nav_host_fragment"  <!-- ← Vérifiez cet ID -->
    android:name="androidx.navigation.fragment.NavHostFragment"
    ...
    app:navGraph="@navigation/nav_graph" />
```

#### ❌ **Crash avec Database / Room errors**

**Solution :**
La base de données est désormais optionnelle. Si elle cause des problèmes, le code continuera quand même.

**Pour désactiver complètement la DB :**
Dans `MainActivity.kt`, commentez :
```kotlin
// initializeDatabase()  // ← Commentez cette ligne
```

---

### **ÉTAPE 3 : Test avec Version Simplifiée**

Si l'app crash toujours, utilisez la version ultra-simplifiée :

1. **Modifiez `AndroidManifest.xml`** :
```xml
<activity
    android:name=".MainActivitySimple"
    android:exported="true" />
```

2. **Modifiez `SplashActivity.kt` ligne 73** :
```kotlin
val intent = Intent(this, MainActivitySimple::class.java)
```

3. **Rebuild et relancez**

Cette version affiche des Toasts pour chaque étape et permet de voir exactement où ça bloque.

---

### **ÉTAPE 4 : Vérifier les Fichiers Critiques**

#### ✓ Fichier 1 : `AndroidManifest.xml`
```xml
<!-- Vérifiez que ces lignes existent -->
<application
    android:name=".MaisonHotesApplication"
    ...>
    
    <activity
        android:name=".SplashActivity"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

    <activity
        android:name=".MainActivity"
        android:exported="true" />
</application>
```

#### ✓ Fichier 2 : `nav_graph.xml`
```xml
<!-- Vérifiez que startDestination existe -->
<navigation ...
    app:startDestination="@id/maisonListFragment">
    
    <fragment
        android:id="@+id/maisonListFragment"
        android:name="com.example.maisonhotesapp.ui.fragment.MaisonListFragment"
        ... />
</navigation>
```

#### ✓ Fichier 3 : `build.gradle.kts` (app)
```kotlin
// Vérifiez que ViewBinding est activé
android {
    buildFeatures {
        viewBinding = true
    }
}
```

---

## 🛠️ Actions Correctives par Type d'Erreur

### **NullPointerException**
→ Un élément du layout est null
→ Vérifiez les IDs dans le XML et les binding calls

### **ClassNotFoundException**
→ Une classe n'est pas trouvée
→ Faites **Build > Clean Project** puis **Build > Rebuild Project**

### **InflateException**
→ Erreur dans un fichier XML
→ Vérifiez les erreurs XML dans les layouts et drawables

### **Resources$NotFoundException**
→ Une ressource (drawable, string, etc.) n'existe pas
→ Vérifiez que tous les drawables existent :
  - `bg_gradient_blue_dynamic.xml`
  - `ic_home.xml`
  - etc.

---

## 📱 Commandes de Débogage

### Voir les logs en temps réel :
```bash
# Dans un terminal
cd C:\Users\pc\AndroidStudioProjects\MaisonHotesApp
.\gradlew installDebug

# Puis dans Android Studio : View > Tool Windows > Logcat
```

### Rebuild complet :
```bash
.\gradlew clean
.\gradlew build
```

### Désinstaller complètement l'app :
```bash
adb uninstall com.example.maisonhotesapp
```

---

## 🚨 Solution d'Urgence : Version MINIMALE

Si RIEN ne fonctionne, créez `MainActivityMinimal.kt` :

```kotlin
package com.example.maisonhotesapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityMinimal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this)
        textView.text = "L'APPLICATION FONCTIONNE !\n\nSi vous voyez ce message,\nle problème vient des fragments\nou de la navigation."
        textView.textSize = 20f
        textView.setPadding(50, 50, 50, 50)
        
        setContentView(textView)
    }
}
```

Puis dans `AndroidManifest.xml` :
```xml
<activity
    android:name=".MainActivityMinimal"
    android:exported="true" />
```

Et dans `SplashActivity.kt` :
```kotlin
val intent = Intent(this, MainActivityMinimal::class.java)
```

Si cette version fonctionne, le problème est dans la navigation ou les fragments.

---

## 📊 Checklist de Vérification

- [ ] ViewBinding activé dans build.gradle.kts
- [ ] Tous les drawables existent (bg_gradient_blue_dynamic.xml, ic_home.xml, etc.)
- [ ] nav_graph.xml est correct
- [ ] MaisonListFragment existe
- [ ] activity_main.xml contient nav_host_fragment
- [ ] AndroidManifest.xml correct
- [ ] Clean + Rebuild effectué
- [ ] App désinstallée puis réinstallée

---

## 📞 Informations à Fournir si Ça Ne Fonctionne Toujours Pas

1. **Message d'erreur exact** du Logcat (copier-coller)
2. **Type d'exception** (NullPointerException, etc.)
3. **Ligne qui cause le problème**
4. **Version d'Android** de votre émulateur/téléphone
5. **Est-ce que MainActivitySimple fonctionne ?** (Oui/Non)
6. **Est-ce que MainActivityMinimal fonctionne ?** (Oui/Non)

Avec ces informations, on pourra identifier exactement le problème !

