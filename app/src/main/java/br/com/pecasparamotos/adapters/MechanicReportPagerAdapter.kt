package br.com.pecasparamotos.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MechanicReportPagerAdapter(
    manager: FragmentManager, // Fragment list that to be using in this adapter
    private val list: List<Fragment>
) : FragmentStatePagerAdapter(manager) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }
}
