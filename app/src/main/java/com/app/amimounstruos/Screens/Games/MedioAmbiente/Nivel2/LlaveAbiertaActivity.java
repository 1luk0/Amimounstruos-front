package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.MapActivity;

public class LlaveAbiertaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_llave_abierta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton llaveButton = findViewById(R.id.llave);

      llaveButton.setOnClickListener(v -> {
        Intent intent = new Intent(LlaveAbiertaActivity.this, LlaveCerradaActivity.class);
        startActivity(intent);
      });
    }
}
