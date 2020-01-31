package br.com.pecasparamotos.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pecasparamotos.R
import br.com.pecasparamotos.adapters.ListItemPriceAdapter
import br.com.pecasparamotos.models.Item

import java.util.ArrayList

class ItemPricesActivity : AppCompatActivity() {

    /**
     *
     */
    private var recyclerView: RecyclerView? = null

    /**
     *
     */
    private var itemsList: ArrayList<Item>? = null

    /**
     *
     */
    private var listItemPriceAdapter: ListItemPriceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_prices)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.title_activity_item_prices)

        itemsList = intent.extras!!.getParcelableArrayList("items")
        listItemPriceAdapter = ListItemPriceAdapter(itemsList!!)

        recyclerView = findViewById(R.id.lvItemPrices)

        val layoutManager= LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = listItemPriceAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_btn_submit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
            R.id.action_submit -> {
                onSubmit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onSubmit() {
        val it = intent
        it.setClass(this, AnnotationActivity::class.java)
        it.putExtra("items", itemsList)

        startActivityForResult(it, 0)
    }
}
