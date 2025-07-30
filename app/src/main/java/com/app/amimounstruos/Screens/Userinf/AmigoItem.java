package com.app.amimounstruos.Screens.Userinf;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.amimounstruos.R;


public class AmigoItem extends LinearLayout {
    private TextView txtNombre;
    private ImageView imgAmimounstruo;
    private ImageButton btnAccion;

    public AmigoItem(Context context) {
        super(context);
        init(context);
    }

    public AmigoItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AmigoItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.amigo_item, this, true);
        txtNombre = findViewById(R.id.txtNombre);
        imgAmimounstruo = findViewById(R.id.imgAmimounstruo);
        btnAccion = findViewById(R.id.btnAccion);
    }

    public void setNombre(String nombre) {
        txtNombre.setText(nombre);
    }

    public void setNumero(int numero) {
        // Mapeo del número al drawable
        int resId;
        switch (numero) {
            case 1: resId = R.drawable.monster1; break;
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
