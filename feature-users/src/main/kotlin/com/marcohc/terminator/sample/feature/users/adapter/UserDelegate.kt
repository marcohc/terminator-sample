package com.marcohc.terminator.sample.feature.users.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.marcohc.terminator.core.recycler.Delegate
import com.marcohc.terminator.core.recycler.DelegateConfig
import com.marcohc.terminator.sample.feature.users.R
import com.marcohc.terminator.sample.feature.users.databinding.UserItemBinding

internal class UserDelegate : Delegate<UserItem.User> {

    override val delegateConfig = DelegateConfig.init<UserItem.User>(R.layout.user_item)

    override fun bind(
        view: View,
        item: UserItem.User,
        childOnClickListener: View.OnClickListener,
        childOnLongClickListener: View.OnLongClickListener
    ) {
        val viewBinding = UserItemBinding.bind(view)
        with(item) {
            viewBinding.fullNameText.text = fullName
            viewBinding.emailText.text = email

            Glide.with(view.context)
                .load(pictureUrl)
                .transform(CenterCrop(), RoundedCorners(ROUNDED_CORNERS_SIZE))
                .error(R.drawable.ic_empty_placeholder)
                .into(viewBinding.userImage)
        }
    }

    private companion object {
        const val ROUNDED_CORNERS_SIZE = 16
    }
}
