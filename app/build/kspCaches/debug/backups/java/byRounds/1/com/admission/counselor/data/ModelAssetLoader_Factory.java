package com.admission.counselor.data;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ModelAssetLoader_Factory implements Factory<ModelAssetLoader> {
  private final Provider<Context> contextProvider;

  public ModelAssetLoader_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ModelAssetLoader get() {
    return newInstance(contextProvider.get());
  }

  public static ModelAssetLoader_Factory create(Provider<Context> contextProvider) {
    return new ModelAssetLoader_Factory(contextProvider);
  }

  public static ModelAssetLoader newInstance(Context context) {
    return new ModelAssetLoader(context);
  }
}
