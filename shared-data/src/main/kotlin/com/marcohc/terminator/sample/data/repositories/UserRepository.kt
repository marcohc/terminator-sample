package com.marcohc.terminator.sample.data.repositories

import com.marcohc.terminator.sample.data.api.UserApi
import com.marcohc.terminator.sample.data.dao.UserDao
import com.marcohc.terminator.sample.data.mappers.UserMapper
import com.marcohc.terminator.sample.data.model.User
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

class UserRepository(
    private val api: UserApi,
    private val dao: UserDao,
    private val scheduler: Scheduler
) {

    fun saveAll(users: List<User>): Completable = Completable.fromAction { dao.saveAll(users.toTypedArray()) }

    fun getFromNetwork(): Single<List<User>> = api.getUsers()
        .map { UserMapper.mapEntitiesToModels(it.results) }
        .subscribeOn(scheduler)

    fun getFromLocal(): Single<List<User>> = dao.getAll()
        .subscribeOn(scheduler)

    fun getByIdFromLocal(id: Int): Single<User> = dao.getById(id)
        .subscribeOn(scheduler)

    fun removeAllFromLocal(): Completable = dao.removeAll()
        .subscribeOn(scheduler)

}
