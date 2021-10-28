package com.marcohc.terminator.sample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcohc.terminator.sample.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(users: Array<User>): List<Long>

    @Query("SELECT * FROM users WHERE id = (:id) LIMIT 1")
    fun getById(id: Int = -1): Single<User>

    @Query("SELECT * FROM users ORDER BY name")
    fun getAll(): Single<List<User>>

    @Query("DELETE FROM users")
    fun removeAll(): Completable

}
