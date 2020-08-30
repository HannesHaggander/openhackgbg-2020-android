package com.towerowl.openhackgbg2020.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towerowl.openhackgbg2020.App
import com.towerowl.openhackgbg2020.R
import com.towerowl.openhackgbg2020.models.Community
import com.towerowl.openhackgbg2020.models.SharedItem
import kotlinx.android.synthetic.main.fragment_shared_items.*
import kotlinx.android.synthetic.main.view_holder_shared_item_item.view.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedItemsFragment : Fragment() {

    private lateinit var sharedItemsAdapter: SharedItemsAdapter

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
        getSharedItems()
    }

    private fun setupRecycler() {
        with(shared_items_recycler) {
            adapter = SharedItemsAdapter { index, clicked ->
                if (clicked.isAvailable()) {
                    clicked.claim()
                    sharedItemsAdapter.data[index] = clicked
                    sharedItemsAdapter.notifyItemChanged(index)
                }
            }.also { sharedItemsAdapter = it }
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun getSharedItems() {
        arguments?.getParcelable<Community>(Community::class.java.simpleName)?.run {
            lifecycleScope.launch(IO) {
                App.instance()
                    .globalComponent
                    .sharedItemsViewModel()
                    .getItemsForCommunity(this@run)
                    .let { items ->
                        withContext(Main) {
                            sharedItemsAdapter.data = items.toMutableList()
                        }
                    }
            }

        }

//        sharedItemsAdapter.data = mutableListOf(
//            SharedItem(UUID.randomUUID(), "Impact drill", 1),
//            SharedItem(UUID.randomUUID(), "Coal grill", 0),
//            SharedItem(UUID.randomUUID(), "Lawn mower", 1)
//        )
    }


    private class SharedItemsAdapter(private val onClick: (Int, SharedItem) -> Unit) :
        RecyclerView.Adapter<SharedItemsViewHolder>() {
        var data = mutableListOf<SharedItem>()
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
                vh_shared_item_claim.isEnabled = item.isAvailable()
                vh_shared_item_claim.text = context.getString(
                    if (item.isAvailable()) R.string.claim
                    else R.string.unavailable
                )
                vh_shared_item_claim.setOnClickListener { onClick(position, item) }
                vh_shared_item_text.text = item.name
            }
        }

        override fun getItemCount(): Int = data.size

    }

    private class SharedItemsViewHolder(v: View) : RecyclerView.ViewHolder(v)
}