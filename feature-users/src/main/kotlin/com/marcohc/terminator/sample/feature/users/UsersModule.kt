package com.marcohc.terminator.sample.feature.users

import com.marcohc.terminator.core.koin.FeatureModule
import com.marcohc.terminator.core.mvi.ext.declareActivityInteractor
import com.marcohc.terminator.core.mvi.ext.declareFactoryActivityRouter
import org.koin.dsl.module

object UsersModule : FeatureModule {

    override val scopeId = "UsersModule"

    override val module = module {

        declareFactoryActivityRouter(scopeId = scopeId) { navigationExecutor ->
            UsersRouter(
                navigationExecutor = navigationExecutor,
                applicationNavigator = get()
            )
        }

        declareActivityInteractor(scopeId = scopeId) {
            UsersInteractor(
                clearUsersUseCase = ClearUsersUseCase(repository = get()),
                getUsersUseCase = GetUsersUseCase(repository = get()),
                router = get(),
                analytics = UsersAnalytics(),
            )
        }
    }

}
