package com.github.amlcurran.showcaseview.sample.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;

/**
 * Created by Hoang on 7/4/2016.
 */
public class ArbitraryShowcaseView extends ShowcaseView {
    private Point mTargetPosition;
    private int[] mTargetDimension;
    private int mTextMargin = 0;
    private CharSequence mArbitraryText;

    private int mTextPosition = ShowcaseView.UNDEFINED;

    protected ArbitraryShowcaseView(Context context, boolean newStyle) {
        super(context, newStyle);
    }

    protected ArbitraryShowcaseView(Context context, AttributeSet attrs, int defStyle, boolean newStyle) {
        super(context, attrs, defStyle, newStyle);
    }

    @Override
    public void forceTextPosition(@TextPosition int textPosition) {
        super.forceTextPosition(textPosition);
        if (textPosition > ShowcaseView.BELOW_SHOWCASE || textPosition < ShowcaseView.UNDEFINED) {
            throw new IllegalArgumentException("ShowcaseView text was forced with an invalid position");
        }
        mTextPosition = textPosition;
    }

    public void setArbitraryTarget(final ArbitraryTarget target) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mTargetDimension = target.getDimension();
                mTargetPosition = target.getPoint();
            }
        }, 100);
    }

    public void buildArbitraryText() {
        if (mArbitraryText == null || mTargetPosition == null || mTargetDimension == null) {
            throw new IllegalStateException("The target view is not set!");
        }

        switch (mTextPosition) {
            case ShowcaseView.ABOVE_SHOWCASE:
                int targetTopPosition = mTargetPosition.y - (mTargetDimension[1]/2);
                int textBottomPosition = targetTopPosition - mTextMargin;
                int textHeight = getTextViewDimension(mArbitraryText.toString())[1];
                addTextViewToShowCase(0, textBottomPosition - textHeight, 0, 0);
                break;
            case ShowcaseView.BELOW_SHOWCASE:
                break;
            case ShowcaseView.LEFT_OF_SHOWCASE:
                break;
        }
    }

    public void addTextViewToShowCase(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        TextView tv = getTextView(mArbitraryText.toString());
        tv.setLayoutParams(lp);
        this.addView(tv);
    }

    public void setArbitraryContentText(CharSequence charSequence, int marginFromTarget) {
        this.setContentTitle("");
        this.setContentText("");
        if (charSequence != null) {
            mArbitraryText = charSequence.toString();
        }
        mTextMargin = dpToPx(marginFromTarget);
    }

    protected TextView getTextView(String content) {
        TextView tv = new TextView(getContext());
        tv.setText(content);
        return tv;
    }

    protected int[] getTextViewDimension(String content) {
        int[] dimension = new int[2];
        TextView tv = getTextView(content);
        tv.measure(0, 0);
        dimension[0] = tv.getMeasuredWidth();
        dimension[1] = tv.getMeasuredHeight();
        return dimension;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static class Builder extends ShowcaseView.Builder {

        public Builder(Activity activity) {
            super(activity);
        }

        public Builder(Activity activity, boolean useNewStyle) {
            super(activity, useNewStyle);
            this.showCaseView
        }
    }

}
