package com.marcohc.terminator.sample.data.mappers

import com.marcohc.terminator.sample.data.api.UserEntity
import com.marcohc.terminator.sample.data.model.User

internal object UserMapper {

    fun mapEntitiesToModels(entities: List<UserEntity>) = entities.map { mapEntityToModel(it) }

    private fun mapEntityToModel(entity: UserEntity) = with(entity) {
        User(
            name = name.first,
            surname = name.last,
            email = email,
            pictureUrl = picture.medium
        )
    }
}
