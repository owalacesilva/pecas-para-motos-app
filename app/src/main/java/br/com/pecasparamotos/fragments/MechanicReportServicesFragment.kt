package br.com.pecasparamotos.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import br.com.pecasparamotos.interfaces.FlowControlInterface
import br.com.pecasparamotos.R
import br.com.pecasparamotos.models.Item
import br.com.pecasparamotos.models.Service
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "param"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MechanicReportItemsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MechanicReportItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MechanicReportServicesFragment : Fragment(), View.OnClickListener {
    private var listener: OnFragmentInteractionListener? = null
    private var itemsList: ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemsList = it.getParcelableArrayList(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_mechanic_report_services, container, false)
        val cbContainer = rootView.findViewById(R.id.divCheckboxes) as ViewGroup
        val btnPrevious = rootView.findViewById<Button>(R.id.btnPrevious)
        val btnNext = rootView.findViewById<Button>(R.id.btnNext)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Select only items worned
        for (item in itemsList!!) {
            val cb = AppCompatCheckBox(context)
            cb.text = item.name
            cb.setOnClickListener(View.OnClickListener { view ->
                val viewCb = view as CheckBox
                listener!!.onServiceClicked(viewCb.isChecked, item as Service)
            })

            cbContainer.addView(cb, layoutParams)
        }

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
        abstract fun onServiceClicked(isChecked: Boolean, item: Service)
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MechanicReportItemsFragment.
         */
        @JvmStatic
        fun newInstance(list: ArrayList<Service>) =
            MechanicReportServicesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM, list)
                }
            }
    }
}
