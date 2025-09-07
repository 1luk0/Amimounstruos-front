package com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Models.Nivel;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1.HistoriaAguaActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;
import com.app.amimounstruos.Services.NivelService;
import com.app.amimounstruos.Services.ProgresoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NivelesmaActivity extends BaseActivity {
    private static final int CURSO_ID = 21; // ← Cambia esto si tu curso tiene otro ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivelesma); // ← Usa el nombre de tu layout XML real

        SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        if (userId == 0) {
            Toast.makeText(this, "ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_URL) // ← Cambia esto por tu IP real si es dispositivo físico
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProgresoService progresoService = retrofit.create(ProgresoService.class);
        NivelService nivelService = retrofit.create(NivelService.class);

        progresoService.getProgresoByUsuarioYCurso(userId, CURSO_ID).enqueue(new Callback<Progreso>() {
          @Override
          public void onResponse(Call<Progreso> call, Response<Progreso> response) {
            Log.d("PROGRESO_RESPONSE_RAW", response.raw().toString());
            Log.d("PROGRESO_RESPONSE_CODE", String.valueOf(response.code()));

            if (response.isSuccessful() && response.body() != null) {
              Log.d("PROGRESO_RESPONSE_BODY", response.body().toString());

              int nivelId = response.body().getNivelId().intValue();

              nivelService.getNivelById(nivelId).enqueue(new Callback<Nivel>() {
                @Override
                public void onResponse(Call<Nivel> call, Response<Nivel> response) {
                  Log.d("NIVEL_RESPONSE_RAW", response.raw().toString());
                  Log.d("NIVEL_RESPONSE_CODE", String.valueOf(response.code()));

                  if (response.isSuccessful() && response.body() != null) {
                    Log.d("NIVEL_RESPONSE_BODY", response.body().toString());

                    int numero = response.body().getNumero().intValue();
                    deshabilitarTodosMenos(numero + 1);
                  } else {
                    Log.e("NIVEL_RESPONSE", "Error en body o código no exitoso");
                    deshabilitarTodosMenos(1);
                  }
                }

                @Override
                public void onFailure(Call<Nivel> call, Throwable t) {
                  Log.e("NIVEL_FAILURE", "Error en la llamada: " + t.getMessage());
                  deshabilitarTodosMenos(1);
                }
              });

            } else {
              Log.e("PROGRESO_RESPONSE", "Error en body o código no exitoso");
              deshabilitarTodosMenos(1);
            }
          }

          @Override
          public void onFailure(Call<Progreso> call, Throwable t) {
            Log.e("PROGRESO_FAILURE", "Error en la llamada: " + t.getMessage());
            deshabilitarTodosMenos(1);
          }
        });

      ImageButton botonMapa = findViewById(R.id.mapButton);

      ImageButton botonPerfil = findViewById(R.id.perfilButton);

      ImageButton botonNivel1 = findViewById(R.id.nivel1);

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(NivelesmaActivity.this, MapActivity.class);
        startActivity(intent);
      });

      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(NivelesmaActivity.this, UserActivity.class);
        startActivity(intent);
      });

      botonNivel1.setOnClickListener(v -> {
        Intent intent = new Intent(NivelesmaActivity.this, HistoriaAguaActivity.class);
        startActivity(intent);
      });



    }

    private void deshabilitarTodosMenos(int maxNivelPermitido) {
        for (int i = 1; i <= 9; i++) { // Cambia a la cantidad total de niveles reales
            int resID = getResources().getIdentifier("b" + i, "id", getPackageName());
            ImageButton boton = findViewById(resID);
            if (boton != null) {
                if (i > maxNivelPermitido) {
                    boton.setAlpha(0.7f);
                    boton.setEnabled(false);
                    boton.setOnClickListener(v ->
                            Toast.makeText(this, "Este nivel está bloqueado", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    boton.setAlpha(1.0f);
                    boton.setEnabled(true);
                }
            }
        }
    }

}
