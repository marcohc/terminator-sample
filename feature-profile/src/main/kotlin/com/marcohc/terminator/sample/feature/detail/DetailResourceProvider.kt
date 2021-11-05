package com.marcohc.terminator.sample.feature.detail

import android.content.Context

internal class DetailResourceProvider(
    private val context: Context
) {

    fun getErrorMessage(): String = context.getString(R.string.detail_error)
}
