package com.demonyga.pills;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddPillsActivity extends AppCompatActivity {
    private int numberOfTimesFields = 1;
    private final List<TextInputEditText> timeFieldsList = new ArrayList<>();
    private final List<FloatingActionButton> removeButtonsList = new ArrayList<>();
    private TextInputEditText lastAddedField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pills);
        lastAddedField = findViewById(R.id.howManyTimes);
        final FloatingActionButton addTimeButton = findViewById(R.id.addTime);
        final Button saveButton = findViewById(R.id.save_button);

        addTimeButton.setOnClickListener(v -> {
            if (numberOfTimesFields < 4) {
                numberOfTimesFields++;
                if (numberOfTimesFields >= 4) {
                    addTimeButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(AddPillsActivity.this, R.color.gray)));
                    addTimeButton.setClickable(false);
                }
                TextInputEditText newField = new TextInputEditText(AddPillsActivity.this);
                newField.setId(View.generateViewId());
                newField.setHint(getString(R.string.time_to_take));
                newField.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                newField.setMinHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
                ConstraintLayout.LayoutParams fieldParams = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );
                fieldParams.topToBottom = lastAddedField.getId();
                fieldParams.startToStart = lastAddedField.getId();
                fieldParams.endToEnd = lastAddedField.getId();
                fieldParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                newField.setLayoutParams(fieldParams);
                ((ConstraintLayout) findViewById(R.id.constraintLayout)).addView(newField);
                lastAddedField = newField;

                FloatingActionButton removeTimeButton = new FloatingActionButton(AddPillsActivity.this);
                removeTimeButton.setId(View.generateViewId());
                removeTimeButton.setContentDescription(getString(R.string.time_to_take));
                removeTimeButton.setClickable(true);
                removeTimeButton.setFocusable(true);
                removeTimeButton.setVisibility(View.VISIBLE);
                removeTimeButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(AddPillsActivity.this, R.color.red)));
                removeTimeButton.setCustomSize(64);
                ConstraintLayout.LayoutParams removeButtonParams = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );
                removeButtonParams.startToEnd = lastAddedField.getId();
                removeButtonParams.topToTop = lastAddedField.getId();
                removeButtonParams.bottomToBottom = lastAddedField.getId();
                removeTimeButton.setLayoutParams(removeButtonParams);
                removeTimeButton.setImageResource(R.drawable.baseline_remove_24);
                removeTimeButton.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(AddPillsActivity.this, R.color.white)));
                removeTimeButton.setOnClickListener(v1 -> {
                    if (numberOfTimesFields > 1) {
                        numberOfTimesFields--;
                        if(numberOfTimesFields < 4) {
                            addTimeButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(AddPillsActivity.this, R.color.main)));
                            addTimeButton.setClickable(true);
                        }
                        if (!timeFieldsList.isEmpty() && !removeButtonsList.isEmpty()) {
                            TextInputEditText removedField = timeFieldsList.remove(timeFieldsList.size() - 1);
                            ((ConstraintLayout) findViewById(R.id.constraintLayout)).removeView(removedField);
                            FloatingActionButton removedRemoveButton = removeButtonsList.remove(removeButtonsList.size() - 1);
                            ((ConstraintLayout) findViewById(R.id.constraintLayout)).removeView(removedRemoveButton);
                            if (!timeFieldsList.isEmpty()) {
                                lastAddedField = timeFieldsList.get(timeFieldsList.size() - 1);
                            } else {
                                lastAddedField = findViewById(R.id.howManyTimes);
                            }
                            ConstraintLayout.LayoutParams saveButtonParams = (ConstraintLayout.LayoutParams) saveButton.getLayoutParams();
                            saveButtonParams.topToBottom = lastAddedField.getId();
                            saveButton.setLayoutParams(saveButtonParams);
                        }
                    }
                });
                ((ConstraintLayout) findViewById(R.id.constraintLayout)).addView(removeTimeButton);
                timeFieldsList.add(newField);
                removeButtonsList.add(removeTimeButton);
                ConstraintLayout.LayoutParams saveButtonParams = (ConstraintLayout.LayoutParams) saveButton.getLayoutParams();
                saveButtonParams.topToBottom = lastAddedField.getId();
                saveButton.setLayoutParams(saveButtonParams);
            }
        });
    }
}
