package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.test.mockComplete
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.sample.data.repositories.UserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class ClearUsersUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var useCase: ClearUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = ClearUsersUseCase(userRepository)
    }

    @Test
    fun `given error when executed then propagate error`() {
        val exception = IllegalStateException()
        userRepository.removeAllFromLocal().mockError(exception)

        useCase.execute()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when executed then complete`() {
        userRepository.removeAllFromLocal().mockComplete()

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertComplete()
    }


}
