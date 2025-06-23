package com.chch.tudoong.di

import android.content.Context
import androidx.room.Room
import com.chch.tudoong.data.local.TodoDatabase
import com.chch.tudoong.data.local.database.dao.DailyDao
import com.chch.tudoong.data.local.database.dao.MetadataDao
import com.chch.tudoong.data.local.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()

    @Provides
    fun provideDailyDao(database: TodoDatabase): DailyDao = database.dailyDao()

    @Provides
    fun provideMetadataDao(database: TodoDatabase): MetadataDao = database.metadataDao()
}