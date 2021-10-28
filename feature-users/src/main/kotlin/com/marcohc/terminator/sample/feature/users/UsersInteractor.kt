package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.ui.consumable.OneTimeExecutable
import com.marcohc.terminator.sample.data.model.User
import com.marcohc.terminator.sample.feature.users.UsersIntention.*
import com.marcohc.terminator.sample.feature.users.adapter.UserItem
import io.reactivex.Completable
import io.reactivex.Observable

internal class UsersInteractor(
    private val clearUsersUseCase: ClearUsersUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val router: UsersRouter,
    private val analytics: UsersAnalytics
) : MviBaseInteractor<UsersIntention, UsersAction, UsersState>(defaultState = UsersState()) {

    override fun intentionToAction(): (UsersIntention) -> Observable<out UsersAction> = { intention ->
        when (intention) {
            is Initial -> initial()
            is PullToRefresh -> pullToRefresh()
            is ItemClick -> itemClick(intention.item).toObservable()
        }
    }

    private fun initial() = analytics.trackScreen()
        .loadAndRenderUsers()

    private fun pullToRefresh() = analytics.trackPullToRefresh()
        .andThen(clearUsersUseCase.execute())
        .loadAndRenderUsers()

    private fun itemClick(item: UserItem.User) = analytics.trackItemClick()
        .andThen(router.goToProfile(item))

    private fun mapModelsToItems(models: List<User>): List<UserItem> {
        return models.map {
            with(it) {
                UserItem.User(
                    id = id,
                    fullName = "$name $surname",
                    email = email,
                    pictureUrl = pictureUrl
                )
            }
        }
    }

    private fun Completable.loadAndRenderUsers(): Observable<UsersAction> = andThen(getUsersUseCase.execute().toObservable())
        .map<UsersAction> { UsersAction.Render(mapModelsToItems(it)) }
        .startWith(UsersAction.Loading)
        .onErrorReturn { UsersAction.Error }

    override fun actionToState(): (UsersState, UsersAction) -> UsersState = { state, action ->
        with(state) {
            when (action) {
                is UsersAction.Loading -> copy(loading = true)
                is UsersAction.Render -> copy(
                    loading = false,
                    items = action.items,
                    error = false
                )
                is UsersAction.Error -> copy(
                    loading = false,
                    items = emptyList(),
                    error = true,
                    showErrorExecutable = OneTimeExecutable.load()
                )
            }
        }
    }
}

