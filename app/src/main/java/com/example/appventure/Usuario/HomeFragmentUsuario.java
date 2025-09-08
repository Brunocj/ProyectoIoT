package com.example.appventure.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appventure.BlankActivity;
import com.example.appventure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragmentUsuario extends Fragment {

    public HomeFragmentUsuario() { /* Required empty public constructor */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_home, container, false);
    }
}