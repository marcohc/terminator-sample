package com.marcohc.terminator.sample.navigation

import android.app.Activity
import com.marcohc.terminator.sample.feature.users.UsersNavigator

class ApplicationNavigator : UsersNavigator {

    override fun goToProfile(activity: Activity, requestCode: Int, id: Int) {
//        activity.startActivityForResult(DetailActivity.newInstance(activity, id), requestCode)
    }
}
