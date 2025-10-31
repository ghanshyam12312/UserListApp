package ghanshyam.demo.anzusersapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProvider
import ghanshyam.demo.anzusersapp.core.viewmodel.CoroutineContextProviderIMPL
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProviderIMPL()
    }

}