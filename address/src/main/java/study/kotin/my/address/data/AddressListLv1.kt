package study.kotin.my.address.data

import com.chad.library.adapter.base.entity.MultiItemEntity
import study.kotin.my.address.Addresslistadapter

class AddressListLv1(val headurl:String,val name:String,val id:String): MultiItemEntity {
    override fun getItemType(): Int {
        return Addresslistadapter.TYPE_LEVEL_1
    }
}