package com.admission.counselor.ui;

import android.content.Context;
import com.admission.counselor.data.ModelAssetLoader;
import com.admission.counselor.domain.LlmEngine;
import com.admission.counselor.domain.PromptInjector;
import com.admission.counselor.domain.RAGRetrievalManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CounselorViewModel_Factory implements Factory<CounselorViewModel> {
  private final Provider<RAGRetrievalManager> ragManagerProvider;

  private final Provider<PromptInjector> promptInjectorProvider;

  private final Provider<LlmEngine> llmEngineProvider;

  private final Provider<ModelAssetLoader> modelAssetLoaderProvider;

  private final Provider<Context> contextProvider;

  public CounselorViewModel_Factory(Provider<RAGRetrievalManager> ragManagerProvider,
      Provider<PromptInjector> promptInjectorProvider, Provider<LlmEngine> llmEngineProvider,
      Provider<ModelAssetLoader> modelAssetLoaderProvider, Provider<Context> contextProvider) {
    this.ragManagerProvider = ragManagerProvider;
    this.promptInjectorProvider = promptInjectorProvider;
    this.llmEngineProvider = llmEngineProvider;
    this.modelAssetLoaderProvider = modelAssetLoaderProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public CounselorViewModel get() {
    return newInstance(ragManagerProvider.get(), promptInjectorProvider.get(), llmEngineProvider.get(), modelAssetLoaderProvider.get(), contextProvider.get());
  }

  public static CounselorViewModel_Factory create(Provider<RAGRetrievalManager> ragManagerProvider,
      Provider<PromptInjector> promptInjectorProvider, Provider<LlmEngine> llmEngineProvider,
      Provider<ModelAssetLoader> modelAssetLoaderProvider, Provider<Context> contextProvider) {
    return new CounselorViewModel_Factory(ragManagerProvider, promptInjectorProvider, llmEngineProvider, modelAssetLoaderProvider, contextProvider);
  }

  public static CounselorViewModel newInstance(RAGRetrievalManager ragManager,
      PromptInjector promptInjector, LlmEngine llmEngine, ModelAssetLoader modelAssetLoader,
      Context context) {
    return new CounselorViewModel(ragManager, promptInjector, llmEngine, modelAssetLoader, context);
  }
}
