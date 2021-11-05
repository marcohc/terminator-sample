package com.marcohc.terminator.sample.feature.users

import android.app.Activity

interface UsersNavigator {

    fun goToProfile(activity: Activity, requestCode: Int, id: Int)
}
