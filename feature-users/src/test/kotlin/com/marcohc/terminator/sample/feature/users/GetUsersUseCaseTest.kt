package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.data.repositories.ConnectionManager
import com.marcohc.terminator.sample.data.repositories.UserRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.internal.operators.single.SingleJust
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetUsersUseCaseTest {

    @Mock
    lateinit var connectionManager: ConnectionManager

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `given connection when use case executes then return items`() {
        val city = "Madrid"
        val items = emptyList<User>()
        whenever(connectionManager.isConnected()).thenReturn(true)
        whenever(userRepository.getFromNetwork()).thenReturn(SingleJust(items))

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == items }
    }

    @Test
    fun `given connection when use case executes then save items`() {
        val city = "Madrid"
        val items = emptyList<User>()
        whenever(connectionManager.isConnected()).thenReturn(true)
        whenever(userRepository.getFromNetwork()).thenReturn(SingleJust(items))

        useCase.execute().test()

        verify(userRepository).saveAll(items)
    }

    @Test
    fun `given no connection when use case executes then return local items`() {
        val city = "Madrid"
        val items = emptyList<User>()
        whenever(connectionManager.isConnected()).thenReturn(false)
        whenever(userRepository.getFromLocal()).thenReturn(SingleJust(items))

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == items }
    }

}
