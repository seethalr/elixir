package com.seethalreghunath.elixir;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicationCourse extends Activity {

    public static final String MEDICINENAME= "medicinename";
    public static final String IVALUE= "ivalue";
    public static final String PHONENUMBER = "phonenumber";
    final ArrayList<String> medicinesname=new ArrayList<String>();
    private String snap;
    private Button eadd;
    private Button eback;
    private ListView simpleList;
    private int j;
    private String name;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    private TextView t1;
//    private TextView t2;
//    private TextView t3;
//    private TextView t4;
//    private TextView t5;

//    public void f1(){
//        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_listview,R.id.medlist,medicinesname);
//        simpleList.setAdapter(adapter);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.med_course);

        eadd=(Button) findViewById(R.id.add);
        eback=(Button) findViewById(R.id.back);
        t1=(TextView) findViewById(R.id.textView1);
//        t2=(TextView) findViewById(R.id.textView2);
//        t3=(TextView) findViewById(R.id.textView3);
//        t4=(TextView) findViewById(R.id.textView4);
//        t5=(TextView) findViewById(R.id.textView5);





        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int i=preferences.getInt(IVALUE, -1);
        String phonenum = preferences.getString(PHONENUMBER, "");
        final String k  = "++91" + phonenum;
        String t="umedicationcourses";
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mDatabase.getReference();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (j = 0; j < i; j++) {

                    snap = (String) dataSnapshot.child(k).child("umedicationcourses").child("umedicationcourse" + (j + 1)).child("medicine").getValue();
                    medicinesname.add(snap);
                    Log.i("data", "value: " + medicinesname.get(j)+j);
                    t1.setText(medicinesname.get(0));



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        if(i==1){
//            t1.setText(medicinesname.get(0));
//            t1.setVisibility(View.VISIBLE);
//        }
//        else if(i==2){
//            t1.setText(medicinesname.get(0));
//            t1.setVisibility(View.VISIBLE);
//            t2.setText(medicinesname.get(1));
//            t2.setVisibility(View.VISIBLE);
//        }
//        else if(i==3){
//            t1.setText(medicinesname.get(0));
//            t1.setVisibility(View.VISIBLE);
//            t2.setText(medicinesname.get(1));
//            t2.setVisibility(View.VISIBLE);
//            t3.setText(medicinesname.get(2));
//            t3.setVisibility(View.VISIBLE);
//        }else if(i==4){
//            t1.setText(medicinesname.get(0));
//            t1.setVisibility(View.VISIBLE);
//            t2.setText(medicinesname.get(1));
//            t2.setVisibility(View.VISIBLE);
//            t3.setText(medicinesname.get(2));
//            t3.setVisibility(View.VISIBLE);
//            t4.setText(medicinesname.get(3));
//            t4.setVisibility(View.VISIBLE);
//
//        }else{
//            t5.setText(medicinesname.get(5));
//            t5.setVisibility(View.VISIBLE);
//            t1.setText(medicinesname.get(0));
//            t1.setVisibility(View.VISIBLE);
//            t2.setText(medicinesname.get(1));
//            t2.setVisibility(View.VISIBLE);
//            t3.setText(medicinesname.get(2));
//            t3.setVisibility(View.VISIBLE);
//            t4.setText(medicinesname.get(3));
//            t4.setVisibility(View.VISIBLE);
//        }
//        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_listview,R.id.medlist,medicinesname);
//        simpleList.setAdapter(adapter);

        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MedicationCourse.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });
        eadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MedicationCourse.this, Addcourse.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
