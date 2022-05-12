package com.example.zaliczenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PoZalogowaniu extends AppCompatActivity {

    //autentykacja
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    //baza danych
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    //lokalizacja
    LocationManager locationManager;
    LocationListener locationListener;
    //lokalizacja
    double aktualnaSzerokosc;
    double aktualnaWysokosc;

    //screen
    private Button logoutButton;

    private Button showButton1;
    private Button showButton2;
    private Button showButton3;
    private Button showButton4;
    private Button showButton5;

    private Button saveButton1;
    private Button saveButton2;
    private Button saveButton3;
    private Button saveButton4;
    private Button saveButton5;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_zalogowaniu);

        //baza danych
        database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //screen
        logoutButton = findViewById(R.id.logoutButton);

        showButton1 = findViewById(R.id.showButton1);
        showButton2 = findViewById(R.id.showButton2);
        showButton3 = findViewById(R.id.showButton3);
        showButton4 = findViewById(R.id.showButton4);
        showButton5 = findViewById(R.id.showButton5);

        saveButton1 = findViewById(R.id.saveButton1);
        saveButton2 = findViewById(R.id.saveButton2);
        saveButton3 = findViewById(R.id.saveButton3);
        saveButton4 = findViewById(R.id.saveButton4);
        saveButton5 = findViewById(R.id.saveButton5);



        //autentykacja
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser==null)
                {
                    przejdzDoMainActivity();
                }
            }
        };

        //lokalizacja
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                aktualnaSzerokosc = location.getLongitude();
                aktualnaWysokosc = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };


        //obsluga przyciskow
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        //pokazywanie
        showButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show1();
            }
        });
        showButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show2();
            }
        });
        showButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show3();
            }
        });
        showButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show4();
            }
        });
        showButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show5();
            }
        });

        //zapisywanie
        saveButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(userId);
                databaseReference.child("lat1").setValue(aktualnaWysokosc);
                databaseReference.child("long1").setValue(aktualnaSzerokosc);
                Toast.makeText(PoZalogowaniu.this, "Zapisano lokalizację!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(userId);
                databaseReference.child("lat2").setValue(aktualnaWysokosc);
                databaseReference.child("long2").setValue(aktualnaSzerokosc);
                Toast.makeText(PoZalogowaniu.this, "Zapisano lokalizację!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(userId);
                databaseReference.child("lat3").setValue(aktualnaWysokosc);
                databaseReference.child("long3").setValue(aktualnaSzerokosc);
                Toast.makeText(PoZalogowaniu.this, "Zapisano lokalizację!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(userId);
                databaseReference.child("lat4").setValue(aktualnaWysokosc);
                databaseReference.child("long4").setValue(aktualnaSzerokosc);
                Toast.makeText(PoZalogowaniu.this, "Zapisano lokalizację!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference(userId);
                databaseReference.child("lat5").setValue(aktualnaWysokosc);
                databaseReference.child("long5").setValue(aktualnaSzerokosc);
                Toast.makeText(PoZalogowaniu.this, "Zapisano lokalizację!", Toast.LENGTH_SHORT).show();
            }
        });


        //ostatnia znana lokalizacja
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,0,locationListener);
            Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnowLocation != null)
            {
                aktualnaSzerokosc = (lastKnowLocation.getLatitude());
                aktualnaWysokosc = (lastKnowLocation.getLongitude());
            }
        }
    }
    //uprawnienia do lokalizacji
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startListening();
        }
    }

    //nasluchiwanie lokalizacji
    public void startListening()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,0,locationListener);
    }

    //metody

    //przejscie do activity MainActivity
    public void przejdzDoMainActivity()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void show1()
    {
        Intent i = new Intent(this,Lokalizacja1.class);
        startActivity(i);
    }
    public void show2()
    {
        Intent i = new Intent(this,Lokalizacja2.class);
        startActivity(i);
    }
    public void show3()
    {
        Intent i = new Intent(this,Lokalizacja3.class);
        startActivity(i);
    }
    public void show4()
    {
        Intent i = new Intent(this,Lokalizacja4.class);
        startActivity(i);
    }
    public void show5()
    {
        Intent i = new Intent(this,Lokalizacja5.class);
        startActivity(i);
    }
}
























