package com.example.home.data

class searchuserdata {


    var results: List<ResultsBean>? = null

    class ResultsBean {
        /**
         * sex : 男
         * expRequirement : 3
         * phoneNumber : 18584703305
         * item_keywords : ["18584703305","705988","岳飞","河北省-石家庄市-井陉矿区","内蒙古自治区-呼和浩特市-赛罕区","运营助理/专员","音乐/视频/阅读 医疗健康 数据服务","生活服务 音乐/视频/阅读 分类信息"]
         * id : 705988
         * nickname : 岳飞
         * status : 2
         * salary_request1 : [12,7]
         * city : ["河北省-石家庄市-井陉矿区","内蒙古自治区-呼和浩特市-赛罕区"]
         * itemCat1_id : ["运营助理/专员"]
         * antipositionsId : [5,7]
         * salary_request2 : [13,8]
         * itemCat2_id : ["音乐/视频/阅读 医疗健康 数据服务","生活服务 音乐/视频/阅读 分类信息"]
         * _version_ : 1620270719002738688
         * Tag_Profile_IM_Nick : 上海市
         * company : 御榜科技
         */

        var sex: String? = null
        var expRequirement: Int = 0
        var phoneNumber: String? = null
        var id: String? = null
        var nickname: String? = null
        var status: Int = 0
        var _version_: Long = 0
        var tag_Profile_IM_Nick: String? = null
        var company: String? = null
        var item_keywords: List<String>? = null
        var salary_request1: List<Int>? = null
        var city: List<String>? = null
        var itemCat1_id: List<String>? = null
        var antipositionsId: List<Int>? = null
        var salary_request2: List<Int>? = null
        var itemCat2_id: List<String>? = null
    }
}
