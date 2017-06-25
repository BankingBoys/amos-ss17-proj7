package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.dtos.AddSavingsAccountData;

public class AddSavingsAccountActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountActivity.class.getSimpleName();

    @BindView(R.id.add_savings_account_pager)
    ViewPager viewPager;

    @BindView(R.id.add_savings_account_button_previous)
    Button buttonPrevious;

    @BindView(R.id.add_savings_account_button_next)
    Button buttonNext;

    private AddSavingsAccountPagerAdapter pagerAdapter;
    private List<AddSavingsAccountPage> pages = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.saving_accounts_activity_add);
        ButterKnife.bind(this);

        pages.add(new AddSavingsAccountNameFragment());
        pages.add(new AddSavingsAccountAmountFragment());

        pagerAdapter = new AddSavingsAccountPagerAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        updateBottomBar();
    }

    @OnClick(R.id.add_savings_account_button_previous)
    void onClickPrevious() {
        final int currentItem = viewPager.getCurrentItem();
        if(currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }

    @OnClick(R.id.add_savings_account_button_next)
    void onClickNext() {
        final int currentItem = viewPager.getCurrentItem();
        if(currentItem < pagerAdapter.getCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1);
        } else {
            submit();
        }
    }

    @OnPageChange(R.id.add_savings_account_pager)
    void onPagerPageChange() {
        updateBottomBar();
    }

    private void updateBottomBar() {
        final int position = viewPager.getCurrentItem();
        buttonNext.setText(position >= pagerAdapter.getCount() - 1
                ? R.string.add_savings_account_button_submit
                : R.string.add_savings_account_button_next);
        buttonPrevious.setVisibility(position <= 0
                ? View.INVISIBLE
                : View.VISIBLE);
    }

    private void submit() {
        final AddSavingsAccountData result = new AddSavingsAccountData();
        for (final AddSavingsAccountPage page : pages) {
            page.fillInData(result);
        }
        Toast.makeText(this, String.format(Locale.getDefault(), "Name: %s, Amount: %.2f", result.name, result.goalBalance), Toast.LENGTH_LONG).show();
        finish();
    }
}
