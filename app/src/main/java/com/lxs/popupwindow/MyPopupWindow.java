package com.lxs.popupwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/5/20.
 */

public class MyPopupWindow extends PopupWindow implements PopupWindowMethod {

    private PopupWindowOnClickListener popupWindowOnClickListener;
    private AdjustScreenBrightnessListener adjustScreenBrightnessListener;
    private float alpha;
    private float dismissAlpha;
    private View rootView;
    private View referenceView;//参照物布局
    private int width;
    private int height;
    private int animationStyle;//设置进出动画
    private Drawable background;//背景
    private boolean focusable;//设置可以获取集点
    private boolean outsideTouchable;//设置点击外边可以消失
    private boolean touchable;//设置可以触摸
    private int x;
    private int y;
    private int gravity;

    private MyPopupWindow(Builder builder) {
        this.popupWindowOnClickListener = builder.popupWindowOnClickListener;
        this.adjustScreenBrightnessListener = builder.adjustScreenBrightnessListener;
        this.alpha = builder.alpha;
        this.dismissAlpha = builder.dismissAlpha;
        this.rootView = builder.rootView;
        this.referenceView = builder.referenceView;
        this.width = builder.width;
        this.height = builder.height;
        this.animationStyle = builder.animationStyle;
        this.background = builder.background;
        this.focusable = builder.focusable;
        this.outsideTouchable = builder.outsideTouchable;
        this.touchable = builder.touchable;
        this.x = builder.x;
        this.y = builder.y;
        this.gravity = builder.gravity;
        init();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setAdjustScreenBrightness(alpha);
    }

    @Override
    public void init() {
        setContentView(rootView);
        setWidth(width);
        setHeight(height);
        setAnimationStyle(animationStyle);
        setBackgroundDrawable(background);
        setFocusable(focusable);
        setOutsideTouchable(outsideTouchable);
        setTouchable(touchable);
        if (popupWindowOnClickListener != null) {
            popupWindowOnClickListener.popupWindowOnClick(rootView);
        }
        rootView.setOnKeyListener(new View.OnKeyListener() {//返回键监听
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showAsDropDown() {
        if (referenceView != null) {
            setAdjustScreenBrightness(dismissAlpha);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                showAsDropDown(referenceView, x, y, gravity);
            } else {
                showAsDropDown(referenceView, x, y);
            }
        } else {
            Log.d("CESHI", "没有设置referenceView");
        }
    }

    @Override
    public void showAtLocation() {
        if (referenceView != null) {
            setAdjustScreenBrightness(dismissAlpha);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                showAsDropDown(referenceView, x, y, gravity);
            } else {
                showAsDropDown(referenceView, x, y);
            }
        } else {
            Log.d("CESHI", "没有设置referenceView");
        }
    }

    @Override
    public void setAdjustScreenBrightness(float alpha) {
        if (adjustScreenBrightnessListener != null) {
            adjustScreenBrightnessListener.setAdjustScreenBrightness(alpha);
        }
    }

    @Override
    public void popupWindowDismiss() {
        popupWindowOnClickListener = null;
        adjustScreenBrightnessListener = null;
        rootView = null;
        referenceView = null;
        background = null;
    }

    public static class Builder {

        private PopupWindowOnClickListener popupWindowOnClickListener;//弹出布局点击监听
        private AdjustScreenBrightnessListener adjustScreenBrightnessListener;//改变屏幕亮度监听
        private float alpha;//正常情况的屏幕亮度值
        private float dismissAlpha;//弹框时屏幕的亮度值
        private View rootView;//弹出布局
        private View referenceView;//参照物布局
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        private int height = WindowManager.LayoutParams.MATCH_PARENT;
        private int animationStyle = -1;//设置进出动画
        private Drawable background = new ColorDrawable(0x00000000);//背景
        private boolean focusable = true;//设置可以获取集点
        private boolean outsideTouchable = true;//设置点击外边可以消失
        private boolean touchable = true;//设置可以触摸
        private int x;
        private int y;
        private int gravity;

        public Builder(@NonNull Context context, @LayoutRes int resource) {
            this.rootView = LayoutInflater.from(context).inflate(resource, null);
        }

        public Builder(@NonNull Context context, @LayoutRes int resource, int width, int height) {
            this.rootView = LayoutInflater.from(context).inflate(resource, null);
            this.width = width;
            this.height = height;
        }

        public Builder setPopupWindowOnClickListener(PopupWindowOnClickListener popupWindowOnClickListener) {
            this.popupWindowOnClickListener = popupWindowOnClickListener;
            return this;
        }

        public Builder setAdjustScreenBrightnessListener(AdjustScreenBrightnessListener adjustScreenBrightnessListener) {
            this.adjustScreenBrightnessListener = adjustScreenBrightnessListener;
            return this;
        }

        public Builder setAlpha(@FloatRange(from = 0.0f, to = 1.0f) float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder setDismissAlpha(@FloatRange(from = 0.0f, to = 1.0f) float dismissAlpha) {
            this.dismissAlpha = dismissAlpha;
            return this;
        }

        public Builder setReferenceView(View referenceView) {
            this.referenceView = referenceView;
            return this;
        }

        public Builder setWidth(@NonNull int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(@NonNull int height) {
            this.height = height;
            return this;
        }

        public Builder setAnimationStyle(@StyleRes int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setBackground(Drawable background) {
            this.background = background;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setOutsideTouchable(boolean outsideTouchable) {
            this.outsideTouchable = outsideTouchable;
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }

        public Builder setX(@Nullable int x) {
            this.x = x;
            return this;
        }

        public Builder setY(@Nullable int y) {
            this.y = y;
            return this;
        }

        public Builder setGravity(@Nullable int gravity) {
            this.gravity = gravity;
            return this;
        }

        public MyPopupWindow build() { // 构建，返回一个新对象
            return new MyPopupWindow(this);
        }
    }
}
