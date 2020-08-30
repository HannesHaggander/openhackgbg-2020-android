package com.towerowl.openhackgbg2020.models

import androidx.lifecycle.ViewModel
import com.towerowl.openhackgbg2020.R
import com.towerowl.openhackgbg2020.repositories.ApiRepository
import java.util.*

class SharedItemsViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    suspend fun getItemsForCommunity(community: Community): List<SharedItem> {
        return listOf(
            SharedItem(UUID.randomUUID(), "Impact drill", 1, R.drawable.impactdrill),
            SharedItem(UUID.randomUUID(), "Coal grill", 0, R.drawable.grill),
            SharedItem(UUID.randomUUID(), "Lawn mower", 1, R.drawable.lawnmower)
        )
    }

}