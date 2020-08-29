package com.towerowl.openhackgbg2020.ui

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towerowl.openhackgbg2020.R
import kotlinx.android.synthetic.main.fragment_shared_items.*
import kotlinx.android.synthetic.main.view_holder_shared_item_item.view.*

class SharedItemsFragment : Fragment() {

    private val sharedItemsAdapter by lazy { SharedItemsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shared_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        with(shared_items_recycler) {
            adapter = sharedItemsAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }


    private class SharedItemsAdapter() : RecyclerView.Adapter<SharedItemsViewHolder>() {
        var data = listOf<ClipData.Item>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedItemsViewHolder {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_shared_item_item, parent, false)
                .run { return SharedItemsViewHolder(this) }
        }

        override fun onBindViewHolder(holder: SharedItemsViewHolder, position: Int) {
            val item = data[position]
            with(holder.itemView) {
                vh_shared_item_text.text = item.text
            }
        }

        override fun getItemCount(): Int = data.size

    }

    private class SharedItemsViewHolder(v: View) : RecyclerView.ViewHolder(v)
}