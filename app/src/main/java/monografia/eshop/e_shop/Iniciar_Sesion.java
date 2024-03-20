package monografia.eshop.e_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Iniciar_Sesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        EditText txtUsu = findViewById(R.id.txtUsu_Usu);
        EditText txtCon = findViewById(R.id.txtCon_Usu);
        Button btnIng = findViewById(R.id.btnIng);

        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsu.getText().toString().trim();
                String cont = txtCon.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    txtUsu.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(cont)){
                    txtCon.setError("Campo requerido");
                } else {
                    Intent Cambiar = new Intent(Iniciar_Sesion.this, MainActivity.class);
                    startActivity(Cambiar);
                }
            }
        });

    }

    public void CambioReg_Usu(View view) {
        Intent Cambiar = new Intent(this, RegistrarUsuario.class);
        startActivity(Cambiar);
    }

}