package com.app.amimounstruos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activarModoInmersivo();
  }

  @Override
  public void startActivity(Intent intent) {
    super.startActivity(intent);
    // animación al abrir nueva activity
    overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out);
  }

  @Override
  public void finish() {
    super.finish();
    // animación al cerrar activity
    overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out);
  }

  private void activarModoInmersivo() {
    final View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN
    );
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      activarModoInmersivo();
    }
  }
}
