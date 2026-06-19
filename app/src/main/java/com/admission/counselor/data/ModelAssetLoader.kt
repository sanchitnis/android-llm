package com.admission.counselor.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelAssetLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun resolveModelPath(): Result<String> {
        val modelFileName = "gemma-4-E2B-it.litertlm"

        // Location 1: App private storage (filesDir)
        val privateFile = java.io.File(context.filesDir, modelFileName)
        if (privateFile.exists() && privateFile.length() > 2_000_000_000L) {
            return Result.success(privateFile.absolutePath)
        }

        // Location 2: Scoped external app storage (no runtime permissions needed)
        // Path: /sdcard/Android/data/com.admission.counselor/files/gemma-4-E2B-it.litertlm
        val extFilesDir = context.getExternalFilesDir(null)
        val extFile = if (extFilesDir != null) java.io.File(extFilesDir, modelFileName) else null
        if (extFile != null && extFile.exists() && extFile.length() > 2_000_000_000L) {
            return Result.success(extFile.absolutePath)
        }

        // Location 3: Public Download folder
        // Path: /sdcard/Download/gemma-4-E2B-it.litertlm
        val downloadFolder = android.os.Environment.getExternalStoragePublicDirectory(
            android.os.Environment.DIRECTORY_DOWNLOADS
        )
        val downloadFile = java.io.File(downloadFolder, modelFileName)
        if (downloadFile.exists() && downloadFile.length() > 2_000_000_000L) {
            // Copy to scoped storage to allow write-access for compiler cache (.xnnpack_cache) companion files
            val targetFile = extFile ?: privateFile
            try {
                android.util.Log.i("ModelAssetLoader", "Model detected in Downloads. Copying to app-scoped storage to enable caching...")
                java.io.FileInputStream(downloadFile).use { input ->
                    java.io.FileOutputStream(targetFile).use { output ->
                        input.copyTo(output)
                    }
                }
                android.util.Log.i("ModelAssetLoader", "Model file copied successfully. Sideload copy complete.")
                
                // Attempt to clean up source file from Download/ folder to free up space (requires MANAGE_EXTERNAL_STORAGE)
                try {
                    if (downloadFile.delete()) {
                        android.util.Log.i("ModelAssetLoader", "Cleaned up source model file from Download folder.")
                    } else {
                        android.util.Log.i("ModelAssetLoader", "Automatic cleanup skipped. User must delete download file manually.")
                    }
                } catch (ex: Exception) {
                    android.util.Log.e("ModelAssetLoader", "Cleanup failed", ex)
                }

                return Result.success(targetFile.absolutePath)
            } catch (e: Exception) {
                android.util.Log.e("ModelAssetLoader", "Failed to copy sideloaded model from Downloads", e)
                // Last resort fallback: Run directly from Download folder (caching will be disabled/fail silently)
                return Result.success(downloadFile.absolutePath)
            }
        }

        return Result.failure(
            IllegalStateException(
                "Model file '$modelFileName' not found. Please sideload the model to standard 'Download' folder or the app's external files directory."
            )
        )
    }
}
