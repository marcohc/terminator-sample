package com.marcohc.terminator.sample.di.shared

import com.marcohc.terminator.core.koin.CoreModule
import com.marcohc.terminator.sample.feature.users.UsersNavigator
import com.marcohc.terminator.sample.navigation.ApplicationNavigator
import org.koin.dsl.module

object NavigationModule : CoreModule {

    override val module = module {
        factory { ApplicationNavigator() }
        factory<UsersNavigator> { get<ApplicationNavigator>() }
    }
}
