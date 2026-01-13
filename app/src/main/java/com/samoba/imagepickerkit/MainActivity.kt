package com.samoba.imagepickerkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.samoba.imagepickerkit.ui.theme.ImagePickerKitTheme
import com.samoba.imagepickkit.ImagePickerResult
import com.samoba.imagepickkit.ImagePickerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagePickerKitTheme {
                val context = LocalContext.current
                var showPicker by remember { mutableStateOf(false) }
                var selectedCount by remember { mutableIntStateOf(0) }
                var selectedUris by remember { mutableStateOf(emptyList<String>()) }

                val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    val allGranted = permissions.entries.all { it.value }
                    if (allGranted) {
                        showPicker = true
                    } else {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }

                fun checkAndRequestPermissions() {
                    val allGranted = permissionsToRequest.all {
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    }
                    if (allGranted) {
                        showPicker = true
                    } else {
                        permissionLauncher.launch(permissionsToRequest)
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showPicker) {
                        ImagePickerScreen(
                            config = com.samoba.imagepickkit.ImagePickerConfig(
                                maxSelection = 10,
                                selectedUris = selectedUris
                            ),
                            onResult = { result ->
                                when (result) {
                                    is ImagePickerResult.Success -> {
                                        selectedUris = result.images.map { it.uri.toString() }
                                        selectedCount = result.images.size
                                        showPicker = false
                                    }
                                    ImagePickerResult.Cancelled -> {
                                        showPicker = false
                                    }
                                }
                            }
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Selected Images: $selectedCount")
                            Button(onClick = { checkAndRequestPermissions() }) {
                                Text("Open Image Picker")
                            }
                        }
                    }
                }
            }
        }
    }
}