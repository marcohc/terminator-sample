package com.marcohc.terminator.sample.resources

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyGlideApp : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.ERROR)
    }
}
