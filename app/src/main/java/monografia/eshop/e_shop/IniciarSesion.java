package monografia.eshop.e_shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);
    }

    public void CambioReg_Usu(View view) {
        Intent Cambiar = new Intent(this, RegistrarUsuario.class);
        startActivity(Cambiar);
    }

    public void CambioPri_Usu(View view) {
        Intent Cambiar = new Intent(this, MainActivity.class);
        startActivity(Cambiar);
    }
}
