package study.kotin.my.baselibrary.data;

import java.util.List;

/**
 * Created by tonycheng on 2015/9/19.
 */
public class SchoolList {

    /**
     * success : true
     * error_code : 0
     * message : 获取高校信息成功
     * totalcount : 55
     * data : [{"school_id":"111","school_name":"南开大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"112","school_name":"天津大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"113","school_name":"中国民航大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"114","school_name":"天津工业大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"115","school_name":"天津科技大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"116","school_name":"天津理工大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"117","school_name":"天津医科大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"118","school_name":"天津中医药大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"119","school_name":"天津师范大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"120","school_name":"天津职业技术师范大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"121","school_name":"天津外国语大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"122","school_name":"天津财经大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"123","school_name":"天津商业大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"124","school_name":"天津天狮学院","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"125","school_name":"天津城建大学","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"126","school_name":"天津农学院","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"127","school_name":"天津体育学院","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"128","school_name":"天津音乐学院","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"129","school_name":"天津美术学院","school_pro_id":"2","school_schooltype_id":"1"},{"school_id":"130","school_name":"天津医学高等专科学校","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"131","school_name":"天津市职业大学","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"132","school_name":"天津电子信息职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"133","school_name":"天津滨海职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"134","school_name":"天津轻工职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"135","school_name":"天津公安警官职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"136","school_name":"天津现代职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"137","school_name":"天津机电职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"138","school_name":"天津工程职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"139","school_name":"天津渤海职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"140","school_name":"天津海运职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"141","school_name":"天津城市职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"142","school_name":"天津石油职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"143","school_name":"天津冶金职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"144","school_name":"天津交通职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"145","school_name":"天津铁道职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"146","school_name":"天津生物工程职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"147","school_name":"天津工艺美术职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"148","school_name":"天津广播影视职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"149","school_name":"天津艺术职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"150","school_name":"天津国土资源和房屋职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"151","school_name":"天津城市建设管理职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"152","school_name":"天津中德职业技术学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"153","school_name":"天津青年职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"154","school_name":"天津商务职业学院","school_pro_id":"2","school_schooltype_id":"2"},{"school_id":"155","school_name":"南开大学滨海学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"156","school_name":"天津外国语学院滨海外事学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"157","school_name":"天津体育学院运动与文化艺术学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"158","school_name":"天津商业大学宝德学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"159","school_name":"天津医科大学临床医学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"160","school_name":"北京科技大学天津学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"161","school_name":"天津师范大学津沽学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"162","school_name":"天津理工大学中环信息学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"163","school_name":"天津大学仁爱学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"164","school_name":"天津财经大学珠江学院","school_pro_id":"2","school_schooltype_id":"3"},{"school_id":"2684","school_name":"天津市工会管理干部学院","school_pro_id":"2","school_schooltype_id":"4"}]
     */

    private boolean success;
    private int error_code;
    private String message;
    private String totalcount;
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

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * school_id : 111
         * school_name : 南开大学
         * school_pro_id : 2
         * school_schooltype_id : 1
         */

        private String school_id;
        private String school_name;
        private String school_pro_id;
        private String school_schooltype_id;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_pro_id() {
            return school_pro_id;
        }

        public void setSchool_pro_id(String school_pro_id) {
            this.school_pro_id = school_pro_id;
        }

        public String getSchool_schooltype_id() {
            return school_schooltype_id;
        }

        public void setSchool_schooltype_id(String school_schooltype_id) {
            this.school_schooltype_id = school_schooltype_id;
        }
    }
}
