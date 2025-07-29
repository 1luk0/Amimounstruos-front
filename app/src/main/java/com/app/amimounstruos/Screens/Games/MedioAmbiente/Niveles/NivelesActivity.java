package com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Services.ProgresoService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NivelesActivity extends AppCompatActivity {

    private ImageButton[] botones;
    private static final int CURSO_ID = 11; // Constante para el curso actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles); // Asegúrate de que sea el nombre correcto del XML

        // Inicializar los botones del layout
        botones = new ImageButton[]{
                findViewById(R.id.btn1),
                findViewById(R.id.btn2),
                findViewById(R.id.btn3),
                findViewById(R.id.btn4),
                findViewById(R.id.btn5),
                findViewById(R.id.btn6),
                findViewById(R.id.btn7),
                findViewById(R.id.btn8),
                findViewById(R.id.btn9)
        };

        // Lógica principal
        obtenerYAplicarProgreso();
    }

    private void obtenerYAplicarProgreso() {
        // Obtener el user_id guardado en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3333/") // Cambia esto por tu URL real si no estás en emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProgresoService service = retrofit.create(ProgresoService.class);

        // Llamar al backend para obtener el progreso
        Call<ResponseBody> call = service.getProgresoByUserId(userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String body = response.body().string().trim();
                        int progreso = Integer.parseInt(body);
                        aplicarProgreso(progreso);
                    } catch (IOException | NumberFormatException e) {
                        Toast.makeText(NivelesActivity.this, "Error al interpretar el progreso", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(NivelesActivity.this, "Error al obtener progreso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NivelesActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aplicarProgreso(int progreso) {
        int limite = progreso + 1;

        for (int i = 0; i < botones.length; i++) {
            boolean habilitado = (i + 1) <= limite;

            botones[i].setEnabled(habilitado);
            botones[i].setClickable(habilitado);

            // Cambiar opacidad: 100% si está habilitado, 40% si no
            botones[i].setAlpha(habilitado ? 1.0f : 0.4f);
        }
    }
}