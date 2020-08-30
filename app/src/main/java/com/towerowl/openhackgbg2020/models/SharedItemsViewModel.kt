package com.towerowl.openhackgbg2020.models

import androidx.lifecycle.ViewModel
import com.towerowl.openhackgbg2020.repositories.ApiRepository
import java.util.*

class SharedItemsViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    suspend fun getItemsForCommunity(community: Community): List<SharedItem> {
        return listOf(
            SharedItem(UUID.randomUUID(), "Impact drill", 1),
            SharedItem(UUID.randomUUID(), "Coal grill", 0),
            SharedItem(UUID.randomUUID(), "Lawn mower", 1)
        )
    }

}