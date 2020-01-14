package br.com.pecasparamotos.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel stores UI-related data that isn’t destroyed on app rotations.
 *
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 */
class SharedItemModel : ViewModel() {
    /**
     * LiveData is an observable data holder class
     *
     * @see https://developer.android.com/topic/libraries/architecture/livedata
     */
    val liveDataItems: MutableLiveData<ArrayList<Item>>

    val itemsList: ArrayList<Item>

    init {
        itemsList = ArrayList()

        liveDataItems = MutableLiveData()
        liveDataItems.value = itemsList
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    fun insertItem(isChecked: Boolean, item: Item) {
        if (isChecked) {
            liveDataItems.value?.add(item)
        } else {
            liveDataItems.value?.remove(item)
        }

        liveDataItems.notifyObserver()
    }
}