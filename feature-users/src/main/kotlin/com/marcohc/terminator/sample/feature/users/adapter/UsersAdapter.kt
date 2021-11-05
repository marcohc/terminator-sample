package com.marcohc.terminator.sample.feature.users.adapter

import com.marcohc.terminator.core.recycler.BaseAdapter
import com.marcohc.terminator.core.recycler.Delegate

internal class UsersAdapter : BaseAdapter<UserItem>() {

    @Suppress("unchecked_cast")
    override fun getDelegatesList(): List<Delegate<UserItem>> = listOf(
        UserDelegate()
    ) as List<Delegate<UserItem>>
}
