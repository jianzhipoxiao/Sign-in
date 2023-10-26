package com.jszx.utils;

/**
 * @author 刘林
 * @version 1.0
 */
public enum Sign {
    SIGIN_PLACE("天津商业大学阳光网站");
    //签到地点
    private String place;
    private Long time;

    Sign(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }
}
