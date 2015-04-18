package saulmm.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    private View mFab;
    private FrameLayout mFabContainer;

    public final static float SCALE_FACTOR      = 13f;
    public final static int ANIMATION_DURATION  = 600;
    public final static int MINIMUN_X_DISTANCE  = 200;

    public float mFabSize;
    private LinearLayout mControlsContainer;
    private boolean flag;
    private AnimatorListenerAdapter         endRevealListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {

            super.onAnimationEnd(animation);

            mFab.setVisibility(View.INVISIBLE);
            mFabContainer.setBackgroundColor(getResources()
                .getColor(R.color.brand_accent));

            for (int i = 0; i < mControlsContainer.getChildCount(); i++) {

                View v = mControlsContainer.getChildAt(i);
                ViewPropertyAnimator animator = v.animate()
                    .scaleX(1).scaleY(1)
                    .setDuration(ANIMATION_DURATION);
                animator.setStartDelay(i * 50);
                animator.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mFabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);

        mFabContainer = (FrameLayout) findViewById(R.id.fab_container);
        mControlsContainer = (LinearLayout) findViewById(R.id.media_controls_container);
    }

    public void onFabPressed(View view) {

        final float startX = mFab.getX();

        AnimatorPath path = new AnimatorPath();
        path.moveTo(0, 0);
        path.curveTo(-200, 200, -400, 100, -600, 50);

        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "buttonLoc",
            new PathEvaluator(), path.getPoints().toArray());

        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                if (Math.abs(startX - mFab.getX()) > MINIMUN_X_DISTANCE) {

                    if (!flag) {

                        mFabContainer.setY(mFabContainer.getY() + mFabSize / 2);

                        mFab.animate()
                            .setListener(endRevealListener)
                            .scaleXBy(SCALE_FACTOR)
                            .scaleYBy(SCALE_FACTOR)
                            .setDuration(ANIMATION_DURATION);

                        flag = true;

                    }
                }
            }
        });




    }

    public void restart(View view) {

        startActivity(new Intent(MainActivity.this, MainActivity.class)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    /**
     * We need this setter to translate between the information the animator
     * produces (a new "PathPoint" describing the current animated location)
     * and the information that the button requires (an xy location). The
     * setter will be called by the ObjectAnimator given the 'buttonLoc'
     * property string.
     */
    public void setButtonLoc(PathPoint newLoc) {

        Log.d("[DEBUG]", "MainActivity setButtonLoc - " +
            "newLoc.mY " + newLoc.mY);

        mFab.setTranslationX(newLoc.mX);


        if (flag)
            mFab.setTranslationY(newLoc.mY - (mFabSize / 2));
        else
            mFab.setTranslationY(newLoc.mY);


    }
}
