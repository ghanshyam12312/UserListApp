package ghanshyam.demo.anzusersapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ghanshyam.demo.anzusersapp.data.mapper.UsersMapper
import ghanshyam.demo.anzusersapp.data.remote.UsersApi
import ghanshyam.demo.anzusersapp.data.repository.UserRepositoryImpl
import ghanshyam.demo.anzusersapp.domain.repository.UsersRepository
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UsersRepository
}