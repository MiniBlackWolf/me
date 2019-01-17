package study.kotin.my.usercenter.common

import com.blankj.utilcode.util.PermissionUtils

/**
 * Creat by blackwolf
 * 2019/1/17
 * system username : Administrator
 */
class permissionCallback : PermissionUtils.FullCallback {
    override fun onGranted(permissionsGranted: MutableList<String>?) {

    }

    override fun onDenied(permissionsDeniedForever: MutableList<String>?, permissionsDenied: MutableList<String>) {
        val array = ArrayList<String>()
        for (i in permissionsDenied) {
            array.add(i)
        }
        when (array.size) {
            1 -> {
                PermissionUtils.permission(array[0]).callback(this).request()
            }
            2 -> {
                PermissionUtils.permission(array[0],array[1]).callback(this).request()
            }
            3 -> {
                PermissionUtils.permission(array[0],array[1],array[2]).callback(this).request()
            }
            4->{
                PermissionUtils.permission(array[0],array[1],array[2],array[3]).callback(this).request()
            }
        }

    }
}