# Flujo de trabajo — Dinamyc Combat (NeoForge)

> **Versión del workflow**: 1.0.0 (codex-docs)
> Este archivo pertenece al proyecto **Dinamyc Combat**. Cada proyecto tiene su propio `WORKFLOW_<MOD_ID>_<MC-VERSION>.md`.
> No es un archivo central ni template compartido. Los cambios aquí solo afectan a este proyecto.
> Para actualizar este workflow, revisar la última versión en `codex-docs/WORKFLOW_GENERIC.md`.

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id` en gradle.properties, assets/, packages Java | `dinamyc_combat` |
| **PascalCase** | Clases Java principales | `DinamyCombat` |
| **camelCase** | Variables, métodos, config keys | `isHoldToAttackEnabled` |
| **Title Case** | Display name en README, CHANGELOG, docs, CurseForge | `Dinamyc Combat` |

### Ficheros de documentación

| Fichero | Formato | Ejemplo |
|---|---|---|
| WORKFLOW | `WORKFLOW_<MOD_ID>_<MC-VERSION>.md` | `WORKFLOW_DINAMYC_COMBAT_26.1.2.md` |
| CHANGELOG | `CHANGELOG.md` (fijo) | `CHANGELOG.md` |
| README | `README.md` (fijo) | `README.md` |

## Estructura del proyecto

```
<mod>/
├── build.gradle                        # Build con net.neoforged.moddev
├── gradle.properties                   # mod_id, mod_version, mod_group_id...
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/<package>/             # Código fuente del mod
│   │   ├── resources/
│   │   │   ├── assets/<mod_id>/        # Texturas, shaders, lang, modelos...
│   │   │   │   └── icon.png           # Logo del mod (64x64)
│   │   │   ├── templates/
│   │   │   │   └── META-INF/
│   │   │   │       └── neoforge.mods.toml  # Template con placeholders ${...}
│   │   │   ├── META-INF/
│   │   │   │   └── accesstransformer.cfg
│   │   │   ├── <mod_id>.mixins.json
│   │   │   └── <mod_id>.png           # Logo del mod
│   │   └── templates/                 # (legacy, evitar)
│   ├── main/java/<package>/...         # Código fuente
├── libs/                               # Dependencias reales (JARs). Versionado.
├── lib_ext/                            # Librerías externas. NO versionado (.gitignore).
├── temp/                               # Archivos temporales. NO versionado (.gitignore).
├── docs/
│   ├── WORKFLOW_DINAMYC_COMBAT_26.1.2.md  # Este documento
│   └── curseforge/                    # Documentación para CurseForge
│       ├── project_vars.md
│       ├── project_description.md
│       └── versions/
├── CHANGELOG.md
├── README.md
└── graphify-out/
    ├── graph.html
    ├── GRAPH_REPORT.md
    └── graph.json
```

### Archivos de CurseForge

| Archivo | Propósito |
|---|---|
| `docs/curseforge/project_vars.md` | Variables del proyecto (ID, token, versiones) |
| `docs/curseforge/project_description.md` | Descripción completa del proyecto |
| `docs/curseforge/versions/<version>.md` | Release notes de cada versión |

### Formato de descripciones CurseForge

Usamos HTML tanto para la descripción general como para las release notes.

**Regla importante**: El valor del campo `changelog` debe ser **exactamente el contenido del archivo** `docs/curseforge/versions/<version>.md`.

---

## Ramas

| Rama | Propósito |
|---|---|
| `main` | Vacía. Solo commit inicial. No se usa |
| `minecraft/26.1.2/neoforge-26.1.2.78/production` | Rama de trabajo |
| `minecraft/26.1.2/neoforge-26.1.2.78/main` | Rama pública para mirror a GitHub |

---

## Versionado

| Estado | Formato | Ejemplos |
|---|---|---|
| Beta | `0.0.0-beta.X` | `0.0.0-beta.1` |
| Release | `X.Y.Z` (SemVer) | `1.0.0` |

### Nombre del JAR

`<mod_id>-<minecraft_version>-<framework>-<version>.jar`
Ejemplo: `dinamyc_combat-26.1.2-neoforge-1.0.4.jar`

---

## Commits (Conventional Commits)

```
<tipo>: <descripción>

v<version>
```

Tipos: `feat`, `fix`, `refactor`, `docs`, `chore`, `style`, `perf`, `test`

---

## Tags (GitLab)

| Estado | Formato | Ejemplo |
|---|---|---|
| Beta | `<mc-version>-neoforge-beta.X` | `26.1.2-neoforge-beta.21` |
| Release | `<mc-version>-neoforge-X.Y.Z` | `26.1.2-neoforge-1.0.0` |

---

## Flujo completo

### 1. Desarrollo
```bash
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production
# Cambios en código
./gradlew.bat build
git add -A
git commit -m "feat: add feature
v1.0.0"
git push
```

### 2. Copiar a instancia de pruebas
```bash
./gradlew.bat clean build
# PREGUNTAR antes de copiar
```

### 3. Probar en instancia

### 4. Preparar versión para CurseForge
```bash
# PREGUNTAR antes de subir
# Actualizar gradle.properties
# Crear release notes en docs/curseforge/versions/<version>.md
# Actualizar CHANGELOG.md
# Commit bump
git commit -m "chore: bump version to X.Y.Z"
# Tag
git tag -a 26.1.2-neoforge-X.Y.Z -m "vX.Y.Z: descripcion"
git push origin 26.1.2-neoforge-X.Y.Z
# PREGUNTAR antes de subir JAR
```

### 5. Release estable
```bash
# Igual que paso 4 con releaseType=release
```

---

## Buenas prácticas

- Un commit por cambio lógico
- Commit y push después de cada cambio funcional
- CHANGELOG.md siempre actualizado
- Siempre `clean build` antes del JAR final
- Sin archivos basura (`nul`, `TEMPLATE_LICENSE.txt`, etc.)
- README.md en inglés
- Sin residuos del mod original (Better Combat)

---

## Idioma

| Ámbito | Idioma |
|---|---|
| Código, logs, commits | Inglés |
| README.md | Inglés |
| Documentación interna (docs/, CHANGELOG, WORKFLOW) | Castellano |
| CurseForge | Inglés |

---

## Historial de versiones del workflow

| Versión | Fecha | Cambios |
|---|---|---|
| 1.0.0 | 2026-07-21 | Versión inicial desde WORKFLOW_GENERIC.md |
