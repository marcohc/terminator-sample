package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.sample.data.repositories.UserRepository

/**
 * Removes all local data
 */
internal class ClearUsersUseCase(
    private val repository: UserRepository
) {

    fun execute() = repository.removeAllFromLocal()
}
