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

public class PreguntaAnimalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pregunta_animales);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonCorrecto = findViewById(R.id.opcion2);

      botonCorrecto.setOnClickListener(v -> {
        Intent intent = new Intent(PreguntaAnimalesActivity.this, CorrectoAnimalesActivity.class);
        startActivity(intent);
      });

      ImageButton botonInCorrecto = findViewById(R.id.opcion1);

      botonInCorrecto.setOnClickListener(v -> {
        Intent intent = new Intent(PreguntaAnimalesActivity.this, IncorrectoAnimalesActivity.class);
        startActivity(intent);
      });
    }
}
