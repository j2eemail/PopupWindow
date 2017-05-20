package com.lxs.popupwindow;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/5/20.
 */

public interface PopupWindowMethod {

    void init();

    void showAsDropDown();

    void showAtLocation();

    void setAdjustScreenBrightness(@FloatRange(from = 0.0f, to = 1.0f) float alpha);

    void update();

    void popupWindowDismiss();
}
