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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.app.amimounstruos.R;
import java.io.Serializable;

public class DialogPopUpActivity extends DialogFragment {

  private static final String KEY_DESTINATION_ACTIVITY = "destination_activity";
  private Class<?> destinationClass;

  public static DialogPopUpActivity newInstance(Class<?> destinationActivityClass) {
    DialogPopUpActivity fragment = new DialogPopUpActivity();
    Bundle args = new Bundle();
    args.putSerializable(KEY_DESTINATION_ACTIVITY, destinationActivityClass);
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
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_dialog_pop_up, container, false);

    ImageButton yesButton = view.findViewById(R.id.yesButton);
    ImageButton noButton = view.findViewById(R.id.noButton);

    if (yesButton != null) {
      yesButton.setOnClickListener(v -> {
        if (destinationClass != null && getActivity() != null) {
          Intent intent = new Intent(getActivity(), destinationClass);
          startActivity(intent);
          getActivity().finish();
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
      // Establece el fondo como transparente y ajusta el tamaño
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

      // Elimina la sombra oscura detrás del diálogo
      window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
  }
}
