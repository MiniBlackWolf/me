package com.example.home.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class searchuserdata  {

    /**
     * total : 1
     * rows : [{"toAccount":"835267","tagProfileImNick":"睡觉睡觉睡觉觉孩子","tagProfileImGender":"Gender_Type_Male","tagProfileImBirthday":"20011214","tagProfileImSelfsignature":"社会社会","tagProfileImAllowtype":"社会社会","tagProfileImImage":"http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1545816170903.png","tagProfileImMsgsettings":"0","tagProfileCustomEmail":"64664@qq.com","tagProfileCustomFollow":null,"tagProfileCustomLabel":"不明","tagProfileCustomPhotowa":null,"tagProfileCustomSchool":"安徽农业大学","tagProfileCustomWork":"还是说","keywords":"835267睡觉睡觉睡觉觉孩子13068380650"}]
     */

    private int total;
    private List<RowsBean> rows;
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public searchuserdata(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }


    public int getItemType() {
        return itemType;
    }

    public static class RowsBean {
        /**
         * toAccount : 835267
         * tagProfileImNick : 睡觉睡觉睡觉觉孩子
         * tagProfileImGender : Gender_Type_Male
         * tagProfileImBirthday : 20011214
         * tagProfileImSelfsignature : 社会社会
         * tagProfileImAllowtype : 社会社会
         * tagProfileImImage : http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1545816170903.png
         * tagProfileImMsgsettings : 0
         * tagProfileCustomEmail : 64664@qq.com
         * tagProfileCustomFollow : null
         * tagProfileCustomLabel : 不明
         * tagProfileCustomPhotowa : null
         * tagProfileCustomSchool : 安徽农业大学
         * tagProfileCustomWork : 还是说
         * keywords : 835267睡觉睡觉睡觉觉孩子13068380650
         */

        private String toAccount;
        private String tagProfileImNick;
        private String tagProfileImGender;
        private String tagProfileImBirthday;
        private String tagProfileImSelfsignature;
        private String tagProfileImAllowtype;
        private String tagProfileImImage;
        private String tagProfileImMsgsettings;
        private String tagProfileCustomEmail;
        private Object tagProfileCustomFollow;
        private String tagProfileCustomLabel;
        private Object tagProfileCustomPhotowa;
        private String tagProfileCustomSchool;
        private String tagProfileCustomWork;
        private String keywords;

        public String getToAccount() {
            return toAccount;
        }

        public void setToAccount(String toAccount) {
            this.toAccount = toAccount;
        }

        public String getTagProfileImNick() {
            return tagProfileImNick;
        }

        public void setTagProfileImNick(String tagProfileImNick) {
            this.tagProfileImNick = tagProfileImNick;
        }

        public String getTagProfileImGender() {
            return tagProfileImGender;
        }

        public void setTagProfileImGender(String tagProfileImGender) {
            this.tagProfileImGender = tagProfileImGender;
        }

        public String getTagProfileImBirthday() {
            return tagProfileImBirthday;
        }

        public void setTagProfileImBirthday(String tagProfileImBirthday) {
            this.tagProfileImBirthday = tagProfileImBirthday;
        }

        public String getTagProfileImSelfsignature() {
            return tagProfileImSelfsignature;
        }

        public void setTagProfileImSelfsignature(String tagProfileImSelfsignature) {
            this.tagProfileImSelfsignature = tagProfileImSelfsignature;
        }

        public String getTagProfileImAllowtype() {
            return tagProfileImAllowtype;
        }

        public void setTagProfileImAllowtype(String tagProfileImAllowtype) {
            this.tagProfileImAllowtype = tagProfileImAllowtype;
        }

        public String getTagProfileImImage() {
            return tagProfileImImage;
        }

        public void setTagProfileImImage(String tagProfileImImage) {
            this.tagProfileImImage = tagProfileImImage;
        }

        public String getTagProfileImMsgsettings() {
            return tagProfileImMsgsettings;
        }

        public void setTagProfileImMsgsettings(String tagProfileImMsgsettings) {
            this.tagProfileImMsgsettings = tagProfileImMsgsettings;
        }

        public String getTagProfileCustomEmail() {
            return tagProfileCustomEmail;
        }

        public void setTagProfileCustomEmail(String tagProfileCustomEmail) {
            this.tagProfileCustomEmail = tagProfileCustomEmail;
        }

        public Object getTagProfileCustomFollow() {
            return tagProfileCustomFollow;
        }

        public void setTagProfileCustomFollow(Object tagProfileCustomFollow) {
            this.tagProfileCustomFollow = tagProfileCustomFollow;
        }

        public String getTagProfileCustomLabel() {
            return tagProfileCustomLabel;
        }

        public void setTagProfileCustomLabel(String tagProfileCustomLabel) {
            this.tagProfileCustomLabel = tagProfileCustomLabel;
        }

        public Object getTagProfileCustomPhotowa() {
            return tagProfileCustomPhotowa;
        }

        public void setTagProfileCustomPhotowa(Object tagProfileCustomPhotowa) {
            this.tagProfileCustomPhotowa = tagProfileCustomPhotowa;
        }

        public String getTagProfileCustomSchool() {
            return tagProfileCustomSchool;
        }

        public void setTagProfileCustomSchool(String tagProfileCustomSchool) {
            this.tagProfileCustomSchool = tagProfileCustomSchool;
        }

        public String getTagProfileCustomWork() {
            return tagProfileCustomWork;
        }

        public void setTagProfileCustomWork(String tagProfileCustomWork) {
            this.tagProfileCustomWork = tagProfileCustomWork;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }
    }
}
