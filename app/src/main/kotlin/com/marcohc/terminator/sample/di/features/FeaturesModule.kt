package com.marcohc.terminator.sample.di.features

import com.marcohc.terminator.sample.feature.users.UsersModule
import org.koin.core.module.Module

object FeaturesModule {

    val modules: List<Module>
        get() = mutableListOf<Module>()
            .apply {
                add(UsersModule.module)
//                add(DetailModule.module)
            }
}
