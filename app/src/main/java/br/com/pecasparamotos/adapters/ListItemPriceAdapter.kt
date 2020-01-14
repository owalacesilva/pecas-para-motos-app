package br.com.pecasparamotos.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Half
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.Item
import br.com.pecasparamotos.utils.Utils
import faranjit.currency.edittext.CurrencyEditText
import java.lang.NumberFormatException

class ListItemPriceAdapter(context: Context, itemList: ArrayList<Item>) : BaseAdapter() {

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
            contextView = inflter.inflate(R.layout.layout_item_price, null)
        } else {
            contextView = view
        }

        val item: Item = getItem(position) as Item

        val tvName = contextView.findViewById<TextView>(R.id.itemName)
        val etItemPrice = contextView.findViewById<CurrencyEditText>(R.id.etItemPrice)
        val tvSuggestedPrice = contextView.findViewById<TextView>(R.id.itemSuggestedPrice)

        tvName.text = item.name
        tvSuggestedPrice.text = Utils.formatToCurrency(item.suggestedPrice)
        etItemPrice.text = Editable.Factory.getInstance().newEditable(item.price.toString())
        etItemPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

            }

            override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                try {
                    val textStr = text.toString().replace(",", "")
                    Log.v("TEST", textStr)

                    val item: Item = getItem(position) as Item
                    item.price = textStr.toDouble()
                } catch (exc: NumberFormatException) {
                    Log.e("TEST FAILED", exc.message)
                }
            }
        })

        return contextView
    }
}