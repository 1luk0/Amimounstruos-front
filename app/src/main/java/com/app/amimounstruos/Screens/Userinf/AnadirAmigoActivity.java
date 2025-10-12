package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.Models.Amigo;
import com.app.amimounstruos.Models.User;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Services.UserService;
import com.app.amimounstruos.Services.AmigoService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnadirAmigoActivity extends BaseActivity {

  private UserService userService;
  private AmigoService amigosService;
  private LinearLayout contenedorResultados;
  private EditText nameInput;
  private SharedPreferences prefs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_anadir_amigo);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Preferencias
    prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);

    // ✅ Inicializar Retrofit como en SelectCharacterActivity
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    userService = retrofit.create(UserService.class);
    amigosService = retrofit.create(AmigoService.class);

    // Vistas
    nameInput = findViewById(R.id.nameInput);
    contenedorResultados = findViewById(R.id.friendListContainer);

    ImageButton botonMapa = findViewById(R.id.mapButton);
    ImageButton botonPerfil = findViewById(R.id.perfilButton);
    ImageButton botonConfiguracion = findViewById(R.id.configurations);
    ImageButton botonReturn = findViewById(R.id.returnButton);

    // Avatar dinámico
    int personajeSeleccionado = prefs.getInt("personaje_seleccionado", -1);
    int avatarBtnResId;
    switch (personajeSeleccionado) {
      case 1: avatarBtnResId = R.drawable.monster1btn; break;
      case 2: avatarBtnResId = R.drawable.monster2btn; break;
      case 3: avatarBtnResId = R.drawable.monster3btn; break;
      case 4: avatarBtnResId = R.drawable.monster4btn; break;
      default: avatarBtnResId = R.drawable.monster1btn; break;
    }
    botonPerfil.setImageResource(avatarBtnResId);

    // Navegación
    botonMapa.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
    botonConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, configuracionActivity.class)));
    botonPerfil.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));
    botonReturn.setOnClickListener(v -> startActivity(new Intent(this, AmigosActivity.class)));

    // Buscar al presionar "Listo"
    nameInput.setOnKeyListener((v, keyCode, event) -> {
      if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        buscarUsuarioPorNombre(nameInput.getText().toString());
        return true;
      }
      return false;
    });
  }

  private void buscarUsuarioPorNombre(String nombre) {
    contenedorResultados.removeAllViews();

    if (nombre.isEmpty()) return;

    // Llamada al backend para buscar usuario por nombre
    Call<User> call = userService.obtenerusuarioByName(nombre);
    call.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (!response.isSuccessful() || response.body() == null) {
          Toast.makeText(AnadirAmigoActivity.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
          return;
        }


        User usuarioEncontrado = response.body();

        LayoutInflater inflater = LayoutInflater.from(AnadirAmigoActivity.this);
        View itemAmigo = inflater.inflate(R.layout.amigo_item, contenedorResultados, false);

        TextView nombreTxt = itemAmigo.findViewById(R.id.txtNombre);
        Button btnAgregar = itemAmigo.findViewById(R.id.btnAccion);

        nombreTxt.setText(usuarioEncontrado.getNombre());
        contenedorResultados.setVisibility(View.VISIBLE); // 👈 Hacer visible el contenedor

        btnAgregar.setOnClickListener(v -> {
          // ⚠️ implementa esta función según tu lógica
          Amigo nuevoAmigo = new Amigo(obtenerUsuarioActualId(), usuarioEncontrado.getId());

          amigosService.registrarAmigo(nuevoAmigo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
              if (response.isSuccessful()) {
                Toast.makeText(AnadirAmigoActivity.this, "Amigo añadido correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AnadirAmigoActivity.this, AmigosActivity.class));
                finish();
              } else {
                Toast.makeText(AnadirAmigoActivity.this, "Error al añadir amigo", Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
              Toast.makeText(AnadirAmigoActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
          });
        });

        contenedorResultados.addView(itemAmigo);
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(AnadirAmigoActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }
  private int obtenerUsuarioActualId() {
    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    return prefs.getInt("user_id", -1);
  }

}
