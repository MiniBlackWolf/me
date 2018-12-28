package com.example.home.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class searchgroupdata  {

    /**
     * total : 11
     * rows : [{"groupid":"@TGS#254ZADTFB","ownerAccount":"705988","type":"Public","name":"1","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#25B3ADTFH","ownerAccount":"835267","type":"Public","name":"16","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#26K2ADTFQ","ownerAccount":"835267","type":"Public","name":"12","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2BO3ADTFC","ownerAccount":"835267","type":"Public","name":"19","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2EJ3ADTFA","ownerAccount":"835267","type":"Public","name":"18","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2H6ZADTFW","ownerAccount":"835267","type":"Public","name":"10","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2LG2ADTFD","ownerAccount":"835267","type":"Public","name":"11","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2R32ADTFU","ownerAccount":"835267","type":"Public","name":"15","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2SO2ADTFS","ownerAccount":"835267","type":"Public","name":"13","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2UT2ADTFZ","ownerAccount":"835267","type":"Public","name":"14","authentication":"false","school":"","faceurl":"","introduction":null},{"groupid":"@TGS#2YF3ADTFQ","ownerAccount":"835267","type":"Public","name":"17","authentication":"false","school":"","faceurl":"","introduction":null}]
     */

    private int total;
    private List<RowsBean> rows;
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public searchgroupdata(int itemType) {
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
         * groupid : @TGS#254ZADTFB
         * ownerAccount : 705988
         * type : Public
         * name : 1
         * authentication : false
         * school :
         * faceurl :
         * introduction : null
         */

        private String groupid;
        private String ownerAccount;
        private String type;
        private String name;
        private String authentication;
        private String school;
        private String faceurl;
        private String introduction;

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getOwnerAccount() {
            return ownerAccount;
        }

        public void setOwnerAccount(String ownerAccount) {
            this.ownerAccount = ownerAccount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getFaceurl() {
            return faceurl;
        }

        public void setFaceurl(String faceurl) {
            this.faceurl = faceurl;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
