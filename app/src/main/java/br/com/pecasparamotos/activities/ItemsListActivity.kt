package br.com.pecasparamotos.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import br.com.pecasparamotos.R
import br.com.pecasparamotos.adapters.MechanicReportPagerAdapter
import br.com.pecasparamotos.fragments.MechanicReportChecklistFragment
import br.com.pecasparamotos.fragments.MechanicReportPiecesFragment
import br.com.pecasparamotos.fragments.MechanicReportServicesFragment
import br.com.pecasparamotos.interfaces.FlowControlInterface
import br.com.pecasparamotos.models.Checklist
import br.com.pecasparamotos.models.Piece
import br.com.pecasparamotos.models.Service
import br.com.pecasparamotos.models.SharedItemModel
import br.com.pecasparamotos.utils.Utils
import com.google.gson.Gson
import java.util.*

class ItemsListActivity : AppCompatActivity(), FlowControlInterface,
    MechanicReportChecklistFragment.OnFragmentInteractionListener,
    MechanicReportServicesFragment.OnFragmentInteractionListener,
    MechanicReportPiecesFragment.OnFragmentInteractionListener{

    private val TAG = "MechanicReportActivity"

    /**
     *
     */
    private var viewPager: ViewPager? = null

    /**
     *
     */
    private var fragmentList: ArrayList<Fragment>? = null

    /**
     *
     */
    private var pagerAdapter: MechanicReportPagerAdapter? = null

    /**
     *
     */
    private var originalChecklistList: ArrayList<Checklist> = ArrayList()

    /**
     *
     */
    private var originalServiceList: ArrayList<Service> = ArrayList()

    /**
     *
     */
    private var originalPieceList: ArrayList<Piece> = ArrayList()

    /**
     *
     */
    private var model: SharedItemModel? = null

    /**
     * Fragment list
     */
    private var reportChecklistFragment: MechanicReportChecklistFragment? = null
    private var reportServicesFragment: MechanicReportServicesFragment? = null
    private var reportPiecesFragment: MechanicReportPiecesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.title_activity_item_list)

        reportChecklistFragment = MechanicReportChecklistFragment.newInstance(originalChecklistList)
        reportServicesFragment = MechanicReportServicesFragment.newInstance(originalServiceList)
        reportPiecesFragment = MechanicReportPiecesFragment.newInstance(originalPieceList)

        fragmentList = ArrayList()
        fragmentList!!.add(reportChecklistFragment!!)
        fragmentList!!.add(reportServicesFragment!!)
        fragmentList!!.add(reportPiecesFragment!!)

        pagerAdapter = MechanicReportPagerAdapter(supportFragmentManager, fragmentList!!)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        with(viewPager!!) {
            offscreenPageLimit = 3
            adapter = pagerAdapter
        }

        model = ViewModelProviders.of(this).get(SharedItemModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        loadChecklistValues()
        loadServiceslistValues()
        loadPiecelistValues()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            // send result and finish
            setResult(RESULT_OK)
            finish()
        } else {
            viewPager!!.currentItem = 0
        }
    }

    private fun loadChecklistValues() {
        val inputStream = assets.open("checklist.json")
        val jsonString: String = Utils.readJson(inputStream)

        // Create a gson object
        val gson = Gson()
        val itemArray = gson.fromJson(jsonString, Array<Checklist>::class.java)
        Arrays.asList(itemArray).let {
            originalChecklistList!!.addAll(it.first())
        }
    }

    private fun loadServiceslistValues() {
        val inputStream = assets.open("servicelist.json")
        val jsonString: String = Utils.readJson(inputStream)

        // Create a gson object
        val gson = Gson()
        val itemArray = gson.fromJson(jsonString, Array<Service>::class.java)
        Arrays.asList(itemArray).let {
            originalServiceList!!.addAll(it.first())
        }
    }

    private fun loadPiecelistValues() {
        val inputStream = assets.open("piecelist.json")
        val jsonString: String = Utils.readJson(inputStream)

        // Create a gson object
        val gson = Gson()
        val itemArray = gson.fromJson(jsonString, Array<Piece>::class.java)
        listOf(itemArray).let {
            originalPieceList!!.addAll(it.first())
        }
    }

    private fun onSubmit() {
        val it = intent
        it.setClass(this, ItemPricesActivity::class.java!!)
        it.putExtra("items", model!!.itemsList)

        startActivityForResult(it, 0)

    }

    override fun onPreviousClicked() {
        val position = viewPager!!.getCurrentItem()

        if (position > 0) {
            viewPager!!.currentItem = position - 1
        }
    }

    override fun onNextClicked() {
        val position = viewPager!!.currentItem

        if (position == pagerAdapter!!.count - 1) {
            onSubmit()
        }

        viewPager!!.currentItem = position + 1
    }

    override fun onItemCheckClicked(isChecked: Boolean, item: Checklist) {
        model!!.insertItem(isChecked, item)
    }

    override fun onPieceClicked(isChecked: Boolean, item: Piece) {
        model!!.insertItem(isChecked, item)
    }

    override fun onServiceClicked(isChecked: Boolean, item: Service) {
        model!!.insertItem(isChecked, item)
    }
}
