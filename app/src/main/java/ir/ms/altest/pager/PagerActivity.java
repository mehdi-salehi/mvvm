package ir.ms.altest.pager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.ms.altest.R;
import ir.ms.altest.base.BaseActivity;
import ir.ms.altest.databinding.ActivityPagerBinding;
import ir.ms.altest.pager.order.OrderFragment;
import ir.ms.altest.pager.order.OrderViewModel;
import ir.ms.altest.pager.product.ProductFragment;

public class PagerActivity extends BaseActivity {

    private ActivityPagerBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private OrderViewModel viewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OrderViewModel.class);

        setToolbar();
        setupViewPager();
        runProductStatusChanger();
    }

    @Override
    protected void onDestroy() {
        if (getDisposable() != null)
            getDisposable().clear();
        super.onDestroy();
    }

    private void runProductStatusChanger() {
        Observable.interval(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getDisposable().add(d);
                    }

                    @Override
                    public void onNext(Object aLong) {
                        Log.e("onNext", "checkOrders");
                        viewModel.checkOrders();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("status runner", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setToolbar() {
        Toolbar toolbar = binding.toolbar.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setTitle(R.string.app_name);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ProductFragment.getInstance(1), "Products");
        adapter.addFragment(OrderFragment.getInstance(2), "Orders");
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void nextPage() {
        binding.viewPager.setCurrentItem(1, true);
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() == 1)
            binding.viewPager.setCurrentItem(0);
        else
            super.onBackPressed();
    }
}
