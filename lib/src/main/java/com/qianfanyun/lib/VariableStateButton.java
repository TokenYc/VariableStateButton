package com.qianfanyun.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;


/**
 * yc
 *
 * @auther：247067345@qq.com
 * @description：登录注册相关页面统一的按钮
 * @params：
 */

public class VariableStateButton extends AppCompatButton {

    private int defaultColor;

    private static final float DEFAULT_ALPHA_VALUE = 0.6f;


    private int mClickableAlpha;
    private int mUnClickableAlpha;
    private int mPressAlpha;
    private int mBgColor;
    private int mCorner;

    GradientDrawable gd = new GradientDrawable();//创建drawable

    public VariableStateButton(Context context) {
        this(context, null);
    }

    public VariableStateButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VariableStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultColor = Color.parseColor("#15bfff");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VariableStateButton);

        mBgColor = typedArray.getColor(R.styleable.VariableStateButton_color_background, defaultColor);
        mCorner = typedArray.getDimensionPixelSize(R.styleable.VariableStateButton_bg_corner, dp2px(context, 4));
        float unSelect = typedArray.getFloat(R.styleable.VariableStateButton_unClick_alpha, DEFAULT_ALPHA_VALUE);
        float pressed = typedArray.getFloat(R.styleable.VariableStateButton_pressed_alpha, DEFAULT_ALPHA_VALUE);
        if (unSelect < 0) {
            unSelect = 0.0f;
        }
        if (unSelect > 1) {
            unSelect = 1.0f;
        }
        if (pressed < 0) {
            pressed = 0.0f;
        }
        if (pressed > 1) {
            pressed = 1.0f;
        }
        mUnClickableAlpha = (int) (unSelect * 255);
        mPressAlpha = (int) (pressed * 255);
        mClickableAlpha = 255;
        init();
    }

    private void init() {
        gd.setColor(mBgColor);
        gd.setCornerRadius(mCorner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(gd);
        } else {
            setBackgroundDrawable(gd);
        }
        setGravity(Gravity.CENTER);

    }


    /**
     * 设置是否可点击
     * 根据是否可点击修改透明度
     *
     * @param clickable
     */
    @Override
    public void setClickable(boolean clickable) {
        if (gd != null) {
            if (clickable) {
                gd.setAlpha(mClickableAlpha);
            } else {
                gd.setAlpha(mUnClickableAlpha);
            }
        }
        super.setClickable(clickable);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            gd.setAlpha(mPressAlpha);
        } else {
            gd.setAlpha(mClickableAlpha);
        }
        return true;
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
