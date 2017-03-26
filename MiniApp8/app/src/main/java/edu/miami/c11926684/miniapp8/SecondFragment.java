package edu.miami.c11926684.miniapp8;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by woodyjean-louis on 10/11/16.
 */

public class SecondFragment extends Fragment {

    //-----------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return(inflater.inflate(R.layout.fragment2,
                container,false));
    }

    private View.OnClickListener clickHandler = new View.OnClickListener() {

        public void onClick(View view) {
            Toast.makeText(getActivity(),"No clicks allowed!", Toast.LENGTH_LONG).
                    show();
        }
    };
//-----------------------------------------------------------------------------

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
                break;
            case R.id.button2:
                System.out.println("f1 Button 2");
                break;
            case R.id.button21:
                System.out.println("f2 Button 1");
                Toast.makeText(getActivity(),"f2 Button 1", Toast.LENGTH_LONG).
                        show();
            default:
                break;
        }
    }
}
