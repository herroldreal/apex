package com.apex.data.di

import com.apex.data.repositories.CharacterRepository
import com.apex.domain.repositories.ICharacterRepository
import com.apex.domain.usecases.DetailCharacterUseCase
import com.apex.domain.usecases.FetchAllCharactersUseCase
import com.apex.localsource.di.localSourceModule
import com.apex.remotesource.di.remoteSourceModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        localSourceModule,
        remoteSourceModule,
    )

    factory<ICharacterRepository> { CharacterRepository(get(), get()) }

    single { FetchAllCharactersUseCase(get()) }
    single { DetailCharacterUseCase(get()) }
}