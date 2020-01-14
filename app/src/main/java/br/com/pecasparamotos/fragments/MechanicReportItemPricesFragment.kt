package br.com.pecasparamotos.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.pecasparamotos.interfaces.FlowControlInterface
import br.com.pecasparamotos.adapters.ListItemPriceAdapter
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.SharedItemModel
import br.com.pecasparamotos.models.Item
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MechanicReportItemsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MechanicReportItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MechanicReportItemPricesFragment : Fragment(), View.OnClickListener {
    private var listener: OnFragmentInteractionListener? = null
    private var itemList: ArrayList<Item>? = null
    private var listItemPriceAdapter: ListItemPriceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemList = ArrayList()
        listItemPriceAdapter = ListItemPriceAdapter(activity!!, itemList!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val model = ViewModelProviders.of(activity!!).get(SharedItemModel::class.java)
        model.liveDataItems.observe(this, Observer {
            itemList!!.clear()
            itemList!!.addAll(it)
            listItemPriceAdapter!!.notifyDataSetChanged()

            Log.v("TEST", "${itemList!!.size}")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_mechanic_report_item_prices, container, false)

        val listView: ListView = rootView.findViewById(R.id.lvItemPrices) as ListView
        listView.adapter = listItemPriceAdapter

        val btnPrevious = rootView.findViewById<Button>(R.id.btnPrevious)
        val btnNext = rootView.findViewById<Button>(R.id.btnNext)

        btnPrevious.setOnClickListener(this)
        btnNext.setOnClickListener(this)

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        abstract fun onItemPriceDefined(item: Item, newPrice: Float)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnPrevious -> (activity as FlowControlInterface).onPreviousClicked()
            R.id.btnNext -> (activity as FlowControlInterface).onNextClicked()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MechanicReportItemsFragment.
         */
        @JvmStatic
        fun newInstance() = MechanicReportItemPricesFragment()
    }
}
