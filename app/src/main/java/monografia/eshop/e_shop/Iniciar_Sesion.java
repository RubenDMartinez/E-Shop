package monografia.eshop.e_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Iniciar_Sesion extends AppCompatActivity {

    EditText txtUsu, txtCon;
    Button btnIng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        txtUsu = findViewById(R.id.txtUsu_Usu);
        txtCon = findViewById(R.id.txtCon_Usu);
        Button btnIng = findViewById(R.id.btnIng);

        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsu.getText().toString().trim();
                String cont = txtCon.getText().toString().trim();

                // Obtiene una referencia al InputMethodManager
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Oculta el teclado
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (TextUtils.isEmpty(user)) {
                    txtUsu.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(cont)) {
                    txtCon.setError("Campo requerido");
                } else {
                    ValidarUsuario("http://10.0.0.37/BDRemota/wsJSONIniciarSesion.php");
                }
            }
        });

    }

    private void ValidarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Manejar la respuesta exitosa del servidor aquí
                if (!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Iniciar_Sesion.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar el error de respuesta del servidor aquí
                Toast.makeText(Iniciar_Sesion.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // Agregar los parámetros que deseas enviar al servidor aquí
                // Ejemplo:
                // params.put("clave", "valor");
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("user", txtUsu.getText().toString());
                parametros.put("cont", txtCon.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void CambioReg_Usu(View view) {
        Intent Cambiar = new Intent(this, RegistrarUsuario.class);
        startActivity(Cambiar);
    }

}