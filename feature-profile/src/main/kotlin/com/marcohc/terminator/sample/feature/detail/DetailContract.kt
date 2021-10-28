package com.marcohc.terminator.sample.feature.detail

import com.marcohc.terminator.sample.data.model.User

sealed class DetailIntention {
    data class Initial(val venueId: String) : DetailIntention()
}

internal sealed class DetailAction {
    object Load : DetailAction()
    data class Render(val items: User) : DetailAction()
    data class Error(val errorText: String) : DetailAction()
}

sealed class DetailState {
    object Loading : DetailState()
    data class Data(val user: User) : DetailState()
    data class Error(val errorText: String) : DetailState()
}
