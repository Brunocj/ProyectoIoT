package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appventure.R;

public class LogDetailFragmentSuperadmin extends Fragment {

    private LogEntrySuperadmin log;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Asegúrate que el XML se llame exactamente fragment_log_detail_superadmin.xml
        return inflater.inflate(R.layout.fragment_log_detail_superadmin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // Back
        View btnBack = v.findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(x -> requireActivity().onBackPressed());

        // Obtener el objeto
        Bundle args = getArguments();
        if (args != null) {
            Object obj = args.getSerializable("log");
            if (obj instanceof LogEntrySuperadmin) log = (LogEntrySuperadmin) obj;
        }

        // Referencias del layout (deben existir en tu XML)
        TextView tvDescripcion      = v.findViewById(R.id.tvDescripcion);
        TextView tvUsuario          = v.findViewById(R.id.tvUsuario);
        TextView tvRol              = v.findViewById(R.id.tvRol);
        TextView tvFecha            = v.findViewById(R.id.tvFecha);
        TextView tvHora             = v.findViewById(R.id.tvHora);
        TextView tvIp               = v.findViewById(R.id.tvIp);
        TextView tvEntidad          = v.findViewById(R.id.tvEntidad);
        TextView tvDescripcionLarga = v.findViewById(R.id.tvDescripcionLarga);

        // Pinta datos
        if (log != null) {
            setText(tvDescripcion,      nz(log.getTitulo()));
            setText(tvUsuario,          nz(log.getUsuario()));
            setText(tvRol,              nz(log.getRol()));
            setText(tvFecha,            nz(log.getFecha()));
            setText(tvHora,             nz(log.getHora()));
            setText(tvIp,               nz(log.getIpDispositivo()));
            setText(tvEntidad,          nz(log.getEntidad()));
            if (tvDescripcionLarga != null) {
                setText(tvDescripcionLarga, nz(log.getDescripcion()));
            }
        }
    }

    private static void setText(TextView tv, String value) {
        if (tv != null) tv.setText(value);
    }

    private static String nz(String s) {
        return TextUtils.isEmpty(s) ? "—" : s;
    }
}
