package com.github.amlcurran.showcaseview.sample.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;

/**
 * Created by Hoang on 7/4/2016.
 */
public class ArbitraryTextWrapper {
    private Point mTargetPosition;
    private int[] mTargetDimension;
    private int mTextMargin = 0;
    private CharSequence mArbitraryText;
    private final Context mContext;
    private final ShowcaseView mShowcaseView;
    private int mTextPosition = ShowcaseView.ABOVE_SHOWCASE;
    private ArbitraryTarget mTarget;
    private final int DEFAULT_MARGIN = 0;

    public ArbitraryTextWrapper(Context context, ShowcaseView showcaseView) {
        mContext = context;
        mShowcaseView = showcaseView;
    }


    public ArbitraryTextWrapper forceTextPosition(int textPosition) {
        if (textPosition > ShowcaseView.BELOW_SHOWCASE || textPosition < ShowcaseView.UNDEFINED) {
            throw new IllegalArgumentException("ShowcaseView text was forced with an invalid position");
        }
        mTextPosition = textPosition;
        return this;
    }

    public ArbitraryTextWrapper setArbitraryTarget(final ArbitraryTarget target) {
        mTarget = target;
        return this;
    }

    public ArbitraryTextWrapper buildArbitraryText() {
        mShowcaseView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTargetDimension = mTarget.getDimension();
                mTargetPosition = mTarget.getPoint();

                if (mArbitraryText == null || mTargetPosition == null || mTargetDimension == null) {
                    throw new IllegalStateException("The target view is not set!");
                }

                int[] showcaselocation = new int[2];
                mShowcaseView.getLocationInWindow(showcaselocation);
                int textHeight, textWidth, textMarginTop, textMarginLeft;

                switch (mTextPosition) {
                    case ShowcaseView.ABOVE_SHOWCASE:
                        textHeight = getTextHeight(getWindowsSize(mContext)[0]);
                        textMarginTop = mTargetPosition.y - (mTargetDimension[1]/2) - showcaselocation[1] - textHeight - mTextMargin;
                        addTextViewToShowcase(0, textMarginTop, 0, 0);
                        break;

                    case ShowcaseView.BELOW_SHOWCASE:
                        textMarginTop = mTargetPosition.y + (mTargetDimension[1]/2) - showcaselocation[1] + mTextMargin;
                        addTextViewToShowcase(0, textMarginTop, 0, 0);
                        break;

                    case ShowcaseView.LEFT_OF_SHOWCASE:
                        int renderWidth = getTextWidth();
                        int remainWidth = mTargetPosition.x - (mTargetDimension[0]/2) - mTextMargin - DEFAULT_MARGIN;
                        textWidth = Math.min(renderWidth, remainWidth);
                        textHeight = getTextHeight(textWidth);
                        textMarginTop = mTargetPosition.y - showcaselocation[1] - textHeight/2;
                        if (renderWidth < remainWidth) {
                            textMarginLeft = mTargetPosition.x - (mTargetDimension[0] / 2) - mTextMargin - renderWidth;
                        } else {
                            textMarginLeft = DEFAULT_MARGIN;
                        }
                        addTextViewToShowcase(textMarginLeft, textMarginTop, 0, 0, textWidth);
                        break;

                    case ShowcaseView.RIGHT_OF_SHOWCASE:
                        textMarginLeft = mTargetPosition.x + mTargetDimension[0]/2 + mTextMargin ;
                        textWidth = getWindowsSize(mContext)[0] - textMarginLeft - DEFAULT_MARGIN;
                        textHeight = getTextHeight(textWidth);
                        textMarginTop = mTargetPosition.y - showcaselocation[1] - textHeight/2;
                        addTextViewToShowcase(textMarginLeft, textMarginTop, 0, 0, textWidth);
                        break;

                    case ShowcaseView.UNDEFINED:
                        break;
                }
            }
        }, 0);
        return this;
    }

    protected void addTextViewToShowcase(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        addTextViewToShowcase(marginLeft, marginTop, marginRight, marginBottom, 0);
    }

    protected void addTextViewToShowcase(int marginLeft, int marginTop, int marginRight, int marginBottom, int maxWidth) {
        maxWidth = (maxWidth != 0? maxWidth: RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(maxWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        if (mTextPosition == ShowcaseView.ABOVE_SHOWCASE || mTextPosition == ShowcaseView.BELOW_SHOWCASE) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
        TextView tv = getTextView(mArbitraryText.toString());
        tv.setLayoutParams(lp);
        mShowcaseView.addView(tv);
    }

    public ArbitraryTextWrapper setArbitraryContentText(CharSequence charSequence, int marginFromTarget) {
        mShowcaseView.setContentTitle("");
        mShowcaseView.setContentText("");
        if (charSequence != null) {
            mArbitraryText = charSequence.toString();
        }
        mTextMargin = dpToPx(marginFromTarget);
        return this;
    }

    protected TextView getTextView(CharSequence content) {
        TextView tv = new TextView(mContext);
        tv.setText(content);
        return tv;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    protected int getTextHeight(int limitedWidth) {
        TextView textView = new TextView(mContext);
        // textView.setTypeface(typeface);
        textView.setText(mArbitraryText, TextView.BufferType.SPANNABLE);
        // textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(limitedWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    protected int getTextWidth() {
        TextView tv = getTextView(mArbitraryText);
        tv.measure(0,0);
        return tv.getMeasuredWidth();
    }

    private int[] getWindowsSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int[] size = new int[2];
        size[0] = display.getWidth();
        size[1] = display.getHeight();
        return size;
    }
}
