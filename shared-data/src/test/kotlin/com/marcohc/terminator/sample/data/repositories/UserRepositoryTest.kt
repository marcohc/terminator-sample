package com.marcohc.terminator.sample.data.repositories

import com.marcohc.terminator.core.mvi.test.mockComplete
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.terminator.sample.data.UserEntityExt.mockUserEntity
import com.marcohc.terminator.sample.data.api.GetUsersResponse
import com.marcohc.terminator.sample.data.api.UserApi
import com.marcohc.terminator.sample.data.dao.UserDao
import com.marcohc.terminator.sample.data.model.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    @Mock
    private lateinit var api: UserApi

    @Mock
    private lateinit var dao: UserDao

    private val scheduler = Schedulers.trampoline()

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = UserRepository(
            api = api,
            dao = dao,
            scheduler = scheduler
        )
    }

    @Test
    fun `given error when saveAll then propagate error`() {
        val users = listOf<User>(mock())
        val exception = IllegalStateException()
        dao.saveAll(any()).mockError(exception)

        repository.saveAll(users)
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when saveAll then complete`() {
        val users = listOf<User>(mock())
        dao.saveAll(any()).mockComplete()

        repository.saveAll(users)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `given error when getFromNetwork then propagate error`() {
        val exception = IllegalStateException()
        api.getUsers().mockError(exception)

        repository.getFromNetwork()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when getFromNetwork then return data`() {
        val userEntity = mockUserEntity()
        val response = GetUsersResponse(results = listOf(userEntity))
        api.getUsers().mockValue(response)

        repository.getFromNetwork()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { models -> models.size == 1 }
    }

    @Test
    fun `given error when getFromLocal then propagate error`() {
        val exception = IllegalStateException()
        dao.getAll().mockError(exception)

        repository.getFromLocal()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when getFromLocal then return data`() {
        val users = listOf<User>(mock())
        dao.getAll().mockValue(users)

        repository.getFromLocal()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(users)
    }

    @Test
    fun `given error when removeAllFromLocal then propagate error`() {
        val exception = IllegalStateException()
        dao.removeAll().mockError(exception)

        repository.removeAllFromLocal()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when removeAllFromLocal then complete`() {
        dao.removeAll().mockComplete()

        repository.removeAllFromLocal()
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
