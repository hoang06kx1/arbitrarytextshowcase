package com.github.amlcurran.showcaseview.sample.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;

/**
 * Created by Hoang on 7/4/2016.
 */
public class ArbitraryTitleShowcase extends ShowcaseView {
    private Point mTargetPosition;
    private int[] mTargetDimension;
    private View mViewTarget;
    private int mTextMargin = 0;
    private CharSequence mText;

    private int mTextPosition = ShowcaseView.UNDEFINED;

    protected ArbitraryTitleShowcase(Context context, boolean newStyle) {
        super(context, newStyle);
    }

    protected ArbitraryTitleShowcase(Context context, AttributeSet attrs, int defStyle, boolean newStyle) {
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

    public void setTargetForPositioningText(View view) {
        mViewTarget = view;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                mViewTarget.getLocationInWindow(location);
                mTargetDimension[0] = mViewTarget.getWidth();
                mTargetDimension[1] = mViewTarget.getHeight();
                int x = location[0] + mTargetDimension[0] / 2;
                int y = location[1] + mTargetDimension[1] / 2;
                mTargetPosition = new Point(x,y);
            }
        }, 100);
    }

    public void setNewTextContent(CharSequence charSequence, int marginFromTarget) {
        this.setContentTitle("");
        this.setContentText("");
        if (charSequence != null) {
            mText = charSequence.toString();
        }
        if (marginFromTarget != 0) {
            mTextMargin = dpToPx(marginFromTarget);
        }

    }

    public ArbitraryTitleShowcase buildTextContent() {
        if (mText == null || mTargetPosition == null || mTargetDimension == null) {
            throw new IllegalStateException("The target view is not set!");
        }

        switch (mTextPosition) {
            case ShowcaseView.ABOVE_SHOWCASE:
                int targetPositionTop = mTargetPosition.y - (mTargetDimension[1]/2);
                int textTopMargin = targetPositionTop - getTextViewDimension(mText.toString())[1] - mTextMargin;
                addTextViewToShowCase(tv);
                break;
            case ShowcaseView.BELOW_SHOWCASE:
                break;
            case ShowcaseView.LEFT_OF_SHOWCASE:
                break;
        }
        mText
    }

    private void addTextViewToShowCase(TextView tv, int textPosition, int left, int right) {

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

}
