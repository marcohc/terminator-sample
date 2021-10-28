package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.ui.navigation.ActivityNavigationExecutor
import com.marcohc.terminator.sample.feature.users.adapter.UserItem

internal class UsersRouter(
    private val navigationExecutor: ActivityNavigationExecutor,
    private val applicationNavigator: UsersNavigator
) {

    fun goToProfile(item: UserItem.User) = navigationExecutor.executeCompletable { applicationNavigator.goToProfile(it, REQUEST_CODE_DETAILS, item.id) }

    companion object {
        private const val REQUEST_CODE_DETAILS = 1234
    }

}
