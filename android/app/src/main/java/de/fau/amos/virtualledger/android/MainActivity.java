package de.fau.amos.virtualledger.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import de.fau.amos.virtualledger.R;

public class MainActivity extends AppCompatActivity {
/*

    @Inject
    Retrofit retrofit;
    TextView textView;
*/


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Button button_register = (Button) findViewById(R.id.button_register);
        final MainActivity _this = this;
        button_register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(_this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
  /*      ((App) getApplication()).getNetComponent().inject(this);

        //Create textview and findViewByID
        textView = (TextView) findViewById(R.id.textView2);
        //Create a retrofit call object
        retrofit2.Call<List<Post>> posts = retrofit.create(Restapi.class).getPosts();*/

        /*//Enque the call
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Post>> call, Response<List<Post>> response) {
                //Set the response to the textview
                textView.setText(response.body().get(0).getBody());

            }

            @Override
            public void onFailure(retrofit2.Call<List<Post>> call, Throwable t) {
                //Set the error to the textview
                textView.setText(t.toString());
            }
        });*/
    }
}
