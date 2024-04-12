package monografia.eshop.e_shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mi_Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mi_Perfil extends Fragment {

    EditText txtCed, txtNom, txtApe, txtCel, txtFna, txtCor, txtDir, txtBar, txtCiu, txtDep, txtUsu, txtCon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mi_Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mi_Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Mi_Perfil newInstance(String param1, String param2) {
        Mi_Perfil fragment = new Mi_Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        // Asignar elementos de interfaz de usuario a variables
        txtCed = view.findViewById(R.id.txtCed);
        txtNom = view.findViewById(R.id.txtNom);
        txtApe = view.findViewById(R.id.txtApe);
        txtCel = view.findViewById(R.id.txtCel);
        txtFna = view.findViewById(R.id.txtFna);
        txtCor = view.findViewById(R.id.txtCor);
        txtDir = view.findViewById(R.id.txtDir);
        txtBar = view.findViewById(R.id.txtBar);
        txtCiu = view.findViewById(R.id.txtCiu);
        txtDep = view.findViewById(R.id.txtDep);
        txtUsu = view.findViewById(R.id.txtUsu);
        txtCon = view.findViewById(R.id.txtCon);

        // Deshabilitar la edición de los campos de texto
        txtCed.setEnabled(false);
        txtNom.setEnabled(false);
        txtApe.setEnabled(false);
        txtCel.setEnabled(false);
        txtFna.setEnabled(false);
        txtCor.setEnabled(false);
        txtDir.setEnabled(false);
        txtBar.setEnabled(false);
        txtCiu.setEnabled(false);
        txtDep.setEnabled(false);
        txtUsu.setEnabled(false);
        txtCon.setEnabled(false);

        // Obtener los datos del usuario al hacer clic en el botón
        Button btnConsultarUsuario = view.findViewById(R.id.btnConsultarUsuario);
        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarUsuario();
            }
        });

        return view;

    }

    private void consultarUsuario() {
        // URL del archivo PHP que consulta los datos del usuario
        String URL = "http://10.0.0.37/BDRemota/wsJSONConsultarUsuario.php";

        // Crear una solicitud JSON para obtener los datos del usuario
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtener los datos del JSON
                            String ced = response.getString("ced_usu");
                            String nom = response.getString("nom_usu");
                            String ape = response.getString("ape_usu");
                            String cel = response.getString("cel_usu");
                            String fna = response.getString("fna_usu");
                            String cor = response.getString("cor_usu");
                            String dir = response.getString("dir_usu");
                            String bar = response.getString("bar_usu");
                            String ciu = response.getString("ciu_usu");
                            String dep = response.getString("dep_usu");
                            String usu = response.getString("usu_usu");
                            String con = response.getString("con_usu");

                            // Mostrar los datos en los campos de texto
                            txtCed.setText(ced);
                            txtNom.setText(nom);
                            txtApe.setText(ape);
                            txtCel.setText(cel);
                            txtFna.setText(fna);
                            txtCor.setText(cor);
                            txtDir.setText(dir);
                            txtBar.setText(bar);
                            txtCiu.setText(ciu);
                            txtDep.setText(dep);
                            txtUsu.setText(usu);
                            txtCon.setText(con);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }
}