package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.data.repositories.UserRepository
import io.reactivex.Single

/**
 * Retrieves from local and if it's empty, fetches from network and stores it to local
 */
internal class GetUsersUseCase(
    private val repository: UserRepository
) {

    fun execute(): Single<List<User>> = repository.getFromLocal()
        .flatMap { localUsers ->
            if (localUsers.isEmpty()) {
                repository.getFromNetwork()
                    .flatMapCompletable { networkUsers -> repository.saveAll(networkUsers) }
                    .andThen(repository.getFromLocal())
            } else {
                Single.just(localUsers)
            }
        }
}
