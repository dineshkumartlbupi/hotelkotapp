package com.saptrishi.hotelkotapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saptrishi.hotelkotapp.R;

public class DisplayTableActivity extends AppCompatActivity implements View.OnClickListener {

    Button but1,but2, but3,but4, but5,but6, but7,but8, but9,but10, but11,but12;
   TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_table);

   textView = (TextView) findViewById(R.id.back);

   textView.setOnClickListener( new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           onBackPressed();
       }
   } );


        but1= (Button) findViewById(R.id.but1);
        but2= (Button) findViewById(R.id.but2);
        but3= (Button) findViewById(R.id.but3);
        but4= (Button) findViewById(R.id.but4);

        but5= (Button) findViewById(R.id.but5);
        but6= (Button) findViewById(R.id.but6);
        but7= (Button) findViewById(R.id.but7);
        but8= (Button) findViewById(R.id.but8);

        but9= (Button) findViewById(R.id.but9);
        but10= (Button) findViewById(R.id.but10);
        but11= (Button) findViewById(R.id.but11);
        clickint();




    }

    private  void clickint()
    {
        but1.setOnClickListener( this);
        but2.setOnClickListener( this);
        but3.setOnClickListener( this);
        but4.setOnClickListener( this);
        but5.setOnClickListener( this);
    }


    @Override
    public void onClick(View v )
    {
        if(v==but1)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "Log Out Successfull.", Toast.LENGTH_LONG ).show ();
        }

        if(v==but2)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }

        if(v==but3)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }

        if(v==but4)
        {

            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but5)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but6)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but7)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but8)
        {
            //startActivity ( new Intent ( MainActivity.this, LoginActivity.class ) );
            Toast.makeText ( DisplayTableActivity.this, "Billed.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but9)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }

        if(v==but10)
        {
            startActivity ( new Intent( DisplayTableActivity.this, ECartHomeActivity.class ) );
            //Toast.makeText ( DisplayTableActivity.this, "but2.", Toast.LENGTH_LONG ).show ();
        }
        if(v==but11)
        {
            //startActivity ( new Intent ( MainActivity.this, LoginActivity.class ) );
            Toast.makeText ( DisplayTableActivity.this, "This Table Already Occupied.", Toast.LENGTH_LONG ).show ();
        }

    }

}
