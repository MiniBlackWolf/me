package study.kotin.my.baselibrary.data;

import java.util.List;

/**
 * Created by tonycheng on 2015/9/19.
 */
public class ProvinceList {

    /**
     * success : true
     * error_code : 0
     * message : 获取省信息成功
     * data : [{"province_id":"1","province_name":"北京"},{"province_id":"2","province_name":"天津"},{"province_id":"3","province_name":"河北"},{"province_id":"4","province_name":"山西"},{"province_id":"5","province_name":"内蒙古"},{"province_id":"6","province_name":"辽宁"},{"province_id":"7","province_name":"吉林"},{"province_id":"8","province_name":"黑龙江"},{"province_id":"9","province_name":"上海"},{"province_id":"10","province_name":"江苏"},{"province_id":"11","province_name":"浙江"},{"province_id":"12","province_name":"安徽"},{"province_id":"13","province_name":"福建"},{"province_id":"14","province_name":"江西"},{"province_id":"15","province_name":"山东"},{"province_id":"16","province_name":"河南"},{"province_id":"17","province_name":"湖北"},{"province_id":"18","province_name":"湖南"},{"province_id":"19","province_name":"广东"},{"province_id":"20","province_name":"广西"},{"province_id":"21","province_name":"海南"},{"province_id":"22","province_name":"重庆"},{"province_id":"23","province_name":"四川"},{"province_id":"24","province_name":"贵州"},{"province_id":"25","province_name":"云南"},{"province_id":"26","province_name":"西藏"},{"province_id":"27","province_name":"陕西"},{"province_id":"28","province_name":"甘肃"},{"province_id":"29","province_name":"青海"},{"province_id":"30","province_name":"宁夏"},{"province_id":"31","province_name":"新疆"},{"province_id":"32","province_name":"台湾"},{"province_id":"33","province_name":"香港"},{"province_id":"34","province_name":"澳门"}]
     */

    private boolean success;
    private int error_code;
    private String message;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * province_id : 1
         * province_name : 北京
         */

        private String province_id;
        private String province_name;

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }
    }
}
