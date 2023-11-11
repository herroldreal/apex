package com.apex.domain.usecases

import androidx.paging.PagingData
import com.apex.domain.base.UseCase
import com.apex.domain.models.CharacterBO
import com.apex.domain.repositories.ICharacterRepository
import kotlinx.coroutines.flow.Flow

class FetchAllCharactersUseCase(
    private val characterRepository: ICharacterRepository,
) : UseCase<Unit?, Flow<PagingData<CharacterBO>>>() {

    override suspend fun useCaseFunction(input: Unit?): Flow<PagingData<CharacterBO>> {
        return characterRepository.getCharacters()
    }
}