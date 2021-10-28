package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.ui.consumable.OneTimeExecutable
import com.marcohc.terminator.sample.feature.users.adapter.UserItem

sealed class UsersIntention {
    object Initial : UsersIntention()
    object PullToRefresh : UsersIntention()
    data class ItemClick(val item: UserItem.User) : UsersIntention()
}

internal sealed class UsersAction {
    object Loading : UsersAction()
    object Error : UsersAction()
    data class Render(val items: List<UserItem> = emptyList()) : UsersAction()
}

data class UsersState(
    val loading: Boolean = false,
    val items: List<UserItem> = emptyList(),
    val error: Boolean = false,
    val showErrorExecutable: OneTimeExecutable = OneTimeExecutable.empty()
)
