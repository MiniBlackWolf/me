package study.kotin.my.find.data;

import java.util.List;

/**
 * Creat by blackwolf
 * 2019/1/23
 * system username : Administrator
 */
public class Getfriendcicledata {

    /**
     * conment : [{"id":19,"fcmid":13,"uname":"没流量","uid":"835267","content":"asddsddfa","createtime":"2019-01-23T06:04:13.000+0000","conmentId":0,"hfname":"没流量","hfuserid":"835267"},{"id":20,"fcmid":13,"uname":"没流量","uid":"835267","content":"asddsddfa","createtime":"2019-01-23T06:04:36.000+0000","conmentId":0,"hfname":null,"hfuserid":null}]
     * circle : {"id":13,"headimg":"http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1545816170903.png","username":"噶嘎嘣","userid":"835267","content":"你试试","picture":"[http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223323612.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223318553.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223322715.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223322333.png]","createtime":"2019-01-23T06:01:53.000+0000","likeCount":1,"liker":"没流量"}
     */

    private CircleBean circle;
    private List<ConmentBean> conment;

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
    }

    public List<ConmentBean> getConment() {
        return conment;
    }

    public void setConment(List<ConmentBean> conment) {
        this.conment = conment;
    }

    public static class CircleBean {
        /**
         * id : 13
         * headimg : http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1545816170903.png
         * username : 噶嘎嘣
         * userid : 835267
         * content : 你试试
         * picture : [http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223323612.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223318553.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223322715.png,  http://picture-1257712289.cos.ap-chengdu.myqcloud.com/1548223322333.png]
         * createtime : 2019-01-23T06:01:53.000+0000
         * likeCount : 1
         * liker : 没流量
         */

        private int id;
        private String headimg;
        private String username;
        private String userid;
        private String content;
        private String picture;
        private String createtime;
        private int likeCount;
        private String liker;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getLiker() {
            return liker;
        }

        public void setLiker(String liker) {
            this.liker = liker;
        }
    }

    public static class ConmentBean {
        /**
         * id : 19
         * fcmid : 13
         * uname : 没流量
         * uid : 835267
         * content : asddsddfa
         * createtime : 2019-01-23T06:04:13.000+0000
         * conmentId : 0
         * hfname : 没流量
         * hfuserid : 835267
         */

        private int id;
        private int fcmid;
        private String uname;
        private String uid;
        private String content;
        private String createtime;
        private int conmentId;
        private String hfname;
        private String hfuserid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFcmid() {
            return fcmid;
        }

        public void setFcmid(int fcmid) {
            this.fcmid = fcmid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getConmentId() {
            return conmentId;
        }

        public void setConmentId(int conmentId) {
            this.conmentId = conmentId;
        }

        public String getHfname() {
            return hfname;
        }

        public void setHfname(String hfname) {
            this.hfname = hfname;
        }

        public String getHfuserid() {
            return hfuserid;
        }

        public void setHfuserid(String hfuserid) {
            this.hfuserid = hfuserid;
        }
    }
}
