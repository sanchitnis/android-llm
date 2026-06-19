package com.admission.counselor.data

import android.content.Context
import com.google.android.play.core.assetpacks.AssetPackManagerFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelAssetLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val assetPackManager = AssetPackManagerFactory.getInstance(context)

    fun resolveModelPath(): Result<String> {
        val packName = "model_asset_pack"
        val location = assetPackManager.getPackLocation(packName)
        return if (location != null) {
            Result.success(location.assetsPath() + "/gemma-4-E2B-it.litertlm")
        } else {
            Result.failure(
                IllegalStateException(
                    "Model asset pack not found. Please reinstall the app from Google Play."
                )
            )
        }
    }
}
