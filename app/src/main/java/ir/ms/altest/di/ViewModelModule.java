package ir.ms.altest.di;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ir.ms.altest.pager.order.OrderViewModel;
import ir.ms.altest.pager.product.ProductViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel.class)
    abstract ViewModel bindProductViewModel(ProductViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel.class)
    abstract ViewModel bindOrderViewModel(OrderViewModel model);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
