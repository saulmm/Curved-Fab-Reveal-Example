package saulmm.test;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;


public class MainActivity extends Activity {

    private View mFab;

    public final static float SCALE_FACTOR = 13f;
    public final static int FINAL_POSITION_Y = 800;
    public final static int FINAL_POSITION_X = 400;
    public final static int ANIMATION_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
    }

    public void onFabPressed(View view) {

        ViewPropertyAnimator animator = mFab.animate()
            .translationX(FINAL_POSITION_X)
            .translationY(FINAL_POSITION_Y)
            .setDuration(ANIMATION_DURATION);


        animator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            boolean flag = false;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (mFab.getX() > 200) {

                    if (!flag) {

                        ViewPropertyAnimator scaleAnimation = mFab.animate()
                            .scaleXBy(SCALE_FACTOR)
                            .scaleYBy(SCALE_FACTOR)
                            .setDuration(ANIMATION_DURATION);

                        flag = true;
                    }
                }
            }
        });
    }
}
