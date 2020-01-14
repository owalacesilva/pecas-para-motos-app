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
import br.com.pecasparamotos.models.Piece


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "param"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MechanicReportPieceFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MechanicReportPieceFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MechanicReportPiecesFragment : Fragment(), View.OnClickListener {
    private var listener: OnFragmentInteractionListener? = null
    private var piecesList: ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            piecesList = it.getParcelableArrayList(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_mechanic_report_piece, container, false)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val llReviewParts = rootView.findViewById(R.id.ll_review_parts) as LinearLayout
        val btnPrevious = rootView.findViewById<Button>(R.id.btnPrevious)
        val btnNext = rootView.findViewById<Button>(R.id.btnNext)

        if (piecesList != null) {
        }
        for (item in piecesList!!) {
            val cb = AppCompatCheckBox(context)
            cb.text = item.name
            cb.setOnClickListener(View.OnClickListener { view ->
                val viewCb = view as CheckBox
                listener!!.onPieceClicked(viewCb.isChecked, item as Piece)
            })

            llReviewParts.addView(cb, layoutParams)
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
        abstract fun onPieceClicked(isChecked: Boolean, piece: Piece)
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
         * @return A new instance of fragment MechanicReportPieceFragment.
         */
        @JvmStatic
        fun newInstance(list: ArrayList<Piece>) =
            MechanicReportPiecesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM, list)
                }
            }
    }
}
