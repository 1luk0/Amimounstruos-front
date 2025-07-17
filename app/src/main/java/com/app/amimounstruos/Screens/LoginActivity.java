package com.app.amimounstruos.Screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Services.APIService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

  private EditText editTextNombre;
  private APIService api;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Retrofit
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://10.0.2.2:3333/") // Asegúrate de usar esta IP para localhost en emulador
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    api = retrofit.create(APIService.class);

    editTextNombre = findViewById(R.id.nameInput);

    editTextNombre.setOnEditorActionListener((TextView v, int actionId, android.view.KeyEvent event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_NULL) {
        String nombre = editTextNombre.getText().toString().trim();

        if (nombre.isEmpty()) {
          Toast.makeText(this, "Por favor, ingresa tu nombre monstruoso", Toast.LENGTH_SHORT).show();
          return true;
        }

        verificarNombreEnBackend(nombre);
        return true;
      }
      return false;
    });
  }

  private void verificarNombreEnBackend(String nombre) {
    Call<ResponseBody> call = api.verificarNombre(nombre);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.code() == 200) {
          // Ya existe
          Toast.makeText(LoginActivity.this, "Ese nombre ya está en uso. Elige otro.", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 204) {
          // No existe, continuar
          SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
          prefs.edit()
            .putString("nombre_usuario", nombre)
            .putBoolean("usuario_registrado", true)
            .apply();

          Intent intent = new Intent(LoginActivity.this, SelectCharacterActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LoginActivity.this, "Error inesperado del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(LoginActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

}
