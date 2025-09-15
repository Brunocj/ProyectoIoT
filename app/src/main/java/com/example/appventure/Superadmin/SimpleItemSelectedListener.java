package com.example.appventure.Superadmin;

import android.view.View;
import android.widget.AdapterView;

public class SimpleItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private final Runnable onChange;

    public SimpleItemSelectedListener(Runnable onChange) {
        this.onChange = onChange;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onChange.run();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no-op
    }
}
