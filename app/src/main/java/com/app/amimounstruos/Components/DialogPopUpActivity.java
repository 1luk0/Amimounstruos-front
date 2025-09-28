package com.app.amimounstruos.Components;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log; // Asegúrate de tener este import para logging

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.amimounstruos.R;
import java.io.Serializable;

public class DialogPopUpActivity extends DialogFragment {

  private static final String TAG = "DialogPopUpActivity";
  private static final String KEY_DESTINATION_ACTIVITY = "destination_activity";
  private static final String KEY_IMAGE_RES_ID = "image_res_id";
  private static final String KEY_CALLBACK = "callback";

  // 1. Interfaz de Callback para acciones personalizadas (como Logout)
  public interface DialogActionCallback extends Serializable {
    void onActionConfirmed();
  }

  private Class<?> destinationClass;
  private int imageResId = -1;
  private DialogActionCallback callback; // Será null para el caso "Salir a Niveles"

  /**
   * Constructor genérico para Salir a Niveles (sin acción de limpieza).
   */
  public static DialogPopUpActivity newInstance(Class<?> destinationActivityClass, int drawableResId) {
    return newInstance(destinationActivityClass, drawableResId, null);
  }

  /**
   * Constructor para Cerrar Sesión (con acción de limpieza).
   */
  public static DialogPopUpActivity newInstance(Class<?> destinationActivityClass, int drawableResId, @Nullable DialogActionCallback callback) {
    DialogPopUpActivity fragment = new DialogPopUpActivity();
    Bundle args = new Bundle();
    args.putSerializable(KEY_DESTINATION_ACTIVITY, destinationActivityClass);
    args.putInt(KEY_IMAGE_RES_ID, drawableResId);

    if (callback != null) {
      args.putSerializable(KEY_CALLBACK, callback);
    }
    fragment.setArguments(args);
    return fragment;
  }

  public DialogPopUpActivity() {
    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      Serializable serializableClass = getArguments().getSerializable(KEY_DESTINATION_ACTIVITY);
      if (serializableClass instanceof Class) {
        destinationClass = (Class<?>) serializableClass;
      }
      imageResId = getArguments().getInt(KEY_IMAGE_RES_ID, -1);

      // Recuperar el callback (será null si no se envió)
      Serializable serializableCallback = getArguments().getSerializable(KEY_CALLBACK);
      if (serializableCallback instanceof DialogActionCallback) {
        callback = (DialogActionCallback) serializableCallback;
      }
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_dialog_pop_up, container, false);

    ImageView popupImage = view.findViewById(R.id.deseassalir);
    if (popupImage != null && imageResId != -1) {
      popupImage.setImageResource(imageResId);
    } else if (popupImage != null) {
      popupImage.setImageResource(R.drawable.salirbk);
    }

    ImageButton yesButton = view.findViewById(R.id.yesButton);
    ImageButton noButton = view.findViewById(R.id.noButton);

    if (yesButton != null) {
      yesButton.setOnClickListener(v -> {
        // EJECUCIÓN CLAVE: Si hay un callback (Logout), se ejecuta la limpieza.
        if (callback != null) {
          Log.d(TAG, "Ejecutando callback de acción (Logout).");
          callback.onActionConfirmed();
        }

        if (destinationClass != null && getActivity() != null) {
          Intent intent = new Intent(getActivity(), destinationClass);

          // Lógica para Logout: Si hay un callback, significa que estamos cerrando sesión
          if (callback != null) {
            Log.d(TAG, "Cerrando sesión, limpiando stack de Activities.");
            // Limpia la pila de Activities para asegurar que no puedan regresar
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          } else {
            // Lógica normal para Salir a Niveles: no limpia el stack
            Log.d(TAG, "Saliendo a Niveles, manteniendo stack.");
          }

          startActivity(intent);
        }
        dismiss();
      });
    }

    if (noButton != null) {
      noButton.setOnClickListener(v -> {
        dismiss();
      });
    }

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    Window window = getDialog().getWindow();
    if (window != null) {
      window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      window.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
      );

      window.getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
      );

      window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
  }
}
