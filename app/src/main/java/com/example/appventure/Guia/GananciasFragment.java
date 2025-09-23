package com.example.appventure.Guia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;
import com.google.android.material.appbar.MaterialToolbar;

public class GananciasFragment extends Fragment {

    public GananciasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ganancias, container, false);

        // Flecha atrás
        MaterialToolbar toolbar = view.findViewById(R.id.toolbarGanancias);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
