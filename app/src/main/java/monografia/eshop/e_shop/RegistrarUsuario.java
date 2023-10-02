package monografia.eshop.e_shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegistrarUsuario extends AppCompatActivity {

    EditText camp1, camp2, camp3, camp4, camp5, camp6, camp7, camp8, camp9, camp10, camp11, camp12, camp13;
    Button botRegUsu;

    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        camp1 = (EditText) findViewById(R.id.txtNom_Usu);
        camp2 = (EditText) findViewById(R.id.txtApe_Usu);
        camp3 = (EditText) findViewById(R.id.txtCed_Usu);
        camp4 = (EditText) findViewById(R.id.txtNac_Usu);
        camp5 = (EditText) findViewById(R.id.txtCel_Usu);
        camp6 = (EditText) findViewById(R.id.txtEml_Usu);
        camp7 = (EditText) findViewById(R.id.txtBar_Usu);
        camp8 = (EditText) findViewById(R.id.txtCiu_Usu);
        camp9 = (EditText) findViewById(R.id.txtDep_Usu);
        camp10 = (EditText) findViewById(R.id.txtDir_Usu);
        camp11 = (EditText) findViewById(R.id.txtApa_Usu);
        camp12 = (EditText) findViewById(R.id.txtUsu_Usu);
        camp13 = (EditText) findViewById(R.id.txtCon_Usu);

        botRegUsu = (Button) findViewById(R.id.btnTerRegUsu);

        botRegUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick (View view) {
                insertarDatos();
            }
        });

    }

    public void insertarDatos() {
        final String nombre = camp1.getText().toString().trim();
        final String apelli = camp2.getText().toString().trim();
        final String cedula = camp3.getText().toString().trim();
        final String fecNac = camp4.getText().toString().trim();
        final String numCel = camp5.getText().toString().trim();
        final String corEle = camp6.getText().toString().trim();
        final String barrio = camp7.getText().toString().trim();
        final String ciudad = camp8.getText().toString().trim();
        final String depart = camp9.getText().toString().trim();
        final String direcc = camp10.getText().toString().trim();
        final String aparta = camp11.getText().toString().trim();
        final String nomUsu = camp12.getText().toString().trim();
        final String conUsu = camp13.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando usuario");

        if (nombre.isEmpty()) {
            camp1.setError("Este campo no puede quedar vacio");
        } if (apelli.isEmpty()) {
            camp2.setError("Este campo no puede quedar vacio");
        } if (cedula.isEmpty()) {
            camp3.setError("Este campo no puede quedar vacio");
        } if (fecNac.isEmpty()) {
            camp4.setError("Este campo no puede quedar vacio");
        } if (numCel.isEmpty()) {
            camp5.setError("Este campo no puede quedar vacio");
        } if (corEle.isEmpty()) {
            camp6.setError("Este campo no puede quedar vacio");
        } if (barrio.isEmpty()) {
            camp7.setError("Este campo no puede quedar vacio");
        } if (ciudad.isEmpty()) {
            camp8.setError("Este campo no puede quedar vacio");
        } if (depart.isEmpty()) {
            camp9.setError("Este campo no puede quedar vacio");
        } if (direcc.isEmpty()) {
            camp10.setError("Este campo no puede quedar vacio");
        } if (aparta.isEmpty()) {
            camp11.setError("Este campo no puede quedar vacio");
        } if (nomUsu.isEmpty()) {
            camp12.setError("Este campo no puede quedar vacio");
        } if (conUsu.isEmpty()) {
            camp13.setError("Este campo no puede quedar vacío");
        }
    }

    public boolean validar() {
        boolean retorno = true;

        String nomb;
        String apel;
        String cedu;
        String naci;
        String celu;
        String emai;
        String barr;
        String ciud;
        String depa;
        String dire;
        String apar;
        String usua;
        String cont;

        nomb = camp1.getText().toString();
        apel = camp2.getText().toString();
        cedu = camp3.getText().toString();
        naci = camp4.getText().toString();
        celu = camp5.getText().toString();
        emai = camp6.getText().toString();
        barr = camp7.getText().toString();
        ciud = camp8.getText().toString();
        depa = camp9.getText().toString();
        dire = camp10.getText().toString();
        apar = camp11.getText().toString();
        usua = camp12.getText().toString();
        cont = camp13.getText().toString();

        if (nomb.isEmpty()) {
            camp1.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (apel.isEmpty()) {
            camp2.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (cedu.isEmpty()) {
            camp3.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (naci.isEmpty()) {
            camp4.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (celu.isEmpty()) {
            camp5.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (emai.isEmpty()) {
            camp6.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (barr.isEmpty()) {
            camp7.setError("Este campo no puede quedar vacio");
        } if (ciud.isEmpty()) {
            camp8.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (depa.isEmpty()) {
            camp9.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (dire.isEmpty()) {
            camp10.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (apar.isEmpty()) {
            camp11.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (usua.isEmpty()) {
            camp12.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (cont.isEmpty()) {
            camp13.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        return retorno;
    }

    public void RegUsu (View view) {
        if (validar()) {
            Toast.makeText(this, "Datos agregados", Toast.LENGTH_SHORT).show();
            Intent Cambiar = new Intent(this, IniciarSesion.class);
            startActivity(Cambiar);
        }
    }

}
