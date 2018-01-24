package com.igor.androidstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText message,filename;
    Button read,write,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText)findViewById(R.id.editTextMessage);
        filename = (EditText)findViewById(R.id.editTextFileName);

        read = (Button)findViewById(R.id.btnRead);
        write = (Button)findViewById(R.id.btnWrite);
        view = (Button)findViewById(R.id.btvView);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean external = isExternalStorageWritable();
                if (external == true){
                    Toast.makeText(getBaseContext(),"Externel storage is available",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getBaseContext(),"not available",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
