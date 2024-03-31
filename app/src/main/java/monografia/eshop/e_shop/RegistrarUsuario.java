package monografia.eshop.e_shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    //private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    //private static final String CARPETA_IMAGEN = "Imagenes";
    //private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    //private String path;
    //File fileImagen;
    //Bitmap bitmap;

    //private static final int COD_SELECCIONA = 10;
    //private static final int COD_FOTO = 20;
    EditText txtNom, txtApe, txtCed, txtNac, txtCel, txtEml;
    EditText txtBar, txtCiu, txtDep, txtDir, txtUsu, txtCon;
    Button btnTerRegUsu, btnFot;
    ProgressDialog progreso;
    //ImageView imgFoto;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        //btnFot = findViewById(R.id.btnFot);
        //imgFoto = findViewById(R.id.imgFoto);


        txtCed = (EditText) findViewById(R.id.txtCed_Usu);
        txtNom = (EditText) findViewById(R.id.txtNom_Usu);
        txtApe = (EditText) findViewById(R.id.txtApe_Usu);
        txtCel = (EditText) findViewById(R.id.txtCel_Usu);
        txtNac = (EditText) findViewById(R.id.txtNac_Usu);
        txtEml = (EditText) findViewById(R.id.txtEml_Usu);
        txtDir = (EditText) findViewById(R.id.txtDir_Usu);
        txtBar = (EditText) findViewById(R.id.txtBar_Usu);
        txtCiu = (EditText) findViewById(R.id.txtCiu_Usu);
        txtDep = (EditText) findViewById(R.id.txtDep_Usu);
        txtUsu = (EditText) findViewById(R.id.txtUsu_Usu);
        txtCon = (EditText) findViewById(R.id.txtCon_Usu);

        btnTerRegUsu = findViewById(R.id.btnTerRegUsu);

        request = Volley.newRequestQueue(getApplicationContext());

        btnTerRegUsu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Obtiene una referencia al InputMethodManager
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Oculta el teclado
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String nomb = txtNom.getText().toString().trim();
                String apel = txtApe.getText().toString().trim();
                String cedu = txtCed.getText().toString().trim();
                String fnac = txtNac.getText().toString().trim();
                String celu = txtCel.getText().toString().trim();
                String mail = txtEml.getText().toString().trim();
                String barr = txtBar.getText().toString().trim();
                String ciud = txtCiu.getText().toString().trim();
                String depa = txtDep.getText().toString().trim();
                String dire = txtDir.getText().toString().trim();
                String usua = txtUsu.getText().toString().trim();
                String cont = txtCon.getText().toString().trim();

                if (TextUtils.isEmpty(nomb)) {
                    txtNom.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(apel)) {
                    txtApe.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(cedu)) {
                    txtCed.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(fnac)) {
                    txtNac.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(celu)) {
                    txtCel.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(mail)) {
                    txtEml.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(barr)) {
                    txtBar.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(ciud)) {
                    txtCiu.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(depa)) {
                    txtDep.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(dire)) {
                    txtDir.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(usua)) {
                    txtUsu.setError("Campo requerido");
                }
                if (TextUtils.isEmpty(cont)) {
                    txtCon.setError("Campo requerido");
                } else {
                    insertarDatosNuevoUsuario();
                }
            }

        });

        /*btnFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });*/
    }

    public void insertarDatosNuevoUsuario() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://10.0.0.37/BDRemota/wsJSONRegistroUsuario.php?txtNom=" + txtNom.getText().toString() +
                "&txtApe=" + txtApe.getText().toString() + "&txtCed=" + txtCed.getText().toString() +
                "&txtNac=" + txtNac.getText().toString() + "&txtCel=" + txtCel.getText().toString() +
                "&txtEml=" + txtEml.getText().toString() + "&txtBar=" + txtBar.getText().toString() +
                "&txtCiu=" + txtCiu.getText().toString() + "&txtDep=" + txtDep.getText().toString() +
                "&txtDir=" + txtDir.getText().toString() + "&txtUsu=" + txtUsu.getText().toString() +
                "&txtCon=" + txtCon.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    /*private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar foto", "Elegir de Galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarUsuario.this);
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")) {
                    abrirCamara();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/");
                            startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                        } catch (Exception e) {
                        }
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }*/

    /*private void abrirCamara() {
        File miFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();
        if (!isCreada) {
            isCreada = miFile.mkdirs();
        }
        if (isCreada) {
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = miFile.getAbsolutePath() + File.separator + nombre;
            fileImagen = new File(path);

            Uri fileUri = FileProvider.getUriForFile(this, "fileProvider", fileImagen);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            startActivityForResult(intent, COD_FOTO);
        }
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COD_SELECCIONA:
                try {
                    if (resultCode == AppCompatActivity.RESULT_OK) {
                        Uri miPath = data.getData();
                        imgFoto.setImageURI(miPath);
                    } else {
                        // El usuario canceló la selección o no se realizó con éxito.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al procesar la imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
                break;

            case COD_FOTO:
                MediaScannerConnection.scanFile(RegistrarUsuario.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        Log.i("Path", "" + path);
                    }
                });
                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                break;
        }
    }*/


    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response != null && response.has("status")) {
                String status = response.getString("status");

                if (status.equals("success")) {
                    // Si el registro fue exitoso, mostrar mensaje de éxito
                    Toast.makeText(RegistrarUsuario.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Iniciar_Sesion.class);
                    startActivity(intent);
                } else {
                    // Si hubo un error en el registro, mostrar mensaje de error correspondiente
                    String message = response.getString("message");
                    Toast.makeText(RegistrarUsuario.this, message, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegistrarUsuario.this, "Error en el formato de respuesta JSON", Toast.LENGTH_LONG).show();
        }

        progreso.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se pudo registrar " + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("Error", error.toString());
    }

}