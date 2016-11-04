package com.prashant.login.loginmodule;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenActivity extends Activity {

    @BindView(R.id.txtWelcome)
    TextView txtWelcome;
    @BindView(R.id.imgView)
    ImageView imgView;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        context = this;
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String usrName = bundle.getString(Constants.USER_NAME);

        SpannableString spannableContent=new SpannableString("Welcome, "+usrName);
        spannableContent.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                9,spannableContent.length(), 0);

        //txtWelcome.setText("Welcome, "+usrName);
        txtWelcome.setText(spannableContent);

//I have used picasso library because it handles image download and cacheing etc.
        Picasso.with(this)
                .load(Constants.URL)
                .resize(250, 200)
                .into(imgView);

        Animation anim = new ScaleAnimation(
                0.95f, 1f, // Start and end values for the X axis scaling
                0.95f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        anim.setRepeatMode(Animation.INFINITE);
        anim.setRepeatCount(Animation.INFINITE);
        imgView.startAnimation(anim);

    }


}
