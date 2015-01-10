package br.com.newagemobile.revealtextview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by rafaelneiva on 10/01/15.
 */
public class RevealTextView extends TextView {
    private String mTextString;
    private SpannableString mSpannableString;

    private double[] mAlphas;
    private boolean mIsVisible;
    private boolean mIsTextResetting = false;
    private int mDuration = 1500;

    ValueAnimator animator;
    ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float percent = (Float) valueAnimator.getAnimatedValue();
            resetSpannableString(mIsVisible ? percent : 2.0f - percent);
        }
    };

    public RevealTextView(Context context) {
        super(context);
        init();
    }

    public RevealTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mIsVisible = false;
        animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.addUpdateListener(listener);
        animator.setDuration(mDuration);
    }

    public void toggle() {
        if (mIsVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        mIsVisible = true;
        animator.start();
    }

    public void hide() {
        mIsVisible = false;
        animator.start();
    }

    public void setIsVisible(boolean isVisible) {
        mIsVisible = isVisible;
        resetSpannableString(isVisible == true ? 2.0f : 0.0f);
    }

    public boolean getIsVisible() {
        return mIsVisible;
    }

    private void resetSpannableString(double percent) {
        mIsTextResetting = true;

        int color = getCurrentTextColor();
        for (int i = 0; i < mSpannableString.length(); i++) {
            mSpannableString.setSpan(
                    new ForegroundColorSpan(Color.argb(clamp(mAlphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color))), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(mSpannableString);
        invalidate();

        mIsTextResetting = false;
    }

    private void resetAlphas(int length) {
        mAlphas = new double[length];

        for (int i = 0; i <= (length / 2); i ++) {
            int index_a = (length / 2) + i; // index of letters after mid letter
            int index_b = (length / 2) - i; // index of letters before mid letter
            mAlphas[index_a] = (1 - (float) i/10) - 1;
            mAlphas[index_b] = (1 - (float) i/10) - 1;
        }

//        for (int i = 0; i < mAlphas.length; i++) {
////            mAlphas[i] = Math.random() - 1; // on random the letters appears random :o
////            mAlphas[i] = i - 1;
//            mAlphas[0] = -1;
//            mAlphas[1] = -0.9;
//            mAlphas[2] = -0.8;
//            mAlphas[3] = -0.7;
//            mAlphas[4] = -0.6;
//            mAlphas[5] = -0.5;
//            mAlphas[6] = -0.4;
//            mAlphas[7] = -0.3;
//            mAlphas[8] = -0.4;
//            mAlphas[9] = -0.5;
//            mAlphas[10] = -0.6;
//            mAlphas[11] = -0.7;
//            mAlphas[12] = -0.8;
//            mAlphas[13] = -0.9;
//            mAlphas[14] = -1;
//        }
    }

    private void resetIfNeeded() {
        if (!mIsTextResetting) {
            mTextString = getText().toString();
            mSpannableString = new SpannableString(this.mTextString);
            resetAlphas(mTextString.length());
            resetSpannableString(mIsVisible ? 2.0f : 0);
        }
    }

    public void setText(String text) {
        super.setText(text);
        resetIfNeeded();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        resetIfNeeded();
    }

    private int clamp(double f) {
        return (int) (255 * Math.min(Math.max(f, 0), 1));
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
        animator.setDuration(mDuration);
    }
}
