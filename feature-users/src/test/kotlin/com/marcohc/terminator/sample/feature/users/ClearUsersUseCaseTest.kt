package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.sample.data.repositories.UserRepository
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

// TODO: Implement
internal class ClearUsersUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var useCase: ClearUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = ClearUsersUseCase(userRepository)
    }

}
