package com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Models.Nivel;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Services.NivelService;
import com.app.amimounstruos.Services.ProgresoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NivelesmaActivity extends AppCompatActivity {
    private static final int CURSO_ID = 11; // ← Cambia esto si tu curso tiene otro ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivelesma); // ← Usa el nombre de tu layout XML real

        SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        if (userId == 0) {
            Toast.makeText(this, "ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3333/") // ← Cambia esto por tu IP real si es dispositivo físico
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProgresoService progresoService = retrofit.create(ProgresoService.class);
        NivelService nivelService = retrofit.create(NivelService.class);

        progresoService.getProgresoByUsuarioYCurso(userId, CURSO_ID).enqueue(new Callback<Progreso>() {
            @Override
            public void onResponse(Call<Progreso> call, Response<Progreso> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int nivelId = (int) response.body().getNivelId();
                    if (nivelId == 0) {
                        deshabilitarTodosMenos(1);
                    } else {
                        nivelService.getNivelById(nivelId).enqueue(new Callback<Nivel>() {
                            @Override
                            public void onResponse(Call<Nivel> call, Response<Nivel> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    int numero = (int) response.body().getNumero();
                                    deshabilitarTodosMenos(numero + 1);
                                } else {
                                    deshabilitarTodosMenos(1);
                                }
                            }

                            @Override
                            public void onFailure(Call<Nivel> call, Throwable t) {
                                deshabilitarTodosMenos(1);
                            }
                        });
                    }
                } else {
                    deshabilitarTodosMenos(1);
                }
            }

            @Override
            public void onFailure(Call<Progreso> call, Throwable t) {
                deshabilitarTodosMenos(1);
            }
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