package com.github.amlcurran.showcaseview.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.espian.showcaseview.sample.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
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
                .setContentTextPaint(paint)
                // .setContentTitle(R.string.custom_text_painting_title)
                // .setContentText(R.string.custom_text_painting_text)
                // .setContentTitlePaint(title)
                .build();

        final TextView tv = new TextView(this);
        showcaseView.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText("lorem ipsum lorem ipsum lorem");
                tv.setTextColor(Color.GREEN);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                lp.setMargins(vt.getPoint().x, vt.getPoint().y, 0, 0);
                tv.setLayoutParams(lp);
                showcaseView.addView(tv);
            }
        },100);



        // showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }
}
