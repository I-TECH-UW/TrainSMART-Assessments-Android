package com.itech.trainsmart.assessments.compat;

import android.os.Build;

/**
 * Currently, there's no {@link android.animation.ValueAnimator} compatibility version
 * and as we didn't want to throw in external dependencies, we made this small class.
 * <p/>
 * <p>
 * This will work like {@link android.support.v4.view.ViewPropertyAnimatorCompat}, that is,
 * not doing anything on API<11 and using the default {@link android.animation.ValueAnimator}
 * on API>=11
 * </p>
 * <p>
 * This class is used to provide animation to the {@link com.itech.trainsmart.assessments.DiscreteSeekBar}
 * when navigating with the Keypad
 * source: org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
 * </p>
 *
 * @hide
 */
public abstract class AnimatorCompat {
    public interface AnimationFrameUpdateListener {
        void onAnimationFrame(float currentValue);
    }

    AnimatorCompat() {

    }

    public abstract void cancel();

    public abstract boolean isRunning();

    public abstract void setDuration(int progressAnimationDuration);

    public abstract void start();

    public static final AnimatorCompat create(float start, float end, AnimationFrameUpdateListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new AnimatorCompatV11(start, end, listener);
        } else {
            return new AnimatorCompatBase(start, end, listener);
        }
    }

    private static class AnimatorCompatBase extends AnimatorCompat {

        private final AnimationFrameUpdateListener mListener;
        private final float mEndValue;

        public AnimatorCompatBase(float start, float end, AnimationFrameUpdateListener listener) {
            mListener = listener;
            mEndValue = end;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public void setDuration(int progressAnimationDuration) {

        }

        @Override
        public void start() {
            mListener.onAnimationFrame(mEndValue);
        }
    }
}

