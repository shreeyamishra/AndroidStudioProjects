package edu.miami.c11926684.miniapp8;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MiniApp8_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app8__main);
    }

    public void myClickHandler(View view) {

        FirstFragment firstFragment;
        SecondFragment secondFragment;
        FragmentManager fragmentManager;

        switch (view.getId()) {
//            case R.id.first_fragment:
//                fragmentManager = getFragmentManager();
//                firstFragment = (FirstFragment)fragmentManager.
//                        findFragmentById(R.id.first_fragment);
//                System.out.println("frag1 " + R.id.first_fragment);
//                break;
//            case R.id.second_fragment:
//                secondFragment = (SecondFragment) findViewById(R.id.second_fragment);
//                System.out.println("frag2 " + R.id.second_fragment);
//                break;
            case R.id.button1:
                System.out.println("f1 Button 1");
                Toast.makeText(this,"f1 Button 1", Toast.LENGTH_LONG).
                        show();
                break;
            case R.id.button2:
                System.out.println("f1 Button 2");
                Toast.makeText(this,"f1 Button 2", Toast.LENGTH_LONG).
                        show();
                break;
            case R.id.button21:
                System.out.println("f2 Button 1");
                Toast.makeText(this,"f2 Button 1", Toast.LENGTH_LONG).
                        show();
                break;
            default:
                break;
        }
    }
}
