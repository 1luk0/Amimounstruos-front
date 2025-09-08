package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel4.Banera1Activity;

public class ReparadorAguaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reparador_agua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton mapButton = findViewById(R.id.mapButton);

      mapButton.setOnClickListener(v -> {
        Intent intent = new Intent(ReparadorAguaActivity.this, Banera1Activity.class);
        startActivity(intent);
      });

      ImageButton botonContinuar = findViewById(R.id.botonContinuar);

      botonContinuar.setOnClickListener(v -> {
        Intent intent = new Intent(ReparadorAguaActivity.this, Banera1Activity.class);
        startActivity(intent);
      });
    }
}
