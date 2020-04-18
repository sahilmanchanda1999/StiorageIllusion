package android.k17bn.stiorageillusion;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Internal extends AppCompatActivity {

    EditText enterText;
    TextView showText;
    private  String file="myFile";
    private  String fileContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_internal);

        enterText=findViewById(R.id.readetext);
        showText=findViewById(R.id.readtext);
        findViewById(R.id.Writebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileContents=enterText.getText().toString();
                try{
                    FileOutputStream fOut=openFileOutput(file,MODE_PRIVATE);
                    fOut.write(fileContents.getBytes());
                    fOut.close();
                    File fileDir=new File(getFilesDir(),file);
                    Toast.makeText(Internal.this, "File Saved at "+fileDir, Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.Readbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileInputStream fIn=openFileInput(file);
                    int c;
                    String temp="";
                    while((c=fIn.read())!=-1){
                        temp=temp + (char) c;
                    }
                    showText.setText(temp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
