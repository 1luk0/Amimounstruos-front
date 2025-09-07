package com.app.amimounstruos.Screens.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Models.User;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectCharacterActivity extends BaseActivity {

  private ImageView imgPersonaje;
  private int personajeActual = 0;
  private int[] personajes = {
          R.drawable.monster1,
          R.drawable.monster2,
          R.drawable.monster3,
          R.drawable.monster4
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_character);

    imgPersonaje = findViewById(R.id.imgPersonaje);
    ImageButton btnIzquierda = findViewById(R.id.btizquierda);
    ImageButton btnDerecha = findViewById(R.id.btderecha);
    ImageButton btnConfirmar = findViewById(R.id.btcheck);

    actualizarImagen();

    btnIzquierda.setOnClickListener(v -> {
      personajeActual = (personajeActual - 1 + personajes.length) % personajes.length;
      actualizarImagen();
    });

    btnDerecha.setOnClickListener(v -> {
      personajeActual = (personajeActual + 1) % personajes.length;
      actualizarImagen();
    });

    btnConfirmar.setOnClickListener(v -> {
      int personajeSeleccionado = personajeActual + 1;
      confirmarSeleccion(personajeSeleccionado);
    });
  }

  private void actualizarImagen() {
    imgPersonaje.setImageResource(personajes[personajeActual]);
  }

  private void confirmarSeleccion(int personajeSeleccionado) {
    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    String nombre = prefs.getString("nombre_usuario", null);

    if (nombre == null) {
      Toast.makeText(this, "Error: nombre no encontrado", Toast.LENGTH_SHORT).show();
      return;
    }

    // Guardar el personaje en SharedPreferences
    prefs.edit().putInt("personaje_seleccionado", personajeSeleccionado).apply();

    // Enviar al backend
    User user = new User(nombre, personajeSeleccionado);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_URL) // Reemplaza con tu endpoint real
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    UserService api = retrofit.create(UserService.class);
    Call<User> call = api.registrarUsuario(user);
    call.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful() && response.body() != null) {
          User userResponse = response.body();

          // Guardar el ID globalmente
          SharedPreferences.Editor editor = prefs.edit();
          editor.putInt("user_id", userResponse.getId());
          editor.apply();

          // Ir a la siguiente pantalla
          Intent intent = new Intent(SelectCharacterActivity.this, MapActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(SelectCharacterActivity.this, "Error al registrar personaje", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(SelectCharacterActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}
