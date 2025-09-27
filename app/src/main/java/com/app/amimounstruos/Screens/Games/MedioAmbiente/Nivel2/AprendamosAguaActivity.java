package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.Components.DialogPopUpActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;

public class AprendamosAguaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aprendamos_agua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonSalir = findViewById(R.id.salirButton);
      ImageButton botonContinuar = findViewById(R.id.continueArrow);

      botonContinuar.setOnClickListener(v -> {
        Intent intent = new Intent(AprendamosAguaActivity.this, LlaveAbiertaActivity.class);
        startActivity(intent);
      });
      botonSalir.setOnClickListener(v -> {
        DialogPopUpActivity dialog = DialogPopUpActivity.newInstance(NivelesmaActivity.class);
        dialog.show(getSupportFragmentManager(), "confirmacion_salida_menu");
      });
    }
}
