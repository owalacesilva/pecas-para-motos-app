package br.com.pecasparamotos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.Item

class ReportItemListAdapter(context: Context, itemList: ArrayList<Item>) : BaseAdapter() {

    private val context: Context

    private var itemList: List<Item>

    private var inflter: LayoutInflater

    init {
        this.context = context
        this.itemList = itemList
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var contextView: View
        if (view == null) {
            contextView = inflter.inflate(R.layout.layout_report_item_list, null)
        } else {
            contextView = view
        }

        val item: Item = getItem(position) as Item

        val itemName = contextView.findViewById<TextView>(R.id.itemName)
        val itemSuggestedPrice = contextView.findViewById<TextView>(R.id.itemSuggestedPrice)

        itemName.text = item.name
        itemSuggestedPrice.text = "R$ 0.00"

        return contextView
    }
}