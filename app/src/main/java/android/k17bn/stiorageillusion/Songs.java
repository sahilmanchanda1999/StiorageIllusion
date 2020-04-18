package android.k17bn.stiorageillusion;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class Songs extends AppCompatActivity {

    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter adapter;
    String[] mPermission = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);


        if ((ContextCompat.checkSelfPermission(Songs.this, mPermission[0]) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(Songs.this, mPermission[1]) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(Songs.this, mPermission, 23);
        }
        else {
            Stuff();
        }

    }

    public void Stuff() {
        listView=findViewById(R.id.listView);
        arrayList=new ArrayList<>();
        getMusic();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                textView.setTextColor(Color.WHITE);

                return textView;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Songs.this, "Song is Playing", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getMusic(){
        ContentResolver contentResolver=getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=contentResolver.query(songUri,null,null,null,null);

        if (songCursor!=null&&songCursor.moveToFirst()){
            int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do{
                String currentTitle=songCursor.getString(songTitle);
                String currentArtist=songCursor.getString(songArtist);
                arrayList.add("Title:" +currentTitle+"\n"+"Artist: "+currentArtist+"\n"+"Location: "
                +songLocation+"\n");

            }while (songCursor.moveToNext());
        }

    }

}
