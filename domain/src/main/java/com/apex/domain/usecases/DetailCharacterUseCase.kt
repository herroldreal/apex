package com.apex.domain.usecases

import com.apex.domain.base.UseCase
import com.apex.domain.models.CharacterBO
import com.apex.domain.repositories.ICharacterRepository
import kotlinx.coroutines.flow.Flow

class DetailCharacterUseCase(
    private val characterRepository: ICharacterRepository,
): UseCase<Int?, Flow<CharacterBO>>() {
    override suspend fun useCaseFunction(input: Int?): Flow<CharacterBO> {
        return characterRepository.characterDetail(input)
    }
}