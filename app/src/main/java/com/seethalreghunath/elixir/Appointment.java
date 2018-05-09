package com.seethalreghunath.elixir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Appointment extends AppCompatActivity {

    private Button back;
    private Button add;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_name);

        back=(Button) findViewById(R.id.back);
        add=(Button) findViewById(R.id.add);
        t=(TextView) findViewById(R.id.textView);

        t.setText("Appointment 1");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appointment.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Appointment.this, AppointmentFeedback.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
