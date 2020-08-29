package com.towerowl.openhackgbg2020.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towerowl.openhackgbg2020.App
import com.towerowl.openhackgbg2020.R
import com.towerowl.openhackgbg2020.models.Community
import kotlinx.android.synthetic.main.fragment_communities.*
import kotlinx.android.synthetic.main.view_holder_community_item.view.*

class CommunitiesFragment : Fragment() {

    private val communityAdapter by lazy { CommunityAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_communities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setupRecycler()
    }

    private fun addObservers() {
        with(App.instance().globalComponent.communitiesViewModel()) {
            communities.observe(viewLifecycleOwner) { communities ->
                communityAdapter.data = communities
            }
            updateCommunities()
        }
    }

    private fun setupRecycler() {
        with(communities_recycler) {
            adapter = communityAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private class CommunityAdapter() : RecyclerView.Adapter<CommunityViewHolder>() {
        var data: List<Community> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_community_item, parent, false)
                .run { return CommunityViewHolder(this) }
        }

        override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
            val item = data[position]
            with(holder.itemView) {
                vh_community_title.text = item.name
            }
        }

        override fun getItemCount(): Int = data.size

    }

    private class CommunityViewHolder(v: View) : RecyclerView.ViewHolder(v)
}
