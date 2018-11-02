package com.example.home.data


import android.os.Parcel
import android.os.Parcelable
import study.kotin.my.baselibrary.common.Poko


data class UserList(val HaedImgUrl: String, val LogoUrl: String, val Name: String, val msg: String, val noreadmsg: Int) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(HaedImgUrl)
        writeString(LogoUrl)
        writeString(Name)
        writeString(msg)
        writeInt(noreadmsg)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserList> = object : Parcelable.Creator<UserList> {
            override fun createFromParcel(source: Parcel): UserList = UserList(source)
            override fun newArray(size: Int): Array<UserList?> = arrayOfNulls(size)
        }
    }
}
