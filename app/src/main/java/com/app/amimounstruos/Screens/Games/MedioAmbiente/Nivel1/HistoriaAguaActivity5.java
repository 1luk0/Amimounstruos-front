package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.MapActivity;

public class HistoriaAguaActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historia_agua5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonContinue = findViewById(R.id.botonContinuar);
      ImageButton botonMapa = findViewById(R.id.mapButton);

      botonContinue.setOnClickListener(v -> {
        Intent intent = new Intent(HistoriaAguaActivity5.this, PlantasActivity.class);
        startActivity(intent);
      });

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(HistoriaAguaActivity5.this, MapActivity.class);
        startActivity(intent);
      });
    }
}
