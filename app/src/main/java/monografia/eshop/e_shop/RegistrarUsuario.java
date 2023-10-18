package monografia.eshop.e_shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "Imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    EditText camp1, camp2, camp3, camp4, camp5, camp6, camp7, camp8, camp9, camp10, camp11, camp12;
    Button btnTerRegUsu, btnFot;
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
        btnTerRegUsu = (Button) findViewById(R.id.btnTerRegUsu);
        btnFot = (Button) findViewById(R.id.btnFot);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);

        request = Volley.newRequestQueue(getApplicationContext());

        btnTerRegUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatosNuevoUsuario();
            }
        });

        btnFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });
    }

    public void insertarDatosNuevoUsuario() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://192.168.1.8/conexionEShop/RegistrarUsuario.php?" +
                "cedu=" + camp3.getText().toString() + "&nomb=" + camp1.getText().toString() +
                "&apel=" + camp2.getText().toString() + "&ncel=" + camp5.getText().toString() +
                "&fnac=" + camp4.getText().toString() + "&emai=" + camp6.getText().toString() +
                "&dire=" + camp10.getText().toString() + "&barr=" + camp7.getText().toString() +
                "&ciud=" + camp8.getText().toString() + "&depa=" + camp9.getText().toString() +
                "&usua=" + camp11.getText().toString() + "&cont=" + camp12.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void mostrarDialogOpciones() {
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
    }

    private void abrirCamara() {
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
    }

    @Override
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
    }

    public void onResponse(JSONObject response) {
        Toast.makeText(RegistrarUsuario.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
        progreso.hide();
        /*
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
        */
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se pudo registrar " + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("Error", error.toString());
    }

}
