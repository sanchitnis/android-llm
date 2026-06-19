package com.admission.counselor.domain;

import com.admission.counselor.data.db.CourseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class RAGRetrievalManager_Factory implements Factory<RAGRetrievalManager> {
  private final Provider<CourseDao> courseDaoProvider;

  public RAGRetrievalManager_Factory(Provider<CourseDao> courseDaoProvider) {
    this.courseDaoProvider = courseDaoProvider;
  }

  @Override
  public RAGRetrievalManager get() {
    return newInstance(courseDaoProvider.get());
  }

  public static RAGRetrievalManager_Factory create(Provider<CourseDao> courseDaoProvider) {
    return new RAGRetrievalManager_Factory(courseDaoProvider);
  }

  public static RAGRetrievalManager newInstance(CourseDao courseDao) {
    return new RAGRetrievalManager(courseDao);
  }
}
