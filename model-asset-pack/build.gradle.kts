plugins {
    id("com.android.asset-pack")
}

assetPack {
    packName.set("model_asset_pack")
    dynamicDelivery {
        deliveryType.set("install-time")
    }
}
