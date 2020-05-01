package com.mobile_app.themovie.presentation.ui.catalog_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.presentation.ui.catalog_list.CatalogListAdapter.CatalogHolder
import com.mobile_app.themovie.presentation.util.hide
import com.mobile_app.themovie.presentation.util.isVisible
import com.mobile_app.themovie.presentation.util.show

class CatalogListAdapter(
    private val catalogClickListener: (Int) -> Unit,
    private val removeCatalogListener: (Catalog) -> Unit
) : RecyclerView.Adapter<CatalogHolder>() {

    private var catalogs: List<Catalog> = ArrayList()

    fun setCatalogs(data: List<Catalog>) {
        catalogs = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_catalog, parent, false)
        return CatalogHolder(contactView)
    }

    override fun onBindViewHolder(holder: CatalogHolder, position: Int) {
        val catalog = catalogs[position]
        holder.catalogName.text = catalog.title
        holder.cardHolder.setOnClickListener {
            if (holder.binHolder.isVisible()) {
                holder.binHolder.hide()
            } else {
                catalog.id?.let { catalogClickListener.invoke(it) }
            }
        }

        holder.cardHolder.setOnLongClickListener {
            holder.binHolder.show()
            true
        }

        holder.binHolder.hide()
        holder.binHolder.setOnClickListener {
            removeCatalogListener.invoke(catalog)
        }
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    // stores and recycles views as they are scrolled off screen
    class CatalogHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val catalogName: TextView
        val cardHolder: CardView
        val binHolder: ImageView

        init {
            catalogName = itemView.findViewById(R.id.tv_catalog_name)
            cardHolder = itemView.findViewById(R.id.cv_catalog)
            binHolder = itemView.findViewById(R.id.remove_bin_catalog)
        }
    }
}