package com.ifootball.app.common;


public enum StandPageTypeEnum {

    ROSTRUM(0), NEARBY(1), NEWS(2), BESTHEAT(3);


    private int type;


    private StandPageTypeEnum(int type) {

        this.type = type;

    }

    public int getPageType() {
        return type;
    }

    @Override
    public String toString() {

        return String.valueOf(this.type);

    }

}
