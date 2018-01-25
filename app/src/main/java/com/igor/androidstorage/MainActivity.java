package com.igor.androidstorage;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
                    final CharSequence[] items = {"External storage","Internal storage","Cancel"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Storage type");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (items[which].equals("External storage")){
                                Toast.makeText(getBaseContext(),"External storage",Toast.LENGTH_SHORT).show();
                            }else if (items[which].equals("Internal storage")){
                                writeInternalStorage();
                               // Toast.makeText(getBaseContext(),"Internal storage",Toast.LENGTH_SHORT).show();
                            }else if (items[which].equals("Cancel")){
                                dialog.dismiss();
                            }

                        }
                    });
                    builder.show();
                   // Toast.makeText(getBaseContext(),"External storage is available",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getBaseContext(),"External storage not available",Toast.LENGTH_SHORT).show();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readInternalStorage();
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
    void createExternalStoragePrivateFile() {
        // Create a path where we will place our private file on external
        // storage.
        File file = new File(getExternalFilesDir(null), "DemoFile.txt");
    }
    public void writeInternalStorage(){
        {
            // add-write text into file
            try {
                FileOutputStream fileout = openFileOutput(filename.getText().toString() +".txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(message.getText().toString());
                outputWriter.close();

                //display file saved message
                Toast.makeText(getBaseContext(), "File saved successfully!",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void readInternalStorage(){
        {
            //reading text from file
            try {
                FileInputStream fileIn = openFileInput(filename.getText().toString() +".txt");
                InputStreamReader InputRead= new InputStreamReader(fileIn);

                char[] inputBuffer= new char[100];
                String s="";
                int charRead;

                while ((charRead=InputRead.read(inputBuffer))>0) {
                    // char to string conversion
                    String readstring=String.copyValueOf(inputBuffer,0,charRead);
                    s +=readstring;
                }
                InputRead.close();
                message.setText(s);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
