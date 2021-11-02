package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.data.repositories.UserRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetUsersUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `given error when executed then propagate error`() {
        val exception = IllegalStateException()
        userRepository.getFromLocal().mockError(exception)

        useCase.execute()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given local users when executed then return local`() {
        val users = listOf<User>(mock())
        userRepository.getFromLocal().mockValue(users)

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it == users }
    }

    @Test
    fun `given no local users when executed then fetch from network`() {
        userRepository.getFromLocal().mockValue(emptyList())

        useCase.execute()
            .test()

        verify(userRepository).getFromNetwork()
    }

    @Test
    fun `given no local users when executed then store to local`() {
        val users = listOf<User>(mock())
        userRepository.getFromLocal().mockValue(emptyList())
        userRepository.getFromNetwork().mockValue(users)

        useCase.execute()
            .test()

        verify(userRepository).saveAll(users)
    }

    @Test
    fun `given no local users when executed then fetch from network and return local`() {
        val users = listOf<User>(mock())
        userRepository.getFromLocal().mockValue(emptyList())
        userRepository.getFromNetwork().mockValue(users)

        useCase.execute()
            .test()

        verify(userRepository, times(2)).getFromLocal()
    }

}
