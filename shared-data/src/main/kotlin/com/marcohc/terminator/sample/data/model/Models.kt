package com.marcohc.terminator.sample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var pictureUrl: String = ""
)
