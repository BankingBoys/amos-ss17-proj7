package de.fau.amos.virtualledger.android.views.savings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;

public class AddSavingsAccountActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.saving_accounts_activity_add);
        ButterKnife.bind(this);
    }
}
