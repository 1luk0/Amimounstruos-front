package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnadirAmigoActivity extends BaseActivity {

  private static final String TAG = "AnadirAmigo";

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
    contenedorResultados = findViewById(R.id.listaResultados);

    TextView textoSinResultado = findViewById(R.id.buscarAmigo);
    textoSinResultado.setText("Escribe el nombre de tu amigo para agregarlo");
    textoSinResultado.setVisibility(View.VISIBLE);

    contenedorResultados.setVisibility(View.GONE);

    prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);

    // Retrofit init
    Log.d(TAG, "🔧 Iniciando Retrofit con URL base: " + BuildConfig.BACKEND_URL);
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();



    userService = retrofit.create(UserService.class);
    amigosService = retrofit.create(AmigoService.class);

    nameInput = findViewById(R.id.nameInput);


    ImageButton botonMapa = findViewById(R.id.mapButton);
    ImageButton botonPerfil = findViewById(R.id.perfilButton);
    ImageButton botonConfiguracion = findViewById(R.id.configurations);
    ImageButton botonReturn = findViewById(R.id.returnButton);

    int personajeSeleccionado = prefs.getInt("personaje_seleccionado", -1);
    int avatarBtnResId;
    switch (personajeSeleccionado) {
      case 1: avatarBtnResId = R.drawable.monster1btn; break;
      case 2: avatarBtnResId = R.drawable.monster2btn; break;
      case 3: avatarBtnResId = R.drawable.monster3btn; break;
      case 4: avatarBtnResId = R.drawable.monster4btn; break;
      default: avatarBtnResId = R.drawable.monster4btn; break;
    }
    botonPerfil.setImageResource(avatarBtnResId);

    botonMapa.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
    botonConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, configuracionActivity.class)));
    botonPerfil.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));
    botonReturn.setOnClickListener(v -> startActivity(new Intent(this, AmigosActivity.class)));

    nameInput.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE ||
        actionId == EditorInfo.IME_ACTION_GO ||
        actionId == EditorInfo.IME_NULL) {

        String nombre = nameInput.getText().toString().trim();
        if (nombre.isEmpty()) {
          Toast.makeText(this, "Por favor ingresa un nombre para buscar", Toast.LENGTH_SHORT).show();
          return true;
        }

        // 🔽 Ocultar el teclado
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }


        Log.d("AnadirAmigo", "🟢 onEditorAction disparado con nombre: " + nombre);
        buscarUsuarioPorNombre(nombre);
        return true;
      }
      return false;
    });

  }

  private void buscarUsuarioPorNombre(String nombre) {

    // ✅ Asegúrate de que este TextView sea el que muestra el mensaje (no el campo de búsqueda)
    TextView textoSinResultado = findViewById(R.id.buscarAmigo);

    if (nombre.isEmpty()) {
      textoSinResultado.setText("Escribe el nombre de tu amigo para agregarlo");
      textoSinResultado.setVisibility(View.VISIBLE);
      contenedorResultados.setVisibility(View.GONE);
      return;
    }


    // Mostrar “buscando…” momentáneo
    textoSinResultado.setText("Buscando amigo...");
    textoSinResultado.setVisibility(View.VISIBLE);
    contenedorResultados.setVisibility(View.GONE);

    Log.d(TAG, "📡 Iniciando llamada a obtenerusuarioByName...");
    Call<User> call = userService.obtenerusuarioByName(nombre);
    call.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        Log.d(TAG, "✅ onResponse recibido. Código: " + response.code());

        if (!response.isSuccessful()) {
          Log.e(TAG, "❌ Respuesta no exitosa. Código: " + response.code());
          textoSinResultado.setText("No encontramos a ese amigo. Intenta con otro nombre");
          textoSinResultado.animate().alpha(1f).setDuration(200);
          textoSinResultado.setVisibility(View.VISIBLE);
          return;
        }

        User usuarioEncontrado = response.body();

        if (usuarioEncontrado == null) {
          Log.e(TAG, "❌ Usuario nulo en la respuesta");
          textoSinResultado.setText("No encontramos a ese amigo. Intenta con otro nombre");
          textoSinResultado.animate().alpha(1f).setDuration(200);
          textoSinResultado.setVisibility(View.VISIBLE);
          return;
        }

        Log.d(TAG, "🎯 Usuario encontrado: " + usuarioEncontrado.getNombre() + " (ID: " + usuarioEncontrado.getId() + ")");
        textoSinResultado.animate().alpha(0f).setDuration(150);
        textoSinResultado.setVisibility(View.GONE);

        LayoutInflater inflater = LayoutInflater.from(AnadirAmigoActivity.this);
        View itemAmigo = inflater.inflate(R.layout.amigo_item, contenedorResultados, false);

        ImageView imgAvatar = itemAmigo.findViewById(R.id.imgAmimounstruo); // 👈 debe existir en amigo_item.xml

        int monstruoId = usuarioEncontrado.getAmimounstruo();
        int avatarResId;

        switch (monstruoId) {
          case 1: avatarResId = R.drawable.monster1; break;
          case 2: avatarResId = R.drawable.monster2; break;
          case 3: avatarResId = R.drawable.monster3; break;
          case 4: avatarResId = R.drawable.monster4; break;
          default: avatarResId = R.drawable.monster1; break;
        }

        imgAvatar.setImageResource(avatarResId);


        TextView nombreTxt = itemAmigo.findViewById(R.id.txtNombre);
        ImageButton btnAgregar = itemAmigo.findViewById(R.id.btnAccion);

        nombreTxt.setText(usuarioEncontrado.getNombre());
        contenedorResultados.setVisibility(View.VISIBLE);

        btnAgregar.setOnClickListener(v -> {
          Amigo nuevoAmigo = new Amigo(obtenerUsuarioActualId(), usuarioEncontrado.getId());
          Log.d(TAG, "👥 Enviando solicitud para agregar amigo: " + nuevoAmigo);
          amigosService.registrarAmigo(nuevoAmigo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
              if (response.isSuccessful()) {
                Log.d(TAG, "✅ Amigo añadido correctamente");
                Toast.makeText(AnadirAmigoActivity.this, "Amigo añadido correctamente 🎉", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AnadirAmigoActivity.this, AmigosActivity.class));
                finish();
              } else {
                Log.e(TAG, "❌ Error al añadir amigo. Código: " + response.code());
                Toast.makeText(AnadirAmigoActivity.this, "Error al añadir amigo", Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
              Log.e(TAG, "🌐 Error de red al añadir amigo: " + t.getMessage(), t);
              Toast.makeText(AnadirAmigoActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
          });
        });

        contenedorResultados.addView(itemAmigo);
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Log.e(TAG, "🌐 Error de red al buscar usuario: " + t.getMessage(), t);
        textoSinResultado.setText("Error de conexión 😣 Intenta nuevamente");
        textoSinResultado.animate().alpha(1f).setDuration(200);
        textoSinResultado.setVisibility(View.VISIBLE);
      }
    });
  }



  private int obtenerUsuarioActualId() {
    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    int id = prefs.getInt("user_id", -1);
    Log.d(TAG, "🧩 Usuario actual ID=" + id);
    return id;
  }
}
