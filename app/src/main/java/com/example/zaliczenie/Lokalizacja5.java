package com.example.zaliczenie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class Lokalizacja5 extends AppCompatActivity {

    //screen
    Button goBackButton;
    TextView latTextView;
    TextView lonTextView;
    TextView infoTextView;

    //baza danych
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    //autentykacja
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokalizacja5);

        //screen
        goBackButton = findViewById(R.id.goBackButton5);
        latTextView = findViewById(R.id.latTextView5);
        lonTextView = findViewById(R.id.lonTextView4);
        infoTextView = findViewById(R.id.infoTextView5);

        //obsluga przyciskow
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //zmiana tekstow
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = database.getReference(userId);

        databaseReference.child("lat5").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && !String.valueOf(task.getResult().getValue()).equals("null"))
                {
                    String latGeo = String.valueOf(task.getResult().getValue());
                    databaseReference.child("long5").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful() && !String.valueOf(task.getResult().getValue()).equals("null"))
                            {
                                String lonGeo = String.valueOf(task.getResult().getValue());

                                String info = "Brak danych";
                                Geocoder geocoder = new Geocoder(Lokalizacja5.this, Locale.getDefault());
                                try {
                                    List<Address> addressList = geocoder.getFromLocation( Double.parseDouble(latGeo)
                                            ,Double.parseDouble(lonGeo),1);
                                    if (addressList != null && addressList.size() > 0)
                                    {
                                        info = "Informacje: \n";

                                        if(addressList.get(0).getLocality() != null)
                                            info += "Miasto: " + addressList.get(0).getLocality() + "\n";

                                        if(addressList.get(0).getThoroughfare() != null)
                                        {
                                            if (addressList.get(0).getFeatureName()!= null)
                                            {
                                                if (addressList.get(0).getThoroughfare().equals(addressList.get(0).getFeatureName()))
                                                {
                                                    info += "Ulica: " + addressList.get(0).getThoroughfare() + "\n";
                                                }
                                                else
                                                {
                                                    info += "Ulica: " + addressList.get(0).getThoroughfare()
                                                            + " " + addressList.get(0).getFeatureName()+ "\n";
                                                }
                                            }
                                            else
                                            {
                                                info += "Ulica: " + addressList.get(0).getThoroughfare() + "\n";
                                            }
                                        }

                                        if(addressList.get(0).getPostalCode() != null)
                                            info += "Kod pocztowy: " + addressList.get(0).getPostalCode() + "\n";

                                        if(addressList.get(0).getAdminArea() != null)
                                            info += "Województwo: " + addressList.get(0).getAdminArea() + "\n";

                                        if(addressList.get(0).getCountryName() != null)
                                            info += "Kraj: " + addressList.get(0).getCountryName() + "\n";

                                        if(addressList.get(0).getExtras() != null)
                                            info += addressList.get(0).getExtras() + "\n";
                                    }
                                    //if (Geocoder.isPresent())
                                    //{
                                    //    Toast.makeText(Lokalizacja5.this, "działa", Toast.LENGTH_SHORT).show();
                                    //}
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                infoTextView.setText(info);
                            }
                            else
                            {
                                Toast.makeText(Lokalizacja5.this, "Najpierw musisz zapisać swoją lokalizację!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Lokalizacja5.this, "Najpierw musisz zapisać swoją lokalizację!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        databaseReference.child("lat5").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Lokalizacja5.this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (String.valueOf(task.getResult().getValue()).equals("null"))
                    {
                        Toast.makeText(Lokalizacja5.this,"Musisz najpierw zapisać swoją lokalizację!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String lat = String.valueOf(task.getResult().getValue());
                        Number test = Double.parseDouble(lat);

                        DecimalFormat df = new DecimalFormat("#.###");
                        df.setRoundingMode(RoundingMode.CEILING);
                        double d = test.doubleValue() + 1e-6;
                        latTextView.setText("Szerokość geograficzna: " + df.format(d));
                    }
                }
            }
        });

        databaseReference.child("long5").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Lokalizacja5.this, "Wystąpił błąd", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (String.valueOf(task.getResult().getValue()).equals("null"))
                    {
                        Toast.makeText(Lokalizacja5.this,"Musisz najpierw zapisać swoją lokalizację!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String lon = String.valueOf(task.getResult().getValue());
                        Number test = Double.parseDouble(lon);

                        DecimalFormat df = new DecimalFormat("#.###");
                        df.setRoundingMode(RoundingMode.CEILING);
                        double d = test.doubleValue() + 1e-6;
                        lonTextView.setText("Wysokość geograficzna: " + df.format(d));
                    }
                }
            }
        });
    }

    //metody
    public void goBack()
    {
        Intent i = new Intent(this,PoZalogowaniu.class);
        startActivity(i);
    }
}