package monografia.eshop.e_shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText camp1, camp2, camp3, camp4, camp5, camp6, camp7, camp8, camp9, camp10, camp11, camp12;
    Button botRegUsu, botFot;
    ProgressDialog progreso;
    ImageView imgFoto;

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
        camp11 = (EditText) findViewById(R.id.txtUsu_Usu);
        camp12 = (EditText) findViewById(R.id.txtCon_Usu);
        botRegUsu = (Button) findViewById(R.id.btnTerRegUsu);
        imgFoto = (ImageView) findViewById(R.id.imagenId);

        request = Volley.newRequestQueue(this);

        botRegUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick (View view) {
                insertarDatosNuevoUsuario();
            }
        });

        /*botFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });*/
    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")) {
                    //metodo para llamar a camara
                    //Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"),10);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
    }

    public void insertarDatosNuevoUsuario() {

        progreso =  new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://192.168.1.8/conexionEShop/RegistrarUsuario.php?cedu="+camp3.getText().toString()+
                "&nomb="+camp1.getText().toString()+"&apel="+camp2.getText().toString()+
                "&fnac="+camp4.getText().toString()+"&ncel="+camp5.getText().toString()+
                "&usua="+camp11.getText().toString()+"&emai="+camp6.getText().toString()+
                "&cont="+camp12.getText().toString()+"&dire="+camp10.getText().toString()+
                "&barr="+camp7.getText().toString()+"&ciud="+camp8.getText().toString()+
                "&depa="+camp9.getText().toString();

        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
        progreso.hide();

        camp1.setText("");
        camp2.setText("");
        camp3.setText("");
        camp4.setText("");
        camp5.setText("");
        camp6.setText("");
        camp7.setText("");
        camp8.setText("");
        camp9.setText("");
        camp10.setText("");
        camp11.setText("");
        camp12.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this, "No se pudo registrar " + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("Error", error.toString());
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
        usua = camp11.getText().toString();
        cont = camp12.getText().toString();

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
        } if (usua.isEmpty()) {
            camp11.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (cont.isEmpty()) {
            camp12.setError("Este campo no puede quedar vacio");
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
