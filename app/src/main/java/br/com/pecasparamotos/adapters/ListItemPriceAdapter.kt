package br.com.pecasparamotos.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.Item
import br.com.pecasparamotos.utils.CurrencyEditText
import br.com.pecasparamotos.utils.Utils
import androidx.recyclerview.widget.RecyclerView


class ListItemPriceAdapter(itemList: ArrayList<Item>) : RecyclerView.Adapter<ListItemPriceAdapter.ItemPriceViewHolder>() {

    class ItemPriceViewHolder(view: View, textWatcherListener: TextWatcherListener) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.itemName)
        var tvSuggestedPrice: TextView = view.findViewById(R.id.itemSuggestedPrice)
        var etItemPrice: EditText = view.findViewById(R.id.etItemPrice)
        var etTextWatcherListener: TextWatcherListener = textWatcherListener

        init {
            etItemPrice.addTextChangedListener(etTextWatcherListener)
        }
    }

    inner class TextWatcherListener : TextWatcher {
        private var itemPosition: Int? = null

        fun updatePosition(currentPosition: Int) {
            itemPosition = currentPosition
        }

        override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(editable: Editable) {

        }

        override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
            try {
                val textStr = text.toString()
                val cleanValue = textStr.replace(CurrencyEditText.PREFIX, "").replace(",", "").trim()

                val itemTarget = itemList[itemPosition!!]
                itemTarget.price = cleanValue.toDouble()

            } catch (exc: NumberFormatException) {
                Log.e("ListItemPriceAdapter", "TEST FAILED: ${exc.message}")
            }
        }
    }


    private var itemList: List<Item> = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPriceViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_price, parent, false)
        return ItemPriceViewHolder(view, TextWatcherListener())
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemPriceViewHolder, position: Int) {
        val item: Item = itemList[position]

        holder.tvName.text = item.name
        holder.tvSuggestedPrice.text = Utils.formatToCurrency(item.suggestedPrice)
        holder.etTextWatcherListener.updatePosition(position)

        if (item.price > 0.0) {
            holder.etItemPrice.text = Editable.Factory.getInstance().newEditable(item.price.toString())
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}