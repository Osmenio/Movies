package com.fortesfilmes.enumeration;

public enum SortEnum {

    NONE(0),
    TITLE_ASC(1),
    TITLE_DESC(2),
    RATING_ASC(3),
    RATING_DESC(4);


    private int value;

    SortEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SortEnum getEnum(int value) {
        SortEnum result = SortEnum.NONE;
        switch (value) {
            case 0:
                result = SortEnum.NONE;
                break;

            case 1:
                result = SortEnum.TITLE_ASC;
                break;

            case 2:
                result = SortEnum.TITLE_DESC;
                break;

            case 3:
                result = SortEnum.RATING_ASC;
                break;

            case 4:
                result = SortEnum.RATING_DESC;
                break;
        }
        return result;
    }
}
