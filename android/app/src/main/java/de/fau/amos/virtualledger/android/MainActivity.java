package de.fau.amos.virtualledger.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.Restapi;
import de.fau.amos.virtualledger.android.dagger.UserCredential;
import de.fau.amos.virtualledger.android.dagger.component.NetComponent;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ((App) getApplication()).getNetComponent().inject(this);

        Button button_register = (Button) findViewById(R.id.button_register);

        button_register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(R.layout.register_succ);
                    }
                }
        );

        //Create textview and findViewByID
        //textView = (TextView) findViewById(R.id.textView2);
        // Create a retrofit call object
        // Create a retrofit call object
        retrofit2.Call<String> responseMessage = retrofit.create(Restapi.class).register(new UserCredential("testuser@abc.de", "testpassword"));

        //Enque the call
        responseMessage.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                //Set the response to the textview
               // textView.setText(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                //Set the error to the textview
                //textView.setText(t.toString());
            }
        });
    }
}
