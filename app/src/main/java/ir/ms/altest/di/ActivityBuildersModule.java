package ir.ms.altest.di;

import ir.ms.altest.di.scope.FragmentScoped;
import ir.ms.altest.di.scope.PerActivity;
import ir.ms.altest.pager.PagerActivity;
import ir.ms.altest.pager.order.OrderFragment;
import ir.ms.altest.pager.product.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract PagerActivity bindPagerActivity();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProductFragment provideProductFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OrderFragment provideOrderFragment();
}
