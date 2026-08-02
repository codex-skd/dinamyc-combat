# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `project_id` | `1600733` |
| `mod_id` | `dinamyc_combat` |
| `display_name` | `Dinamyc Combat` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

## Versión actual

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `26.2` |
| `framework` | `neoforge` |
| `java_version` | `25` |
| `environment` | `Client`, `Server` |

## Rama

```
minecraft/26.2/neoforge-26.2.0.32-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-1.0.0`

## Parámetros del upload

| Campo | Valor | Notas |
|-------|-------|-------|
| `displayName` | `Dinamyc Combat (1.0.0)` | Nombre visible: `display_name (version)` |
| `changelog` | HTML (no Markdown) | Ver estructura abajo |
| `changelogType` | `html` | Obligatorio para que se vea bien |
| `releaseType` | `release` | Primera versión estable del port a 26.2 |
| `gameVersions` | `[9638, 9639, 10150, 16498]` | **IDs numéricos**, no nombres (la API devuelve 400 "Expected Integer but got String" si se envían strings como `"Client"`). Ver tabla de IDs abajo |

### IDs de `gameVersions` para 26.2

Verificados contra archivos ya publicados de `utility_core` (proyecto 1601825) y `GET https://minecraft.curseforge.com/api/game/versions`:

| Nombre | ID | gameVersionTypeId |
|--------|-----|--------|
| `Client` | `9638` | 75208 |
| `Server` | `9639` | 75208 |
| `NeoForge` | `10150` | 68441 |
| `26.2` | `16498` | 86297 |

> Ojo: `GET https://minecraft.curseforge.com/api/game/versions` (con `X-Api-Token`) devuelve **varias entradas duplicadas** con el mismo nombre `26.2` pero distinto `id`/`gameVersionTypeID` (p. ej. `16498`, `16500`). Solo una es la correcta (la que ya usan los archivos existentes) — verificarlo siempre contra un archivo ya publicado antes de asumir un ID.

## Claves parseables por el script genérico

```
project_id = 1600733
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 9638, 9639, 10150, 16498
release_type = release
```

## Estructura del changelog (HTML)

```html
<h2>v1.0.0 - First Stable Release for Minecraft 26.2</h2>

<h3>Port</h3>
<ul>
<li><strong>Full port</strong>: ...</li>
</ul>

<hr>

<p><strong>JAR</strong>: <code>dinamyc_combat-26.2-neoforge-1.0.0.jar</code></p>
```

## Subir archivo (JAR)

**No usar `urllib.request` de Python** — el body multipart hecho a mano (concatenando bytes de texto UTF-8 con los bytes crudos del JAR) provoca un `500 An unhandled exception occurred` del lado de CurseForge por razones no diagnosticadas. Usar el script genérico `codex-docs/scripts/curseforge-upload.ps1` (PowerShell con `System.Net.Http.MultipartFormDataContent`, binary-safe):

```powershell
powershell -File ../../codex-docs/scripts/curseforge-upload.ps1
```

## Verificar con GET

```bash
curl -s "https://api.curseforge.com/v1/mods/1600733/files/<FILE_ID>" \
  -H "x-api-key: $2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO"
```

## Changelog

```bash
curl -s "https://api.curseforge.com/v1/mods/1600733/files/<FILE_ID>/changelog" \
  -H "x-api-key: $2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO"
```

## Descripcion del proyecto

No hay endpoint API para actualizar la descripcion. Se edita manualmente desde la web de CurseForge pegando el HTML de `docs/curseforge/project_description.md`.

## Flujo completo

1. `./gradlew clean build`
2. Actualizar `docs/curseforge/versions/<version>.md` con HTML
3. Actualizar `CHANGELOG.md`
4. `git commit -m "fix: descripcion\n\nvX.Y.Z"` + `git push`
5. `git tag -a 26.2-neoforge-<version> -m "vX.Y.Z: descripcion"` + `git push origin <tag>`
6. Subir JAR a CurseForge con el script genérico
7. Verificar con GET que el changelog se vea bien
8. Liberar manualmente desde la web si es necesario
