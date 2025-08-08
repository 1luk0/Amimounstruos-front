package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1.HistoriaAgua6Activity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1.HistoriaAgua7Activity;

public class AprendisteCerrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aprendiste_cerrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonContinue = findViewById(R.id.botonContinuar);

      botonContinue.setOnClickListener(v -> {
        Intent intent = new Intent(AprendisteCerrarActivity.this, TuberiaActivity.class);
        startActivity(intent);
      });
    }
}
