package com.marcohc.terminator.sample.feature.users.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.marcohc.terminator.core.recycler.Delegate
import com.marcohc.terminator.core.recycler.DelegateConfig
import com.marcohc.terminator.sample.feature.users.R
import kotlinx.android.synthetic.main.user_item.view.*

internal class UserDelegate : Delegate<UserItem.User> {

    override val delegateConfig = DelegateConfig.init<UserItem.User>(R.layout.user_item)

    override fun bind(view: View, item: UserItem.User, childOnClickListener: View.OnClickListener, childOnLongClickListener: View.OnLongClickListener) {
        with(item) {
            view.fullNameText.text = fullName
            view.emailText.text = email

            Glide.with(view.context)
                .load(pictureUrl)
                .transform(*listOf(CenterCrop(), RoundedCorners(16)).toTypedArray())
                .error(R.drawable.ic_empty_placeholder)
                .into(view.userImage)
        }
    }
}
