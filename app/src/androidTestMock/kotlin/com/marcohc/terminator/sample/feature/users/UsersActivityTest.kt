package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.android.test.MviActivityTest
import com.marcohc.terminator.sample.feature.users.adapter.UserItem
import org.junit.Test

internal class UsersActivityTest : MviActivityTest<UsersIntention, UsersState, UsersRobot>(
    UsersActivity::class.java,
    UsersModule.scopeId,
    UsersRobot()
) {

    private val user = UserItem.User(
        id = 0,
        fullName = "Mr. Nerdy Garcia",
        email = "nerdy.garcia@gmail.com",
        pictureUrl = "https://randomuser.me/api/portraits/men/11.jpg"
    )

    @Test
    fun whenScreenStartsThenInitialIntentionIsSent() {
        UsersIntention.Initial.assertFirstIntention()
    }

    @Test
    fun whenPullToRefreshThenPullToRefreshIntentionIsSent() {
        robot {
            pullToRefresh()
        }

        UsersIntention.PullToRefresh.assertIntentionAt(1)
    }

    @Test
    fun givenDataWhenItemClickThenPullToRefreshIntentionIsSent() {
        setState(UsersState(items = listOf(user)))

        robot {
            clickOnItem()
        }

        UsersIntention.ItemClick(user).assertIntentionAt(1)
    }

    @Test
    fun whenLoadingThenLoadingViewIsDisplayed() {
        setState(UsersState(loading = true))

        robot {
            checkLoadingViewIsDisplayed()
            checkErrorViewIsNotDisplayed()
            checkListItemViewIsEmpty()
        }
    }

    @Test
    fun whenErrorThenErrorTextIsDisplayed() {
        setState(UsersState(error = true))

        robot {
            checkLoadingViewIsNotDisplayed()
            checkErrorViewIsDisplayed()
            checkListItemViewIsEmpty()
        }
    }

    @Test
    fun whenDataThenDataIsDisplayed() {
        setState(UsersState(items = listOf(user)))

        robot {
            checkLoadingViewIsNotDisplayed()
            checkErrorViewIsNotDisplayed()
            checkListItemViewIsThisUser(user)
        }
    }
}
