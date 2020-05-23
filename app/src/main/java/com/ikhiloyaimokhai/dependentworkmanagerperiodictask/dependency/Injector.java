package com.ikhiloyaimokhai.dependentworkmanagerperiodictask.dependency;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.EmployeeApp;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.factory.EmployeeViewModelFactory;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.repository.EmployeeRepository;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.service.EmployeeService;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.AppExecutor;


/**
 * Created by Ikhiloya Imokhai on 2020-05-21.
 * <p>
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */

public class Injector {

    /**
     * Creates an instance of  [EmployeeService]
     */
    public static EmployeeService provideEmployeeService() {
        return EmployeeApp.get().getEmployeeService();
    }

    /**
     * Creates an instance of [EmployeeRepository]
     */
    private static EmployeeRepository provideEmployeeRepository(Activity activity) {
        return new EmployeeRepository(activity.getApplication());
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    public static ViewModelProvider.Factory provideViewModelFactory(Activity activity) {
        return new EmployeeViewModelFactory(provideEmployeeRepository(activity));
    }


    /**
     * Provides the [AppExecutor] that is then used to get a reference to an Executor
     *
     * @return [AppExecutor]
     */
    public static AppExecutor provideExecutors() {
        return new AppExecutor();
    }
}
