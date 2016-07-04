package com.github.amlcurran.showcaseview.sample.ui.view;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

import com.github.amlcurran.showcaseview.targets.ViewTarget;

/**
 * Created by hoang_000 on 7/4/2016.
 */
public class ArbitraryViewTarget extends ViewTarget implements ArbitraryTarget {
    private final View mView;

    public ArbitraryViewTarget(View view) {
        super(view);
        mView = view;
    }

    public ArbitraryViewTarget(int viewId, Activity activity) {
        super(viewId, activity);
        mView = activity.findViewById(viewId);
    }

    @Override
    public Point getPoint() {
        return super.getPoint();
    }

    @Override
    public int[] getDimension() {
        int[] dimension = new int[2];
        dimension[0] = mView.getWidth();
        dimension[1] = mView.getHeight();
        return dimension;
    }
}
