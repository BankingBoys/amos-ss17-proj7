package de.fau.amos.virtualledger.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.Post;
import de.fau.amos.virtualledger.android.dagger.Restapi;
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
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getNetComponent().inject(this);

        //Create textview and findViewByID
        textView = (TextView) findViewById(R.id.textView2);
        //Create a retrofit call object
        retrofit2.Call<List<Post>> posts = retrofit.create(Restapi.class).getPosts();

        //Enque the call
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
        });
    }
}
