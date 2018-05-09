package com.seethalreghunath.elixir;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AppointmentFeedback extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    public static final String PHONENUMBER = "phonenumber";

    private Button echoosefile;
    private Button eupload;
    private EditText edetails;
    private ImageView ephoto;
    private Uri eimageuri;
    private String phonenum;
    private Button back;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private StorageTask euploadtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_feedback);

        echoosefile=(Button) findViewById(R.id.choose_file);
        eupload=(Button) findViewById(R.id.upload);
        edetails=(EditText) findViewById(R.id.details);
        back=(Button) findViewById(R.id.back);
        ephoto= findViewById(R.id.photo);


        mStorageReference= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        phonenum = preferences.getString(PHONENUMBER, "");
        phonenum = "++91" + phonenum;
        echoosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        eupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(euploadtask !=null && euploadtask.isInProgress()){
                    Toast.makeText(AppointmentFeedback.this, "Upload in progress...", Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppointmentFeedback.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null && data.getData() != null){
            eimageuri=data.getData();
            Picasso.with(this).load(eimageuri).into(ephoto);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if(eimageuri!= null){
            StorageReference fileReference=mStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(eimageuri));
            euploadtask=fileReference.putFile(eimageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         //   mProgressBar.setProgress(0);
                        }
                    },5000);

                    Toast.makeText(AppointmentFeedback.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    Upload upload=new Upload(edetails.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                    String uploadid=mDatabaseReference.push().getKey();
                    mDatabaseReference.child(phonenum).child("uappointment_feedback").setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AppointmentFeedback.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress=(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//
//                        }
//                    });

            Intent intent=new Intent(AppointmentFeedback.this, Appointment.class);
            finish();
            startActivity(intent);
        }else{
            Toast.makeText(this, "No file selected....", Toast.LENGTH_SHORT).show();

        }
    }
}


