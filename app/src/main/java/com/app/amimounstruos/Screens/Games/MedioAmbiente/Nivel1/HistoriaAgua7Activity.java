package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Services.ProgresoService;
import com.google.gson.Gson;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class HistoriaAgua7Activity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_historia_agua7);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ImageButton botonContinue = findViewById(R.id.botonContinuar);
    ImageButton botonNiveles = findViewById(R.id.configurationsButton);

    botonContinue.setOnClickListener(v -> {
      // Recuperar el usuarioId guardado
      SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
      int usuarioId = prefs.getInt("user_id", -1);

      // Crear el progreso
      Progreso progreso = new Progreso(usuarioId, 22, 1, "en curso");

      // Retrofit
      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL) // Reemplaza con tu endpoint real
        .addConverterFactory(GsonConverterFactory.create())
        .build();

      ProgresoService service = retrofit.create(ProgresoService.class);

      Call<Void> call = service.registrarProgreso(progreso);
      Log.d("API", "JSON enviado: " + new Gson().toJson(progreso));

      call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
          if (response.isSuccessful()) {
            Log.d("API", "Progreso creado con éxito");
          } else {
            Log.e("API", "Error en la respuesta: " + response.code() + " - " + response.message());
          }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
          Log.e("API", "Fallo en la petición: " + t.getMessage(), t);
        }

      }

      );
      Intent intent = new Intent(HistoriaAgua7Activity.this, NivelesmaActivity.class);
      startActivity(intent);
    }); // <-- Cierro el listener de botonContinue

    botonNiveles.setOnClickListener(v -> {
      Intent intent = new Intent(HistoriaAgua7Activity.this, NivelesmaActivity.class);
      startActivity(intent);
    });
  }
}
