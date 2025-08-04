package com.app.amimounstruos.Screens.Userinf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.amimounstruos.R;

public class AmigoItem extends LinearLayout {

  private TextView txtNombre;
  private ImageView imgAmimounstruo;
  private ImageButton btnAccion;

  public AmigoItem(Context context) {
    super(context);
    init(context);
  }

  private void init(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.amigo_item, this, true);

    txtNombre = view.findViewById(R.id.txtNombre);
    imgAmimounstruo = view.findViewById(R.id.imgAmimounstruo);
    btnAccion = view.findViewById(R.id.btnAccion);

    btnAccion.setOnClickListener(v ->
      Toast.makeText(context, "Ver perfil de " + txtNombre.getText(), Toast.LENGTH_SHORT).show()
    );
  }

  public void setNombre(String nombre) {
    txtNombre.setText(nombre);
  }

  public void setNumero(int numero) {
    // Asegúrate de tener monster1.png hasta monster4.png en res/drawable
    int resId;
    switch (numero) {
      case 2: resId = R.drawable.monster2; break;
      case 3: resId = R.drawable.monster3; break;
      case 4: resId = R.drawable.monster4; break;
      default: resId = R.drawable.monster1;
    }
    imgAmimounstruo.setImageResource(resId);
  }

  public void setOnAccionClickListener(OnClickListener listener) {
    btnAccion.setOnClickListener(listener);
  }
}
