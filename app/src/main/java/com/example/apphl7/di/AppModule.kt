package com.example.apphl7.di

import com.example.apphl7.data.dataLoader.HL7FileLoader
import com.example.apphl7.data.repository.HL7RepositoryImpl
import com.example.apphl7.domain.repository.HL7Repository
import com.example.apphl7.domain.usecase.LoadAndParseHL7FileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHL7Repository(loader: HL7FileLoader): HL7Repository = HL7RepositoryImpl(loader)

}