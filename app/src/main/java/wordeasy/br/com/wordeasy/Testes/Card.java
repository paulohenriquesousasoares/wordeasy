package wordeasy.br.com.wordeasy.Testes;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import wordeasy.br.com.wordeasy.R;

/**
 * Created by paulo on 27/12/2015.
 */
public class Card extends AppCompatActivity {

//    ImageView imgFront;
//    ImageView imgBack;
    View viewFront;
    View viewBack;

    Button btnFlip;

    boolean isBackVisible = false; // Boolean variable to check if the back image is visible currently

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudar);


        viewFront = findViewById(R.id.rl_card_front);
        viewBack = findViewById(R.id.rl_card_back);
        btnFlip = (Button)findViewById(R.id.btnFlip);

        final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flight_right_out);
        final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flight_left_in);


        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isBackVisible){
                    setRightOut.setTarget(viewFront);
                    setLeftIn.setTarget(viewBack);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = true;
                }
                else{
                    setRightOut.setTarget(viewBack);
                    setLeftIn.setTarget(viewFront);
                    setRightOut.start();
                    setLeftIn.start();
                    isBackVisible = false;
                }

            }
        });

    }
}
