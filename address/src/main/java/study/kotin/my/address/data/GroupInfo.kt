package study.kotin.my.address.data

import android.os.Parcel
import android.os.Parcelable

data class GroupInfo(val name: String, val Authentication: Int, val memberNum: Long, val maxMemberNum: Long, val faceimg: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readLong(),
            source.readLong(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(Authentication)
        writeLong(memberNum)
        writeLong(maxMemberNum)
        writeString(faceimg)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GroupInfo> = object : Parcelable.Creator<GroupInfo> {
            override fun createFromParcel(source: Parcel): GroupInfo = GroupInfo(source)
            override fun newArray(size: Int): Array<GroupInfo?> = arrayOfNulls(size)
        }
    }
}