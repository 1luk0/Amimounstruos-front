package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2.PequenoHeroeActivity;
import com.app.amimounstruos.Screens.MapActivity;

public class Banera5Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banera5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton toallaButton = findViewById(R.id.toallaButton);
      ImageButton botonMapa = findViewById(R.id.mapButton);

      toallaButton.setOnClickListener(v -> {
        Intent intent = new Intent(Banera5Activity.this, Banera6Activity.class);
        startActivity(intent);
      });

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(Banera5Activity.this, MapActivity.class);
        startActivity(intent);
      });

    }
}
