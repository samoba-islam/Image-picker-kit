<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform Android"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
</p>

<h1 align="center">📸 ImagePickKit</h1>

<p align="center">
  <strong>A modern, high-performance Android image picker library built with Jetpack Compose and Material 3</strong>
</p>

<p align="center">
  <a href="https://jitpack.io/#samoba-islam/Image-picker-kit"><img src="https://jitpack.io/v/samoba-islam/Image-picker-kit.svg" alt="JitPack"/></a>
  <img src="https://img.shields.io/badge/API-29%2B-brightgreen.svg?style=flat" alt="API 29+"/>
  <img src="https://img.shields.io/badge/License-GPL--3.0-blue.svg" alt="License GPL-3.0"/>
</p>

---

## ✨ Features

- 🎨 **Material 3 Design** — Beautiful, modern UI following latest Material Design guidelines
- ⚡ **High Performance** — Paging 3 integration for smooth scrolling with large image libraries
- 📁 **Folder Browsing** — Navigate through device folders with ease
- ✅ **Multi-Selection** — Configurable selection limits with "Select All" capability
- 🖼️ **Modern Format Support** — Built-in AVIF/HEIF software decoding for broad device compatibility
- 🎛️ **Fully Customizable** — Control MIME types, grid layout, UI text, and more
- 📱 **Pre-selection Support** — Initialize picker with already-selected images
- 🧹 **Memory Efficient** — Built-in thumbnail caching with manual cache clearing

---

## 📦 Installation

Add JitPack repository to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.samoba-islam:Image-picker-kit:1.0.0")
}
```

---

## 🚀 Quick Start

### 1. Add Permissions

Add the necessary permissions to your `AndroidManifest.xml`:

```xml
<!-- Android 13+ (API 33+) -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

<!-- Android 10-12 (API 29-32) -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

### 2. Launch the Picker

```kotlin
import com.samoba.imagepickkit.ImagePickerScreen
import com.samoba.imagepickkit.ImagePickerConfig
import com.samoba.imagepickkit.ImagePickerResult

@Composable
fun MyScreen() {
    var showPicker by remember { mutableStateOf(false) }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    Button(onClick = { showPicker = true }) {
        Text("Select Images")
    }

    if (showPicker) {
        ImagePickerScreen(
            config = ImagePickerConfig(
                maxSelection = 10,
                title = "Select Photos"
            ),
            onResult = { result ->
                when (result) {
                    is ImagePickerResult.Success -> {
                        selectedImages = result.images.map { it.uri }
                        showPicker = false
                    }
                    ImagePickerResult.Cancelled -> {
                        showPicker = false
                    }
                }
            }
        )
    }
}
```

---

## ⚙️ Configuration

Customize the picker with `ImagePickerConfig`:

| Property | Type | Default | Description |
|:---------|:-----|:--------|:------------|
| `maxSelection` | `Int` | `Int.MAX_VALUE` | Maximum number of selectable images |
| `mimeTypes` | `List<String>` | JPEG, PNG, WebP, AVIF, HEIC, HEIF | Filter displayed media types |
| `gridColumns` | `Int` | `3` | Number of columns in the image grid |
| `title` | `String` | `"Select Images"` | Title displayed in the top app bar |
| `showSelectAll` | `Boolean` | `true` | Show/hide the "Select All" option |
| `selectedUris` | `List<Uri>` | `emptyList()` | Pre-select specific images by URI |

### Example with Full Configuration

```kotlin
ImagePickerScreen(
    config = ImagePickerConfig(
        maxSelection = 5,
        mimeTypes = listOf("image/jpeg", "image/png"),
        gridColumns = 4,
        title = "Choose up to 5 photos",
        showSelectAll = false,
        selectedUris = existingSelections
    ),
    onResult = { result -> /* Handle result */ }
)
```

---

## 📖 API Reference

### `ImagePickerScreen`

The main composable entry point for the image picker.

```kotlin
@Composable
fun ImagePickerScreen(
    config: ImagePickerConfig = ImagePickerConfig(),
    onResult: (ImagePickerResult) -> Unit
)
```

### `ImagePickerResult`

Sealed class representing the picker outcome:

- `ImagePickerResult.Success(images: List<SelectedImage>)` — User completed selection
- `ImagePickerResult.Cancelled` — User dismissed the picker

### `SelectedImage`

Data class containing selected image information:

```kotlin
data class SelectedImage(
    val uri: Uri,
    val name: String,
    val size: Long,
    val width: Int,
    val height: Int,
    val mediaId: Long
)
```

### `clearImagePickerCache()`

Utility function to manually clear the thumbnail cache:

```kotlin
// Call when picker is dismissed or on low memory
clearImagePickerCache()
```

---

## 📋 Requirements

- **Minimum SDK**: 29 (Android 10)
- **Target SDK**: 36
- **Kotlin**: 1.9+
- **Jetpack Compose**: BOM 2024.x

---

## 📄 License

```
Copyright (C) 2026 Shawon Hossain

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
```

See the [LICENSE](LICENSE) file for full details.

---

## 👤 Author

**Shawon Hossain**

- GitHub: [@samoba-islam](https://github.com/samoba-islam)
- Website: [samoba.pages.dev](https://samoba.pages.dev)

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

<p align="center">
  Made with ❤️ for the Android community
</p>
