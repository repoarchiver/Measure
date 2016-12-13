package abcmeasurecorp.com.measureit.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import abcmeasurecorp.com.measureit.R;
import abcmeasurecorp.com.measureit.view.RulerView;

/**
 * Created by Joel Anderson on 12/12/16.
 *
 * Main Activity class, whose layout contains the ruler view
 * and all additional interactive views.
 */

public class MainActivity extends AppCompatActivity {

    private boolean mPointerHidden = false;

    private Button mTogglePointerButton;
    private RulerView mRulerView;
    private LinearLayout mRightContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRulerView = (RulerView) findViewById(R.id.ruler);
        mRightContainer = (LinearLayout) findViewById(R.id.right_container);
        mTogglePointerButton = (Button) findViewById(R.id.toggle_pointer_button);
        Button randomColorButton = (Button) findViewById(R.id.random_color_button);

        mTogglePointerButton.setOnClickListener(clickTogglePointer());
        randomColorButton.setOnClickListener(clickRandomColor());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setImmersiveMode();
        }
    }

    /**
     * Uses custom properties and events of RulerView to toggle the visibility
     * of the ruler's pointer on button click
     *
     * @return OnClickListener to assign to the toggle button
     */
    private View.OnClickListener clickTogglePointer() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePointer();
            }
        };
    }

    /**
     * Toggles visibility of pointer, toggles text on pointer visibility button
     */
    private void togglePointer() {
        String label;
        if (mPointerHidden) {
            label = getString(R.string.button_hide_pointer);
        } else {
            label = getString(R.string.button_show_pointer);
        }
        mTogglePointerButton.setText(label);
        mRulerView.setShowPointer(mPointerHidden);
        mPointerHidden = !mPointerHidden;
    }

    /**
     * Uses custom properties and events of RulerView to change accent colors onscreen
     * to random color on button click.
     *
     * @return OnClickListener to assign to the toggle button
     */
    private View.OnClickListener clickRandomColor() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomColor();
            }
        };
    }

    /**
     * Creates random color, sets right container background and ruler accent colors
     */
    private void setRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        animateBackgroundColor(color);
        mRulerView.animateAccentColor(color);
    }

    /**
     * Changes the background color of the right container with animation
     *
     * @param color desired new background color
     */
    private void animateBackgroundColor(int color) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(mRightContainer, "backgroundColor",
                mRulerView.getAccentColor(), color);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(RulerView.ANIMATION_DURATION);
        colorAnim.start();

    }

    /**
     * Sets fullscreen mode, hides system bars
     */
    private void setImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}
