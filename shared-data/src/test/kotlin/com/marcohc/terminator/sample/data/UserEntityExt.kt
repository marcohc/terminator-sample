package com.marcohc.terminator.sample.data

import com.marcohc.terminator.sample.data.api.NameResponse
import com.marcohc.terminator.sample.data.api.PictureResponse
import com.marcohc.terminator.sample.data.api.UserEntity

object UserEntityExt {

    fun mockUserEntity() = UserEntity(
        email = "nerdy.garcia@gmail.com",
        name = NameResponse(title = "Mr", first = "Nerdy", last = "Garcia"),
        picture = PictureResponse("", "https://randomuser.me/api/portraits/men/11.jpg", "")
    )
}
