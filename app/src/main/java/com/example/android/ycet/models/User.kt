package com.example.android.ycet.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id:String="",
    val firstName: String = "",
    val LastName: String = "",
    val email: String = "",
    val image: String = "",
    val date:String="",
    val mobile: Long = 0,
    val fcmToken: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readLong(),
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(firstName)
        writeString(LastName)
        writeString(email)
        writeString(image)
        writeString(date)
        writeLong(mobile)
        writeString(fcmToken)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}