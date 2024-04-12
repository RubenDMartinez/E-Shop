package monografia.eshop.e_shop;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "Imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    Button btnTerRegUsu, btnFot;
    EditText txtNom, txtApe, txtCed, txtNac, txtCel, txtEml;
    EditText txtBar, txtCiu, txtDep, txtDir, txtUsu, txtCon;
    ProgressDialog progreso;
    ImageView imgFoto;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFot = (Button) findViewById(R.id.btnFot);

        /*if (validarPermisos()) {
            btnFot.setEnabled(true);
        } else {
            btnFot.setEnabled(false);
        }*/

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

        btnFot = findViewById(R.id.btnFot);

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

        btnFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });
    }

    /*private boolean validarPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnFot.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }

    }*/

    /*private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"Si", "No"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(RegistrarUsuario.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
            if (opciones[i].equals("Si")) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Los permisos no fueron otorgados", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });
        alertOpciones.show();
    }*/

    /*private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(RegistrarUsuario.this);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la aplicación E-Shop en su dispositivo");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }*/


    private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(RegistrarUsuario.this);
        alertOpciones.setTitle("Elige una opción");
        alertOpciones.setItems(opciones, (DialogInterface.OnClickListener) (dialogInterface, i) -> {
            if (opciones[i].equals("Tomar foto")) {
                abrirCamara();
            } else {
                if (opciones[i].equals("Elegir de Galeria")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(Intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error al cargar la imagen de la galeria", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
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
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), miPath);
                        imgFoto.setImageBitmap(bitmap);
                    } else {
                        // El usuario canceló la selección o no se realizó con éxito.
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al procesar la imagen seleccionada", Toast.LENGTH_LONG).show();
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

    public void insertarDatosNuevoUsuario() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://10.0.0.37/BDRemota/wsJSONRegistroUsuarioFoto.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //Encargado de recibir la respuesta del WebService cuando todo está correcto
                progreso.hide();

                if (response.trim().equalsIgnoreCase("Registra")) {
                    Toast.makeText(RegistrarUsuario.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistrarUsuario.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //Encargado de procesar el inconveniente del error y procesar el mismo
                Toast.makeText(RegistrarUsuario.this, "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { //Usado para enviar los datos por medio del metodo POST

                String cedu = txtCed.getText().toString();
                String nomb = txtNom.getText().toString();
                String apel = txtApe.getText().toString();
                String celu = txtCel.getText().toString();
                String fnac = txtNac.getText().toString();
                String mail = txtEml.getText().toString();
                //String foto = convertirImgString(bitmap);
                String dire = txtDir.getText().toString();
                String barr = txtBar.getText().toString();
                String ciud = txtCiu.getText().toString();
                String depa = txtDep.getText().toString();
                String usua = txtUsu.getText().toString();
                String cont = txtCon.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("cedu", cedu);
                parametros.put("nomb", nomb);
                parametros.put("apel", apel);
                parametros.put("celu", celu);
                parametros.put("fnac", fnac);
                parametros.put("mail", mail);
                //parametros.put("foto", foto);
                parametros.put("dire", dire);
                parametros.put("barr", barr);
                parametros.put("ciud", ciud);
                parametros.put("depa", depa);
                parametros.put("usua", usua);
                parametros.put("cont", cont);

                return parametros;
            }
        };

        request.add(stringRequest);

    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = android.util.Base64.encodeToString(imagenByte, android.util.Base64.DEFAULT);

        return imagenString;
    }

}