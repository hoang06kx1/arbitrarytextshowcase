package com.github.amlcurran.showcaseview.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;

import com.espian.showcaseview.sample.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.sample.ui.view.ArbitraryTextWrapper;
import com.github.amlcurran.showcaseview.sample.ui.view.ArbitraryViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class CustomTextActivity extends Activity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_text);
        View img = findViewById(R.id.imageView);
        int id = View.generateViewId();
        // img.setId(id);
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(getResources().getDimension(R.dimen.abc_text_size_body_1_material));
        paint.setStrikeThruText(true);
        paint.setColor(Color.RED);
        paint.setTypeface(Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf"));

        TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        title.setTextSize(getResources().getDimension(R.dimen.abc_text_size_headline_material));
        title.setUnderlineText(true);
        title.setColor(Color.YELLOW);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf"));

        final ViewTarget vt = new ViewTarget(R.id.imageView, this);
        final ShowcaseView showcaseView = new ShowcaseView.Builder(this)
                .withNewStyleShowcase()
                .setTarget(vt)
                // .setContentTextPaint(paint)
                // .setContentTitle(R.string.custom_text_painting_title)
                // .setContentText(R.string.custom_text_painting_text)
                // .setContentTitlePaint(title)
                .build();

        ArbitraryViewTarget avt = new ArbitraryViewTarget(R.id.imageView, this);
        new ArbitraryTextWrapper(this, showcaseView)
                .forceTextPosition(ShowcaseView.RIGHT_OF_SHOWCASE)
                .setArbitraryTarget(avt)
                .setArbitraryContentText("a a a a a a a a a  a a a a a a a a a  a a a a a a a a a a a a a a", 50)
                .buildArbitraryText();
        new ArbitraryTextWrapper(this, showcaseView)
                .forceTextPosition(ShowcaseView.LEFT_OF_SHOWCASE)
                .setArbitraryTarget(avt)
                .setArbitraryContentText("a a a a a a a a a  a a a a a a a a a  a a a a a a a a a a a a a a", 50)
                .buildArbitraryText();
        new ArbitraryTextWrapper(this, showcaseView)
                .forceTextPosition(ShowcaseView.ABOVE_SHOWCASE)
                .setArbitraryTarget(avt)
                .setArbitraryContentText("a a a a a a a a a  a a a a a a a a a  a a a a a a a a a a a a a a", 50)
                .buildArbitraryText();
        new ArbitraryTextWrapper(this, showcaseView)
                .forceTextPosition(ShowcaseView.BELOW_SHOWCASE)
                .setArbitraryTarget(avt)
                .setArbitraryContentText("a a a a a a a a a  a a a a a a a a a  a a a a a a a a a a a a a a", 50)
                .buildArbitraryText();

        // showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }
}
