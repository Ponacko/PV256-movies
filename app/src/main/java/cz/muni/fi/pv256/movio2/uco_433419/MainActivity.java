package cz.muni.fi.pv256.movio2.uco_433419;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.activateStrictMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}