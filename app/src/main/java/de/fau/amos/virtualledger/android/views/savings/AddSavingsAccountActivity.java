package de.fau.amos.virtualledger.android.views.savings;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;

public class AddSavingsAccountActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountActivity.class.getSimpleName();

    @BindView(R.id.add_savings_account_pager)
    ViewPager viewPager;

    private AddSavingsAccountPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.saving_accounts_activity_add);
        ButterKnife.bind(this);

        pagerAdapter = new AddSavingsAccountPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @OnClick(R.id.add_savings_account_button_previous)
    void onClickPrevious() {

    }

    @OnClick(R.id.add_savings_account_button_next)
    void onClickNext() {

    }
}
