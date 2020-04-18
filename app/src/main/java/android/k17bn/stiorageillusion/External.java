package android.k17bn.stiorageillusion;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class External extends AppCompatActivity {

    Dialog dialog;
    ImageView popclose;
    Button posbtn;
    TextView postext;

    EditText ereg,ename,eEmail,ephone,estream;
    private String file="myNewFile.txt";
    private String path="MyFileStorage";
    FileOutputStream fstream;
    FileInputStream fileInputStream;
    File myExFile;
    String[] mPermission = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_external);

        dialog=new Dialog(this);

        ereg= findViewById(R.id.regedit);
        ename= findViewById(R.id.nameedit);
        eEmail= findViewById(R.id.emailedit);
        ephone= findViewById(R.id.phoneedit);
        estream= findViewById(R.id.streamedit);

        findViewById(R.id.savebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reg = ereg.getText().toString() + "\n";
                String name = ename.getText().toString() + "\n";
                String email = eEmail.getText().toString() + "\n";
                String phone = ephone.getText().toString() + "\n";
                String stream = estream.getText().toString();

                try {
                    if ((ContextCompat.checkSelfPermission(External.this, mPermission[0]) != PackageManager.PERMISSION_GRANTED)
                            || (ContextCompat.checkSelfPermission(External.this, mPermission[1]) != PackageManager.PERMISSION_GRANTED)) {

                        ActivityCompat.requestPermissions(External.this, mPermission, 23);
                    } else {
                        if (reg.isEmpty())
                            ereg.setError("Required");
                        if (name.isEmpty())
                            ename.setError("Required");
                        if (email.isEmpty())
                            eEmail.setError("Required");
                        if (phone.isEmpty())
                            ephone.setError("Required");
                        if (stream.isEmpty())
                            estream.setError("Required");
                        if (!reg.isEmpty() && !name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !stream.isEmpty()) {


                            myExFile = new File(getExternalFilesDir(path), file);
                            fstream = new FileOutputStream(myExFile);
                            fstream.write(reg.getBytes());
                            fstream.write(name.getBytes());
                            fstream.write(email.getBytes());
                            fstream.write(phone.getBytes());
                            fstream.write(stream.getBytes());
                            fstream.close();
                            Toast.makeText(getApplicationContext(), "Details Saved in " + myExFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        });
        
        findViewById(R.id.showbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myExFile = new File(getExternalFilesDir(path), file);
                    fileInputStream = new FileInputStream(myExFile);
                    StringBuffer buffer = new StringBuffer();
                    int c;
                    while ((c = fileInputStream.read())!= -1){
                        buffer.append((char)c);
                    }
                    fileInputStream.close();
                    String details[] = buffer.toString().split("\n");
                    ShowDetails(details[0],details[1],details[2],details[3],details[4]);
                    //result.setText("Name: "+ details[0]+"\nPassword: "+details[1]);

                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        
        

    }

    private void ShowDetails(String reg, String name, String email, String phone, String stream) {

        dialog.setContentView(R.layout.popup_pos);
        popclose=dialog.findViewById(R.id.posclose);
        posbtn=dialog.findViewById(R.id.posbtn);
        posbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        postext=dialog.findViewById(R.id.postext);
        postext.setText("Reg: "+reg+"\n"+"Name: "+name+"\n"+"Email: "+email+"\n"+
                "Phone: "+phone+"\n"+"Stream: "+stream+"\n");
        popclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
