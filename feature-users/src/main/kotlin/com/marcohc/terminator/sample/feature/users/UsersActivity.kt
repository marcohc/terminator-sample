package com.marcohc.terminator.sample.feature.users

import android.os.Bundle
import android.widget.Toast
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcohc.terminator.core.mvi.ui.MviActivity
import com.marcohc.terminator.core.mvi.ui.MviConfig
import com.marcohc.terminator.core.mvi.ui.MviConfigType
import com.marcohc.terminator.core.utils.setVisibleEitherGone
import com.marcohc.terminator.core.utils.unsafeLazy
import com.marcohc.terminator.sample.feature.users.adapter.UserItem
import com.marcohc.terminator.sample.feature.users.adapter.UsersAdapter
import com.marcohc.terminator.sample.feature.users.databinding.UsersActivityBinding

class UsersActivity : MviActivity<UsersIntention, UsersState>() {

    override val mviConfig = MviConfig(
        scopeId = UsersModule.scopeId,
        layoutId = R.layout.users_activity,
        mviConfigType = MviConfigType.SCOPE_AND_NAVIGATION
    )

    private lateinit var recyclerAdapter: UsersAdapter
    private val viewBinding: UsersActivityBinding by unsafeLazy { UsersActivityBinding.bind(inflatedView) }

    override fun afterComponentCreated(savedInstanceState: Bundle?) {
        viewBinding.usersProgressBar.indeterminateDrawable.colorFilter = BlendModeColorFilterCompat
            .createBlendModeColorFilterCompat(
                R.color.colorPrimary, BlendModeCompat.SRC_ATOP
            )

        recyclerAdapter = UsersAdapter()
        val layoutManager = LinearLayoutManager(this)
        viewBinding.usersRecyclerView.apply {
            adapter = recyclerAdapter
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }

        recyclerAdapter.setOnItemClickListener { _, _, item ->
            when (item) {
                is UserItem.User -> sendIntention(UsersIntention.ItemClick(item))
            }
        }

        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            sendIntention(UsersIntention.PullToRefresh)
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }

        sendIntention(UsersIntention.Initial)
    }

    override fun render(state: UsersState) {
        with(state) {
            viewBinding.usersProgressBar.setVisibleEitherGone(loading)
            recyclerAdapter.setData(items)
            viewBinding.usersErrorText.setVisibleEitherGone(error)
            showErrorExecutable.execute {
                Toast.makeText(this@UsersActivity, R.string.widget_general_error, Toast.LENGTH_LONG).show()
            }
        }
    }
}
