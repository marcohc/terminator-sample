package com.marcohc.terminator.sample.data.mappers

import com.marcohc.terminator.sample.data.UserEntityExt.mockUserEntity
import com.marcohc.terminator.sample.data.api.*
import junit.framework.Assert.assertTrue
import org.junit.Test

class UserMapperTest {

    @Test
    fun `when map entities to models return models`() {
        val userEntity = mockUserEntity()

        val expected = UserMapper.mapEntitiesToModels(listOf(userEntity))

        assertTrue(expected.size == 1)
        assertTrue(
            expected[0].email == userEntity.email &&
                    expected[0].name == userEntity.name.first &&
                    expected[0].surname == userEntity.name.last &&
                    expected[0].pictureUrl == userEntity.picture.medium
        )
    }


}
