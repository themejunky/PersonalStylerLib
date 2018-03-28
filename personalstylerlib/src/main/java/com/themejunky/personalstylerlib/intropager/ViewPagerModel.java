package com.themejunky.personalstylerlib.intropager;

import java.io.Serializable;

public class ViewPagerModel implements Serializable {
    private int image;
    private String title;
    private String text;
    private int colorStart;
    private int colorCenter;
    private int colorEnd;
    private boolean isLastPage;


    public ViewPagerModel(int image, String title, String text, int colorStart, int colorCenter, int colorEnd, boolean isLastPage) {
        this.image = image;
        this.title = title;
        this.text = text;
        this.colorStart = colorStart;
        this.colorCenter = colorCenter;
        this.colorEnd = colorEnd;

        this.isLastPage = isLastPage;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getColorStart() {
        return colorStart;
    }

    public int getColorCenter() {
        return colorCenter;
    }

    public int getColorEnd() {
        return colorEnd;
    }


    public boolean getIsLastPage() {
        return isLastPage;
    }
}
