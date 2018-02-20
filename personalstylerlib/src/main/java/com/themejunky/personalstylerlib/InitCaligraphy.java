package com.themejunky.personalstylerlib;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Junky2 on 2/19/2018.
 */

public class InitCaligraphy {

    public InitCaligraphy(String defaultFontPath) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(defaultFontPath).setFontAttrId(R.attr.fontPath).build());
    }
}
