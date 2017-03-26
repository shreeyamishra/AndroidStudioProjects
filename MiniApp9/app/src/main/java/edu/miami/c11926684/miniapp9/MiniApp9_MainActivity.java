package edu.miami.c11926684.miniapp9;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MiniApp9_MainActivity extends AppCompatActivity {

    public static final int LIST_DIALOG = 1;
    public static final int OTHER_DIALOG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app9__main);
    }

    public void myClickHandler(View view) {

        System.out.println("made it");

        dialogActivity theDialogFragment;
        Bundle bundleToFragment = new Bundle();
        bundleToFragment.putInt("dialog_type",LIST_DIALOG);
        theDialogFragment = new dialogActivity();
        System.out.println("made it");
        theDialogFragment.setArguments(bundleToFragment);
        System.out.println("made it");
        theDialogFragment.show(getFragmentManager(),"my_fragment");
    }
}
