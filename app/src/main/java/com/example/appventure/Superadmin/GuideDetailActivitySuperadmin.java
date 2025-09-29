package com.example.appventure.Superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appventure.R;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

public class GuideDetailActivitySuperadmin extends AppCompatActivity {

    private TextView textViewGuideNameHeader;
    private TextView textViewDescription;
    private TextView textViewAge;
    private TextView textViewDni;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewLanguages;
    private TextView textViewLocation;
    private ViewPager2 viewPagerPhotos;
    private LinearLayout layoutPageIndicators;
    private LinearLayout layoutActionButtons;
    private ImageView buttonApprove;
    private ImageView buttonReject;
    private ImageButton buttonBack;

    private String guideName;
    private String guideStatus;
    private List<String> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail_superadmin);

        initViews();
        loadGuideData();
        setupPhotoCarousel();
        setupButtons();
    }

    private void initViews() {
        textViewGuideNameHeader = findViewById(R.id.textViewGuideNameHeader);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewAge = findViewById(R.id.textViewAge);
        textViewDni = findViewById(R.id.textViewDni);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewLanguages = findViewById(R.id.textViewLanguages);
        textViewLocation = findViewById(R.id.textViewLocation);
        viewPagerPhotos = findViewById(R.id.viewPagerPhotos);
        layoutPageIndicators = findViewById(R.id.layoutPageIndicators);
        layoutActionButtons = findViewById(R.id.layoutActionButtons);
        buttonApprove = findViewById(R.id.buttonApprove);
        buttonReject = findViewById(R.id.buttonReject);
        buttonBack = findViewById(R.id.buttonBack);
    }

    private void loadGuideData() {
        Intent intent = getIntent();
        
        guideName = intent.getStringExtra("guide_name");
        String description = intent.getStringExtra("guide_description");
        String age = intent.getStringExtra("guide_age");
        String dni = intent.getStringExtra("guide_dni");
        String email = intent.getStringExtra("guide_email");
        String phone = intent.getStringExtra("guide_phone");
        String languages = intent.getStringExtra("guide_languages");
        String location = intent.getStringExtra("guide_location");
        guideStatus = intent.getStringExtra("guide_status");
        String photosString = intent.getStringExtra("guide_photos");

        // Establecer textos
        textViewGuideNameHeader.setText(guideName);
        textViewDescription.setText(description);
        textViewAge.setText(age);
        textViewDni.setText(dni);
        textViewEmail.setText(email);
        textViewPhone.setText(phone);
        textViewLanguages.setText(languages);
        textViewLocation.setText(location);

        // Procesar fotos
        if (photosString != null && !photosString.isEmpty()) {
            photos = Arrays.asList(photosString.split(","));
        } else {
            photos = Arrays.asList("default_photo.jpg"); // Foto por defecto
        }

        // Configurar botones según el estado actual
        layoutActionButtons.setVisibility(View.VISIBLE);
        
        if ("Pendiente".equalsIgnoreCase(guideStatus)) {
            // Pendiente: mostrar ambos botones
            buttonApprove.setVisibility(View.VISIBLE);
            buttonReject.setVisibility(View.VISIBLE);
        } else if ("Aprobado".equalsIgnoreCase(guideStatus)) {
            // Aprobado: solo mostrar rechazar (para desaprobar)
            buttonApprove.setVisibility(View.GONE);
            buttonReject.setVisibility(View.VISIBLE);
        } else if ("Rechazado".equalsIgnoreCase(guideStatus)) {
            // Rechazado: solo mostrar aprobar
            buttonApprove.setVisibility(View.VISIBLE);
            buttonReject.setVisibility(View.GONE);
        } else {
            // Estado desconocido: ocultar botones
            layoutActionButtons.setVisibility(View.GONE);
        }
    }

    private void setupPhotoCarousel() {
        PhotoCarouselAdapterSuperadmin photoAdapter = new PhotoCarouselAdapterSuperadmin(photos);
        viewPagerPhotos.setAdapter(photoAdapter);
        
        if (photos.isEmpty()) {
            viewPagerPhotos.setVisibility(View.GONE);
            layoutPageIndicators.setVisibility(View.GONE);
        } else {
            setupPageIndicators();
        }
    }

    private void setupPageIndicators() {
        layoutPageIndicators.removeAllViews();
        
        // Crear indicadores para cada foto
        for (int i = 0; i < photos.size(); i++) {
            View indicator = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24, 24);
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.indicator_inactive);
            layoutPageIndicators.addView(indicator);
        }
        
        // Seleccionar el primer indicador
        if (layoutPageIndicators.getChildCount() > 0) {
            layoutPageIndicators.getChildAt(0).setBackgroundResource(R.drawable.indicator_active);
        }
        
        // Configurar listener para cambio de página
        viewPagerPhotos.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updatePageIndicators(position);
            }
        });
    }
    
    private void updatePageIndicators(int position) {
        for (int i = 0; i < layoutPageIndicators.getChildCount(); i++) {
            View indicator = layoutPageIndicators.getChildAt(i);
            if (i == position) {
                indicator.setBackgroundResource(R.drawable.indicator_active);
            } else {
                indicator.setBackgroundResource(R.drawable.indicator_inactive);
            }
        }
    }

    private void setupButtons() {
        buttonBack.setOnClickListener(v -> finish());

        buttonApprove.setOnClickListener(v -> showApprovalDialog());

        buttonReject.setOnClickListener(v -> showRejectionDialog());
    }

    private void showApprovalDialog() {
        String message;
        if ("Rechazado".equalsIgnoreCase(guideStatus)) {
            message = "¿Estás seguro de que deseas aprobar a " + guideName + " como guía?";
        } else {
            message = "¿Estás seguro de que deseas aprobar a " + guideName + " como guía?";
        }
        
        new AlertDialog.Builder(this)
                .setTitle("Aprobar Guía")
                .setMessage(message)
                .setPositiveButton("Aprobar", (dialog, which) -> {
                    approveGuide();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showRejectionDialog() {
        String message;
        if ("Aprobado".equalsIgnoreCase(guideStatus)) {
            message = "¿Estás seguro de que deseas desaprobar a " + guideName + "?";
        } else {
            message = "¿Estás seguro de que deseas rechazar la solicitud de " + guideName + "?";
        }
        
        new AlertDialog.Builder(this)
                .setTitle(("Aprobado".equalsIgnoreCase(guideStatus)) ? "Desaprobar Guía" : "Rechazar Guía")
                .setMessage(message)
                .setPositiveButton(("Aprobado".equalsIgnoreCase(guideStatus)) ? "Desaprobar" : "Rechazar", (dialog, which) -> {
                    rejectGuide();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void approveGuide() {
        guideStatus = "Aprobado";
        
        // Actualizar visibilidad de botones
        buttonApprove.setVisibility(View.GONE);
        buttonReject.setVisibility(View.VISIBLE);
        
        // Enviar resultado de vuelta
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_guide_name", guideName);
        resultIntent.putExtra("updated_guide_status", guideStatus);
        setResult(RESULT_OK, resultIntent);
        
        // TODO: Aquí podrías mostrar un mensaje de éxito
        finish();
    }

    private void rejectGuide() {
        guideStatus = "Rechazado";
        
        // Actualizar visibilidad de botones
        buttonApprove.setVisibility(View.VISIBLE);
        buttonReject.setVisibility(View.GONE);
        
        // Enviar resultado de vuelta
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_guide_name", guideName);
        resultIntent.putExtra("updated_guide_status", guideStatus);
        setResult(RESULT_OK, resultIntent);
        
        // TODO: Aquí podrías mostrar un mensaje de éxito
        finish();
    }
}