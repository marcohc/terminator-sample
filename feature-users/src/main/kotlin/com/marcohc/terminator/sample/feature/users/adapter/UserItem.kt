package com.marcohc.terminator.sample.feature.users.adapter

import com.marcohc.terminator.core.recycler.RecyclerItem

sealed class UserItem : RecyclerItem {

    data class User(
            val id: Int,
            val fullName: String,
            val email: String,
            val pictureUrl: String
    ) : UserItem()
}
