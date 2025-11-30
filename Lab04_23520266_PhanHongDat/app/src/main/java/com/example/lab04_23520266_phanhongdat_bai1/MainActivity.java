package com.example.lab04_23520266_phanhongdat_bai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Khai báo các View được dùng trong ứng dụng
    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    // Ánh xạ các view tới XML
    private void findViewsByIds() {
        ivUitLogo = (ImageView) findViewById(R.id.iv_uit_logo);
        btnFadeInXml = (Button) findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = (Button) findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = (Button) findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = (Button) findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = (Button) findViewById(R.id.btn_blink_xml);
        btnBlinkCode = (Button) findViewById(R.id.btn_blink_code);
        btnZoomInXml = (Button) findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = (Button) findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = (Button) findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = (Button) findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = (Button) findViewById(R.id.btn_rotate_xml);
        btnRotateCode = (Button) findViewById(R.id.btn_rotate_code);
        btnMoveXml = (Button) findViewById(R.id.btn_move_xml);
        btnMoveCode = (Button) findViewById(R.id.btn_move_code);
        btnSlideUpXml = (Button) findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = (Button) findViewById(R.id.btn_slide_up_code);
        btnBounceXml = (Button) findViewById(R.id.btn_bounce_xml);
        btnBounceCode = (Button) findViewById(R.id.btn_bounce_code);
        btnCombineXml = (Button) findViewById(R.id.btn_combine_xml);
        btnCombineCode = (Button) findViewById(R.id.btn_combine_code);
    }
    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }
    private void handleClickAnimationXml(Button btn, int animId)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, animId);
                ivUitLogo.startAnimation(animation);
            }
         });
    }

    // CODE
    // HandleClickAnimationCode
    private void handleClickAnimationCode(Button btn, final Animation animation) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    // Fade in
    private Animation initFaceInAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    // Fade out
    private Animation initFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }
    // Zoom in
    private Animation initZoomInAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                0.5f, 2.0f,
                0.5f, 2.0f,   // từ 50 → 200
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }
    // Zoom out
    private Animation initZoomOutAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 0.5f,
                1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    // Blink
    private Animation initBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);        // Khoảng tg các lần nháy
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    // Rotate
    private Animation initRotateAnimation() {
        RotateAnimation animation = new RotateAnimation(
                0, 360,  // xoay 360
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setRepeatCount(3);
        animation.setFillAfter(true);
        return animation;
    }
// Move
private Animation initMoveAnimation() {
    TranslateAnimation animation = new TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,  // giữa màn hình
            Animation.RELATIVE_TO_PARENT, 1.0f,   // vào qua bên phải màn hình
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
    );
    animation.setDuration(1000);
    animation.setFillAfter(true);
    return animation;
}

// Silde up
private Animation initSlideUpAnimation() {
    ScaleAnimation animation = new ScaleAnimation(
            1.0f, 1.0f,
            1.0f, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.0f
    );
    animation.setDuration(1000);
    animation.setFillAfter(true);
    return animation;
}


//Bounce
private Animation initBounceAnimation() {
    ScaleAnimation animation = new ScaleAnimation(
            1.0f, 1.0f,
            0.0f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
    );
    animation.setDuration(700);
    animation.setInterpolator(new android.view.animation.BounceInterpolator());
    //animation.setRepeatCount(1);
    animation.setRepeatMode(Animation.REVERSE);
    return animation;
}

//Combine
private Animation initCombineAnimation() {
    ScaleAnimation scale = new ScaleAnimation(
            1f, 3f, 1f, 3f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );
    scale.setDuration(3000);
    scale.setFillAfter(true);
    scale.setInterpolator(new LinearInterpolator());

    RotateAnimation rotate = new RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    );
    rotate.setDuration(500);
    rotate.setRepeatCount(3);
    rotate.setRepeatMode(Animation.RESTART);
    rotate.setInterpolator(new LinearInterpolator());

    AnimationSet combine = new AnimationSet(true);
    combine.addAnimation(scale);
    combine.addAnimation(rotate);
    combine.setFillAfter(false);
    combine.setAnimationListener(animationListener);
    return combine;
}
// Bai 2
private void handleClickImage(ImageView image,int aminIDIn,int aminIDOut) {
    image.setOnClickListener(v -> {
    Intent intent =new Intent(MainActivity.this, com.example.lab04_23520266_phanhongdat_bai1.NewActivity.class);
            startActivity(intent);
        overridePendingTransition(aminIDIn,aminIDOut);
    });
    }


    // Viết hàm từ chỗ này
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initVariables();
        // Handle khi nhấn vào animation XML
        // Tải anim
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);



        animation.setAnimationListener(animationListener);


        handleClickAnimationXml(btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml, R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXml(btnCombineXml, R.anim.anim_combine);


        // Xây dựng hàm animation bằng Code
        handleClickAnimationCode(btnFadeInCode, initFaceInAnimation());
        handleClickAnimationCode(btnFadeOutCode, initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode,initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode, initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode, initRotateAnimation());
        handleClickAnimationCode(btnMoveCode, initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, initBounceAnimation());
        handleClickAnimationCode(btnCombineCode, initCombineAnimation());

        // Xây dựng giao diện chuyển tiếp khi nhấn vào image
        handleClickImage(ivUitLogo,R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}