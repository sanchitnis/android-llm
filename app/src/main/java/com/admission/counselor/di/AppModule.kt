package com.admission.counselor.di

import android.content.Context
import androidx.room.Room
import com.admission.counselor.data.db.AdmissionDatabase
import com.admission.counselor.data.db.CourseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAdmissionDatabase(@ApplicationContext context: Context): AdmissionDatabase {
        return Room.databaseBuilder(
            context,
            AdmissionDatabase::class.java,
            "admission.db"
        )
        .createFromAsset("admission.db")
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(database: AdmissionDatabase): CourseDao {
        return database.courseDao()
    }
}
