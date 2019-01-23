package study.kotin.my.find.data;

/**
 * Creat by blackwolf
 * 2019/1/23
 * system username : Administrator
 */
public class ConmentData {


    /**
     * content : asddsddfa
     * fcmid : 13
     * conment_id : 0
     * hfname : 没流量
     * hfuserid : 835267
     */

    private String content;
    private int fcmid;
    private int conment_id;
    private String hfname;
    private String hfuserid;

    public ConmentData(String content, int fcmid, int conment_id, String hfname, String hfuserid) {
        this.content = content;
        this.fcmid = fcmid;
        this.conment_id = conment_id;
        this.hfname = hfname;
        this.hfuserid = hfuserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFcmid() {
        return fcmid;
    }

    public void setFcmid(int fcmid) {
        this.fcmid = fcmid;
    }

    public int getConment_id() {
        return conment_id;
    }

    public void setConment_id(int conment_id) {
        this.conment_id = conment_id;
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
