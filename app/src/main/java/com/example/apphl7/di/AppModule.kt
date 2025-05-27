package com.example.apphl7.di

import com.example.apphl7.data.repository.HL7RepositoryImpl
import com.example.apphl7.domain.repository.HL7Repository
import com.example.apphl7.domain.usecase.ParseHL7FileUseCase
import dagger.Module

@Module
@InsallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHL7Repository(): HL7Repository = HL7RepositoryImpl()

    @Provides
    @Singleton
    fun provideParseHL7UseCase(repo: HL7Repository): ParseHL7FileUseCase {
        return ParseHL7FileUseCase(repo)

    }
}