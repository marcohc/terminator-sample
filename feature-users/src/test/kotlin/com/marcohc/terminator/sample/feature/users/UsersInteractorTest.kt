package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.test.MviInteractorTest
import com.marcohc.terminator.sample.data.repositories.ConnectionManager
import com.marcohc.terminator.sample.feature.users.adapter.UserItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.internal.operators.observable.ObservableJust
import org.junit.Test
import org.mockito.Mock

internal class UsersInteractorTest : MviInteractorTest<UsersIntention, UsersAction, UsersState>() {

    @Mock
    lateinit var router: UsersRouter
    @Mock
    lateinit var analytics: UsersAnalytics
    @Mock
    lateinit var getUsersUseCase: GetUsersUseCase

    override fun provideInteractor(): MviBaseInteractor<UsersIntention, UsersAction, UsersState> = UsersInteractor(
        getUsersUseCase,
        router,
        analytics
    )

//    @Test
//    fun `when initial then return show toast`() {
//        whenever(connectionManager.observeConnection()).thenReturn(ObservableJust(true))
//
//        sendIntention(UsersIntention.Initial)
//
//        testObserver.assertValueAt(1) { it.connected.consume()!! }
//    }

//    @Test
//    fun `when search then track event`() {
//        val city = "Amsterdam"
//        whenever(getUsersUseCase.execute()).thenReturn(Single.never())
//
//        sendIntention(UsersIntention.Search(city))
//
//        verify(analytics).logSearchClick()
//    }
//
//    @Test
//    fun `when search then return loading`() {
//        val city = "Amsterdam"
//        whenever(getUsersUseCase.execute()).thenReturn(Single.never())
//
//        sendIntention(UsersIntention.Search(city))
//
//        assertTypedStateAt<UsersState>(1) { loading }
//    }
//
//    @Test
//    fun `when search with error should return general error text`() {
//        val city = "Amsterdam"
//        val text = "bla bla"
//        whenever(getUsersUseCase.execute()).thenReturn(Single.error(NullPointerException()))
//        whenever(resourceProvider.getGeneralErrorText()).thenReturn(text)
//
//        sendIntention(UsersIntention.Search(city))
//
//        testObserver.assertValueAt(2) { it.status == text }
//    }

//    @Test
//    fun `when search with error should return location unknown text`() {
//        val city = "Amsterdam"
//        val text = "bla bla"
//        whenever(getUsersUseCase.execute()).thenReturn(Single.error(LocationUnknownException))
//        whenever(resourceProvider.getLocationUnknownText()).thenReturn(text)
//
//        sendIntention(UsersIntention.Search(city))
//
//        testObserver.assertValueAt(2) { it.status == text }
//    }
//
//    @Test
//    fun `when search with success and empty items should return no items text`() {
//        val noItemsText = "No places found in AmsterdamXXX"
//        whenever(getUsersUseCase.execute()).thenReturn(SingleJust(emptyList()))
//        whenever(resourceProvider.getNoItemsString(noItemsText)).thenReturn(noItemsText)
//
//        sendIntention(UsersIntention.Search(noItemsText))
//
//        assertTypedStateAt<UsersState>(2) { status == noItemsText}
//    }
//
//    @Test
//    fun `when search with success and empty items should return items`() {
//        val city = "Amsterdam"
//        val venue = User()
//        whenever(getUsersUseCase.execute()).thenReturn(SingleJust(listOf(venue)))
//
//        sendIntention(UsersIntention.Search(city))
//
//        testObserver.assertValueAt(2) { it.items.isNotEmpty() }
//    }

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
