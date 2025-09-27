package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.MapActivity;

public class UserActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_user);

    // UI
    TextView nombreUsuario = findViewById(R.id.textView5);
    ImageView avatarView = findViewById(R.id.avatarView);

    // SharedPreferences
    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);

    // Cargar nombre
    String nombre = prefs.getString("nombre_usuario", "Invitado");
    nombreUsuario.setText(nombre);

    // Cargar avatar
    int personajeSeleccionado = prefs.getInt("personaje_seleccionado", 0);

    // Mapea personajeSeleccionado con el drawable correspondiente
    int avatarResId;
    switch (personajeSeleccionado) {
      case 1:
        avatarResId = R.drawable.monster1;
        break;
      case 2:
        avatarResId = R.drawable.monster2;
        break;
      case 3:
        avatarResId = R.drawable.monster3;
        break;
      case 4:
        avatarResId = R.drawable.monster4;
        break;
      default:
        avatarResId = R.drawable.monster1;
        break;
    }

    avatarView.setImageResource(avatarResId);

    // ---- tus botones ya existentes ----
    ImageButton botonConfiguracion = findViewById(R.id.configurations);
    ImageButton botonMapa = findViewById(R.id.salirButton);
    ImageButton botonAmigos = findViewById(R.id.amigosButton);

    botonConfiguracion.setOnClickListener(v -> {
      startActivity(new Intent(UserActivity.this, configuracionActivity.class));
    });

    botonMapa.setOnClickListener(v -> {
      startActivity(new Intent(UserActivity.this, MapActivity.class));
    });

    botonAmigos.setOnClickListener(v -> {
      startActivity(new Intent(UserActivity.this, AmigosActivity.class));
    });
  }

}
