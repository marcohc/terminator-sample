package com.marcohc.terminator.sample.feature.detail

import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.data.repositories.ConnectionManager
import com.marcohc.terminator.sample.data.repositories.UserRepository
import io.reactivex.Scheduler
import io.reactivex.Single

internal class GetVenueByIdUseCase(
    private val connectionManager: ConnectionManager,
    private val userRepository: UserRepository,
    private val scheduler: Scheduler
) {

    fun execute(id: String): Single<User> {

        val getByIdObservable = if (connectionManager.isConnected()) {
            userRepository.getByIdFromNetwork(id)
                .flatMap { venueFromNetwork ->
                    // Small adjustment to update full information including city
                    userRepository.getByIdFromLocal(id)
                        .flatMap { localVenue ->
                            val venue = venueFromNetwork.copy(city = localVenue.city)
                            userRepository.save(venue).toSingleDefault(venue)
                        }
                }
        } else {
            userRepository.getByIdFromLocal(id)
        }

        return getByIdObservable
            .subscribeOn(scheduler)
    }
}

