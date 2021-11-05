package com.marcohc.terminator.sample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marcohc.terminator.sample.data.dao.UserDao
import com.marcohc.terminator.sample.data.model.User

@Database(
    entities = [
        User::class
    ],
    exportSchema = true,
    version = 1
)
internal abstract class RoomDatabaseImpl : RoomDatabase() {

    abstract fun venueDao(): UserDao
}

internal object AppDatabase {

    private const val DATABASE_NAME = "random_users.db"
    private var INSTANCE: RoomDatabaseImpl? = null

    @JvmField
    var DATABASE_PATH = ""

    fun getAppDatabase(context: Context): RoomDatabaseImpl {
        if (INSTANCE == null) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext, RoomDatabaseImpl::class.java,
                    DATABASE_NAME
                )
                .build()
            DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).absolutePath
        }
        return INSTANCE as RoomDatabaseImpl
    }
}
