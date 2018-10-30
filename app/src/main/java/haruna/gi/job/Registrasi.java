package haruna.gi.job;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haruna.gi.job.sys.AppController;
import haruna.gi.job.sys.List_bidang;
import mehdi.sakout.fancybuttons.FancyButton;

public class Registrasi extends AppCompatActivity {
    private static final String[] strata = new String[]{
            "None", "Callisto", "Ganymede", "Luna"
    };
    private boolean[] clicked_colors;
    private String[] COLORS;

    private ProgressDialog pDialog;
    int success;
    ConnectivityManager conMgr;
    private String url ="http://192.168.137.1/job/register.php";
    private static final String TAG = Login.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_EMAIL = "email";
    public final static String TAG_ID = "id";

    String tag_json_obj = "json_obj_req";
    List<List_bidang> itemList = new ArrayList<>();

    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    FancyButton keahlian,register;
    MaterialEditText email,password,nama,gaji,alamat,notelp;
    String semail,spassword,snama,sgaji,salamat,snotelp;
    String spendidikan;
    RadioGroup rg;

    TextView txtSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi);
        keahlian = findViewById(R.id.keahlian);
        register = findViewById(R.id.daftar);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nama = findViewById(R.id.nama);
        gaji = findViewById(R.id.gaji);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        txtSelected = findViewById(R.id.txt);
        callVolley();

        RadioGroup rg = (RadioGroup) findViewById(R.id.rbt);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sd:
                        spendidikan = "sd";
                        break;
                    case R.id.smp:
                        spendidikan = "sd";
                        break;
                    case R.id.sma:
                        spendidikan = "sma";
                        break;
                    case R.id.d3:
                        spendidikan = "d3";
                        break;
                    case R.id.s1:
                        spendidikan = "s1";
                        break;
                    case R.id.s2:
                        spendidikan = "s2";
                        break;
                    case R.id.s3:
                        spendidikan = "s3";
                        break;
                }
                Toast.makeText(getApplicationContext(),spendidikan,Toast.LENGTH_SHORT).show();
            }
        });


        keahlian.setOnClickListener((View view)->{

          showMultiChoiceDialog();
        });

        register.setOnClickListener((View view)->{
            semail = email.getText().toString();
            snama = nama.getText().toString();
            spassword = password.getText().toString();
            sgaji = gaji.getText().toString();
            salamat = alamat.getText().toString();
            snotelp = notelp.getText().toString();
            checkRegister(semail,spassword,snama,salamat,snotelp,spendidikan,sgaji);
        });
    }

    private String single_choice_selected;
    private void showMultiChoiceDialog() {
        final ArrayList<ColorVO> colorList = new ArrayList<ColorVO>();
        txtSelected.setText("");
        colorList.clear();
        for (int ni = 0; ni < COLORS.length; ni++) {
            ColorVO colorVO = new ColorVO();
            colorVO.setName(COLORS[ni]);
            colorVO.setSelected(clicked_colors[ni]);
            colorList.add(colorVO);
        }
        clicked_colors = new boolean[COLORS.length];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih beberapa keahlian anda");
        builder.setMultiChoiceItems(COLORS, clicked_colors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int idd, boolean b) {
                colorList.get(idd).setSelected(b);
                Toast.makeText(getApplicationContext(),String.valueOf(idd),Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<ColorVO> selectedList = new ArrayList<>();
                for (int ji = 0; ji < colorList.size(); ji++) {
                    ColorVO colorVO = colorList.get(ji);
                    COLORS[ji] = colorVO.getAlamat_pe();
                    clicked_colors[ji] = colorVO.isSelected();
                    if (colorVO.isSelected()) {
                        selectedList.add(colorVO);
                    }
                }
                for (int ih = 0; ih < selectedList.size(); ih++) {
                    // if element is last then not attach comma or attach it
                    if (ih != selectedList.size() - 1)
                        txtSelected.setText(txtSelected.getText() + selectedList.get(ih).getAlamat_pe() + " ,");
                    else
                        txtSelected.setText(txtSelected.getText() + selectedList.get(ih).getAlamat_pe());
                }
                selectedList.clear();
            }
        });
        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void checkRegister(final String email, final String password,
                               final String nama,
                               final String alamat,
                               final String notelp,
                               final String pendidikan,
                               final String gaji) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("notelp", notelp);
                params.put("pendidikan",pendidikan);//
                params.put("gaji", gaji);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void callVolley(){

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest("http://192.168.137.1/job/list_bidang.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                COLORS = new String[response.length()];
                clicked_colors = new boolean[response.length()];
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        List_bidang item = new List_bidang();

                        item.setId(obj.getString("id_bidang"));
                        item.setNama(obj.getString("urai"));

                        // menambah item ke array
                        itemList.add(item);
                        COLORS[i]=obj.getString("urai");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                    Log.e("errrrr",String.valueOf(itemList.get(3)));

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Registrasi.this, Login.class));
    }

    private class ColorVO {
        private String name;
        private boolean selected;

        public String getAlamat_pe() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}

