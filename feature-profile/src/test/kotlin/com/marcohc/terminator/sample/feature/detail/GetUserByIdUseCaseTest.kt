package com.marcohc.terminator.sample.feature.detail

import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.data.repositories.ConnectionManager
import com.marcohc.terminator.sample.data.repositories.UserRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.internal.operators.single.SingleJust
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetUserByIdUseCaseTest {

    @Mock
    lateinit var connectionManager: ConnectionManager

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var useCase: GetVenueByIdUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetVenueByIdUseCase(
            connectionManager,
            userRepository,
            Schedulers.trampoline()
        )
    }

    @Test
    fun `given connection when use case executes then return items`() {
        val city = "Madrid"
        val item = User()
        whenever(connectionManager.isConnected()).thenReturn(true)
        whenever(userRepository.getByIdFromLocal(city)).thenReturn(SingleJust(item))
        whenever(userRepository.getByIdFromNetwork(city)).thenReturn(SingleJust(item))
        whenever(userRepository.save(item)).thenReturn(Completable.complete())

        useCase.execute(city).test().assertValue { it == item }
    }

    @Test
    fun `given connection when use case executes then save items`() {
        val city = "Madrid"
        val item = User()
        whenever(connectionManager.isConnected()).thenReturn(true)
        whenever(userRepository.getByIdFromNetwork(city)).thenReturn(SingleJust(item))
        whenever(userRepository.getByIdFromLocal(city)).thenReturn(SingleJust(item))

        useCase.execute(city).test()

        verify(userRepository).save(item)
    }

    @Test
    fun `given no connection when use case executes then return local items`() {
        val city = "Madrid"
        val item = User()
        whenever(connectionManager.isConnected()).thenReturn(false)
        whenever(userRepository.getByIdFromLocal(city)).thenReturn(SingleJust(item))

        useCase.execute(city).test().assertValue { it == item }
    }
}
