/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.test.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import saulmm.test.R;

public class MediaFragment extends Fragment {

    private View mFab;
    private FrameLayout mFabContainer;
    private LinearLayout mControlsContainer;

    public final static float SCALE_FACTOR      = 13f;
    public final static int ANIMATION_DURATION  = 300;
    public final static int MINIMUN_X_DISTANCE  = 200;

    private boolean mRevealFlag;
    private float mFabSize;
    private View mRootView;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_media, container, false);
        return mRootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
        bindViews();
    }

    private void bindViews() {
        mFab = mRootView.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onFabPressed(v);
            }
        });

        mFabContainer = (FrameLayout) mRootView.findViewById(R.id.fab_container);
        mControlsContainer = (LinearLayout) mRootView.findViewById(R.id.media_controls_container);
    }

    public void onFabPressed(View view) {
        final float startX = mFab.getX();

        AnimatorPath path = new AnimatorPath();
        path.moveTo(0, 0);
        path.curveTo(-200, 200, -400, 100, -600, 50);

        final ObjectAnimator anim = ObjectAnimator.ofObject(this, "fabLoc",
            new PathEvaluator(), path.getPoints().toArray());

        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(ANIMATION_DURATION);
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (Math.abs(startX - mFab.getX()) > MINIMUN_X_DISTANCE) {

                    if (!mRevealFlag) {
                        mFabContainer.setY(mFabContainer.getY() + mFabSize / 2);

                        mFab.animate()
                            .scaleXBy(SCALE_FACTOR)
                            .scaleYBy(SCALE_FACTOR)
                            .setListener(mEndRevealListener)
                            .setDuration(ANIMATION_DURATION);

                        mRevealFlag = true;
                    }
                }
            }
        });
    }

    private AnimatorListenerAdapter mEndRevealListener = new AnimatorListenerAdapter() {

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


    /**
     * We need this setter to translate between the information the animator
     * produces (a new "PathPoint" describing the current animated location)
     * and the information that the button requires (an xy location). The
     * setter will be called by the ObjectAnimator given the 'fabLoc'
     * property string.
     */
    public void setFabLoc(PathPoint newLoc) {

        mFab.setTranslationX(newLoc.mX);


        if (mRevealFlag)
            mFab.setTranslationY(newLoc.mY - (mFabSize / 2));
        else
            mFab.setTranslationY(newLoc.mY);
    }

    public static MediaFragment newInstance () {
        return new MediaFragment();
    }
}
