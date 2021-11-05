@file:Suppress("NoWildcardImports")

package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.test.*
import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.feature.users.adapter.UserItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test
import org.mockito.Mock

internal class UsersInteractorTest : MviInteractorTest<UsersIntention, UsersAction, UsersState>() {

    @Mock
    lateinit var clearUsersUseCase: ClearUsersUseCase

    @Mock
    lateinit var getUsersUseCase: GetUsersUseCase

    @Mock
    lateinit var router: UsersRouter

    @Mock
    lateinit var analytics: UsersAnalytics

    override fun provideInteractor(): MviBaseInteractor<UsersIntention, UsersAction, UsersState> = UsersInteractor(
        clearUsersUseCase = clearUsersUseCase,
        getUsersUseCase = getUsersUseCase,
        router = router,
        analytics = analytics
    )

    @Test
    fun `when initial then track event`() {
        sendIntention(UsersIntention.Initial)

        verify(analytics).trackScreen()
    }

    @Test
    fun `when initial then return Loading`() {
        analytics.trackScreen().mockComplete()
        getUsersUseCase.execute().mockNever()

        sendIntention(UsersIntention.Initial)

        assertTypedStateAt<UsersState>(1) { loading }
    }

    @Test
    fun `given error when initial then return Error`() {
        analytics.trackScreen().mockComplete()
        getUsersUseCase.execute().mockError()

        sendIntention(UsersIntention.Initial)

        assertTypedStateAt<UsersState>(2) { error }
    }

    @Test
    fun `given error when initial then return show error executable`() {
        analytics.trackScreen().mockComplete()
        getUsersUseCase.execute().mockError()
        sendIntention(UsersIntention.Initial)

        assertTypedStateAt<UsersState>(2) { showErrorExecutable.isLoaded() }
    }

    @Test
    fun `given no error when initial then return items`() {
        analytics.trackScreen().mockComplete()
        getUsersUseCase.execute().mockValue(listOf(User()))

        sendIntention(UsersIntention.Initial)

        assertTypedStateAt<UsersState>(2) { items.isNotEmpty() }
    }

    @Test
    fun `when pullToRefresh then track event`() {
        sendIntention(UsersIntention.PullToRefresh)

        verify(analytics).trackPullToRefresh()
    }

    @Test
    fun `when pullToRefresh then clear users`() {
        sendIntention(UsersIntention.PullToRefresh)

        verify(clearUsersUseCase).execute()
    }

    // Pull to refresh follows the same flow as initial

    @Test
    fun `when item click then track event`() {
        val item = mock<UserItem.User>()
        whenever(router.goToProfile(item)).thenReturn(Completable.complete())

        sendIntention(UsersIntention.ItemClick(item))

        verify(analytics).trackItemClick()
    }

    @Test
    fun `when item click then go to details`() {
        val item = mock<UserItem.User>()

        sendIntention(UsersIntention.ItemClick(item))

        verify(router).goToProfile(item)
    }
}
