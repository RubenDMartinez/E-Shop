package monografia.eshop.e_shop.Fragments.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import monografia.eshop.e_shop.R;
import monografia.eshop.e_shop.databinding.FragmentInicioBinding;

import org.json.JSONObject;

public class FragmentInicio extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    EditText txtNom_Usu, txtApe_Usu, txtCed_Usu, txtNac_Usu, txtCel_Usu, txtEml_Usu, txtBar_Usu, txtCiu_Usu, txtDep_Usu, txtDir_Usu, txtUsu_Usu, txtCon_Usu;
    Button botReg;

    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        txtNom_Usu = (EditText) vista.findViewById(R.id.txtNom_Usu);

        botReg = (Button) vista.findViewById(R.id.btnTerRegUsu);

        request = Volley.newRequestQueue(getContext());

        botReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
        return vista;
    }

    private void cargarWebService() {

        progreso =  new ProgressDialog(getContext());
        progreso.setMessage("Registrando usuario...");
        progreso.show();

        String url = "http://172.18.2.247/conexionEShop/RegistrarUsuario.php?cedu="+txtCed_Usu.getText().toString()+
                "&nomb="+txtNom_Usu.getText().toString()+"&apel="+txtApe_Usu.getText().toString()+
                "&fnac="+txtNac_Usu.getText().toString()+"&ncel="+txtCel_Usu.getText().toString()+
                "&usua="+txtUsu_Usu.getText().toString()+"&emai="+txtEml_Usu.getText().toString()+
                "&cont="+txtCon_Usu.getText().toString()+"&dire="+txtDir_Usu.getText().toString()+
                "&barr="+txtDir_Usu.getText().toString()+"&ciud="+txtCiu_Usu.getText().toString()+
                "&depa="+txtDep_Usu.getText().toString();

        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
        progreso.hide();



        txtCed_Usu.setText("");
        txtNom_Usu.setText("");
        txtApe_Usu.setText("");
        txtNac_Usu.setText("");
        txtCel_Usu.setText("");
        txtUsu_Usu.setText("");
        txtEml_Usu.setText("");
        txtCon_Usu.setText("");
        txtDir_Usu.setText("");
        txtBar_Usu.setText("");
        txtCiu_Usu.setText("");
        txtDep_Usu.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se pudo registrar " + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("Error", error.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
/*
 * Cambiar IP
 * Editar linea 79 a 85 (IP)
 * */
//http://172.18.3.8/conexionEShop/RegistrarUsuario.php?cedu=4&nomb=de&apel=de&fnac=4&ncel=4&usua=de&emai=de&cont=de&dire=de&barr=de&ciud=de&depa=de