package edu.miami.c11926684.miniapp9;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.LayoutInflater;

public class dialogActivity extends DialogFragment {

    private final String[] forList = { "Pikachu", "Squirtle", "Charmander", "Ditto"};

    int whichDialog;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("made it");


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        whichDialog = this.getArguments().getInt("dialog_type");

        switch (whichDialog) {
            case MiniApp9_MainActivity.LIST_DIALOG:

                dialogBuilder.setTitle("Pick a pokemon");
                dialogBuilder.setItems(forList,listListener);
                break;

            default:
                return(null);
        }
        return (dialogBuilder.create());
    }
    //-----------------------------------------------------------------------------
    @Override
    public void onResume() {

        super.onResume();
        switch (whichDialog) {

            default:
                break;
        }
    }
    //-----------------------------------------------------------------------------
    private DialogInterface.OnClickListener listListener =
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int indexClicked) {

                    String[] pokemon;

                    pokemon = forList;
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You chose " + pokemon[indexClicked],Toast.LENGTH_SHORT).show();
                    openDialogueWithImage = indexClicked;
                    sideQueue.run();
                }
            };

    int openDialogueWithImage;
    private Runnable sideQueue = new Runnable() {

        Handler handler = new Handler();
        @Override
        public void run() {
            Bundle bundleToFragment = new Bundle();
            bundleToFragment.putInt("dialog_type",MiniApp9_MainActivity.OTHER_DIALOG);
            bundleToFragment.putInt("which_pokemon", openDialogueWithImage);
            CustomDialog theDialogFragment = new CustomDialog();
            System.out.println("made it");
            theDialogFragment.setArguments(bundleToFragment);
            System.out.println("made it");
            theDialogFragment.show(getFragmentManager(),"my_cusstom_fragment");
        }
    };
}
