package br.com.pecasparamotos.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class Item : Parcelable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("price")
    var price: Double = 0.0

    @SerializedName("suggested_price")
    var suggestedPrice: Double = 0.0

    constructor() {}

    constructor(id: String, name: String, price: Double, suggestedPrice: Double) : this() {
        this.id = id
        this.name = name
        this.price = price
        this.suggestedPrice = suggestedPrice
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        price = parcel.readDouble()
        suggestedPrice = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeDouble(suggestedPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
