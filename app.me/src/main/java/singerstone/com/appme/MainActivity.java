package singerstone.com.appme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import singerstone.com.libbase.Test;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test.test();
        testmerge();
    }

    private void testmerge() {
        Log.i("123", "123");
    }

}
