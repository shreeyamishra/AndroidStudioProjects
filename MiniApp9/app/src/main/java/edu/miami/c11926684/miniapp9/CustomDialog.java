package edu.miami.c11926684.miniapp9;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by woodyjean-louis on 10/17/16.
 */

public class CustomDialog extends DialogFragment {
    private final int[] POKEMON = { R.drawable.pikachu, R.drawable.squirtle, R.drawable.charmander, R.drawable.ditto };

    View dialogView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Here's your pokemon");
        dialogView = inflater.inflate(R.layout.activity_dialog, container);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image);
        imageView.setImageResource(POKEMON[this.getArguments().getInt("which_pokemon")]);
        ((Button)dialogView.findViewById(R.id.dismiss)).
                setOnClickListener(myClickHandler);
        return(dialogView);
    }

    private View.OnClickListener myClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.dismiss:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
