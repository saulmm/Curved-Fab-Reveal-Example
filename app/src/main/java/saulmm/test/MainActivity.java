package saulmm.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;


public class MainActivity extends Activity {

    private View mFab;
    private FrameLayout mFabContainer;

    public final static float SCALE_FACTOR      = 13f;
    public final static int ANIMATION_DURATION  = 600;

    public float mFabSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.fab);
        mFabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
        mFabContainer = (FrameLayout) findViewById(R.id.fab_container);
    }

    public void onFabPressed(View view) {

        float endX = ((mFabContainer.getWidth()) / 2) * -1;
        float endY = (((mFabContainer.getHeight()) / 2) + mFabSize);

        ViewPropertyAnimator animator = mFab.animate()
            .translationX(endX)
            .translationY(endY)
            .setDuration(ANIMATION_DURATION);

        animator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            boolean flag = false;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Log.d("[DEBUG]", "MainActivity onAnimationUpdate - " +
                    "Hi");
                
                if (mFab.getY() > 150) {

                    if (!flag) {

                        mFabContainer.setY(mFabContainer.getY() + mFabSize/2);

                        mFab.animate()
                            .scaleXBy(SCALE_FACTOR)
                            .scaleYBy(SCALE_FACTOR)
                            .setDuration(ANIMATION_DURATION);

                        flag = true;

                    } else {

                        mFab.setY(mFab.getY() - mFabSize/2 + 30);
                    }
                }
            }
        });

        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                mFab.setVisibility(View.INVISIBLE);
                mFabContainer.setBackgroundColor(Color.YELLOW);
            }
        });
    }
}
