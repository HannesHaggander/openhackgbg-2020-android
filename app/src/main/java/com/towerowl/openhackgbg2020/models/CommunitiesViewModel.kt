package com.towerowl.openhackgbg2020.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerowl.openhackgbg2020.ext.enqueue
import com.towerowl.openhackgbg2020.repositories.ApiRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

class CommunitiesViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    companion object {
        private const val TAG = "CommunitiesViewModel"
    }

    private val mCommunities = MutableLiveData<List<Community>>()
    val communities: LiveData<List<Community>> get() = mCommunities

    private var updateCommunityJob: Job? = null
        set(value) {
            field?.cancel("New job started")
            field = value
        }

    fun updateCommunities() {
        viewModelScope.launch(IO) {
            apiRepository.communities.getAll().enqueue(onResponse = { c, r ->
                if (!r.isSuccessful) {
                    return@enqueue
                }

                r.body()?.run { mCommunities.postValue(this) }
                    ?: throw Exception("Failed to find body")
            }, onFailure = { c, err ->
                Log.w(TAG, "updateCommunities: ", Exception(err))
            })
        }.also { updateCommunityJob = it }
    }

    fun mockCommunities() {
        mCommunities.postValue(
            listOf(
                Community(UUID.randomUUID(), "Housing association Blomman"),
                Community(UUID.randomUUID(), "Boating club Styrman")
            )
        )
    }
}