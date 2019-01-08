package com.example.home.ui.activity

import android.os.Bundle
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.R
import com.example.home.persenter.HomePersenter
import kotlinx.android.synthetic.main.qcode.*
import org.jetbrains.anko.startActivity
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity

class qcodeActivity:BaseMVPActivity<HomePersenter>(), QRCodeView.Delegate {
    override fun onScanQRCodeSuccess(result: String) {
        if(result.substring(6,result.length)=="madengwang"){
            startActivity<PersonalhomeActivity>("id" to result.substring(0,6))
            finish()
        }else if(result.substring(0,result.indexOf(":"))=="madeng"){
            startActivity<madengscanActivity>("code" to result)
            finish()
        }

    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        if(isDark){
            aaaaa.openFlashlight()
        }else{
            aaaaa.closeFlashlight()
        }
    }

    override fun onScanQRCodeOpenCameraError() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qcode)
        aaaaa.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        aaaaa.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
        aaaaa.startSpotAndShowRect();
        aaaaa.startSpot()
        aaaaa.setDelegate(this)
    }

    override fun onStop() {
        aaaaa.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        aaaaa.onDestroy()
        super.onDestroy()
    }

    override fun onRestart() {
        aaaaa.startSpotAndShowRect();
        super.onRestart()
    }
}