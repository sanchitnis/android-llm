package com.admission.counselor.domain;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class PromptInjector_Factory implements Factory<PromptInjector> {
  @Override
  public PromptInjector get() {
    return newInstance();
  }

  public static PromptInjector_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PromptInjector newInstance() {
    return new PromptInjector();
  }

  private static final class InstanceHolder {
    private static final PromptInjector_Factory INSTANCE = new PromptInjector_Factory();
  }
}
