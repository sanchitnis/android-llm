package com.admission.counselor.di;

import com.admission.counselor.data.db.AdmissionDatabase;
import com.admission.counselor.data.db.CourseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideCourseDaoFactory implements Factory<CourseDao> {
  private final Provider<AdmissionDatabase> databaseProvider;

  public AppModule_ProvideCourseDaoFactory(Provider<AdmissionDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CourseDao get() {
    return provideCourseDao(databaseProvider.get());
  }

  public static AppModule_ProvideCourseDaoFactory create(
      Provider<AdmissionDatabase> databaseProvider) {
    return new AppModule_ProvideCourseDaoFactory(databaseProvider);
  }

  public static CourseDao provideCourseDao(AdmissionDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCourseDao(database));
  }
}
