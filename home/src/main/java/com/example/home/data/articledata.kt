package com.example.home.data

import android.os.Parcel
import android.os.Parcelable
import study.kotin.my.baselibrary.common.Poko

data class articledata(val id: Int,
                       val title: String,
                       val starttime: String,
                       val endtime: String,
                       val address: String,
                       val communityid: String,
                       val userid: String,
                       val content: String,
                       val createtime: String,
                       val num: List<nums>

) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            ArrayList<nums>().apply { source.readList(this, nums::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeString(starttime)
        writeString(endtime)
        writeString(address)
        writeString(communityid)
        writeString(userid)
        writeString(content)
        writeString(createtime)
        writeList(num)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<articledata> = object : Parcelable.Creator<articledata> {
            override fun createFromParcel(source: Parcel): articledata = articledata(source)
            override fun newArray(size: Int): Array<articledata?> = arrayOfNulls(size)
        }
    }
}

 data class nums(
        var toAccount: String,
        var tagProfileImNick: String,
        var tagProfileImGender: String,
        var tagProfileImBirthday: String,
        var tagProfileImSelfsignature: String,
        var tagProfileImAllowtype: String,
        var tagProfileImImage: String,
        var tagProfileImMsgsettings: String,
        var tagProfileCustomEmail: String,
        //var tagProfileCustomFollow: Any,
        var tagProfileCustomLabel: String,
        //  var tagProfileCustomPhotowa: Any,
        var tagProfileCustomSchool: String,
        var tagProfileCustomWork: String,
        var keywords: String

) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(toAccount)
        writeString(tagProfileImNick)
        writeString(tagProfileImGender)
        writeString(tagProfileImBirthday)
        writeString(tagProfileImSelfsignature)
        writeString(tagProfileImAllowtype)
        writeString(tagProfileImImage)
        writeString(tagProfileImMsgsettings)
        writeString(tagProfileCustomEmail)
        writeString(tagProfileCustomLabel)
        writeString(tagProfileCustomSchool)
        writeString(tagProfileCustomWork)
        writeString(keywords)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<nums> = object : Parcelable.Creator<nums> {
            override fun createFromParcel(source: Parcel): nums = nums(source)
            override fun newArray(size: Int): Array<nums?> = arrayOfNulls(size)
        }
    }
}