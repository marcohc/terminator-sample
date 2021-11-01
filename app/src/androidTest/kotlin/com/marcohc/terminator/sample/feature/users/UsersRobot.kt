package com.marcohc.terminator.sample.feature.users

import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaSwipeRefreshInteractions.refresh
import com.marcohc.terminator.core.mvi.android.test.Robot
import com.marcohc.terminator.sample.feature.users.adapter.UserItem

/**
 * This class could be reused across different instrumentation tests
 */
internal class UsersRobot : Robot {

    fun pullToRefresh() {
        refresh(R.id.swipeRefreshLayout)
    }

    fun clickOnItem() {
        clickListItem(R.id.usersRecyclerView, 0)
    }

    fun checkLoadingViewIsDisplayed() {
        assertDisplayed(R.id.usersProgressBar)
    }

    fun checkLoadingViewIsNotDisplayed() {
        assertNotDisplayed(R.id.usersProgressBar)
    }

    fun checkErrorViewIsDisplayed() {
        assertDisplayed(R.id.usersErrorText)
    }

    fun checkErrorViewIsNotDisplayed() {
        assertNotDisplayed(R.id.usersErrorText)
    }

    fun checkListItemViewIsEmpty() {
        assertListItemCount(R.id.usersRecyclerView, 0)
    }

    fun checkListItemViewIsThisUser(user: UserItem.User) {
        assertDisplayedAtPosition(
            R.id.usersRecyclerView,
            0,
            R.id.fullNameText,
            user.fullName
        )

        assertDisplayedAtPosition(
            R.id.usersRecyclerView,
            0,
            R.id.emailText,
            user.email
        )

        assertDisplayed(R.id.userImage)
    }
}
