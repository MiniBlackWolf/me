package study.kotin.my.address.data

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import study.kotin.my.address.Addresslistadapter

data class AddressListLv0 (val groupname:String,val fdcount:Int): AbstractExpandableItem<AddressListLv1>(), MultiItemEntity {
    override fun getItemType(): Int {
        return Addresslistadapter.TYPE_LEVEL_0
    }

    override fun getLevel(): Int {
        return 0
    }
}