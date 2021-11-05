package com.marcohc.terminator.sample.feature.users

import io.reactivex.Completable

internal class UsersAnalytics {

    fun trackScreen() = Completable.fromAction {
        // Track your analytics events here
    }

    fun trackPullToRefresh() = Completable.fromAction {
        // Track your analytics events here
    }

    fun trackItemClick() = Completable.fromAction {
        // Track your analytics events here
    }
}
