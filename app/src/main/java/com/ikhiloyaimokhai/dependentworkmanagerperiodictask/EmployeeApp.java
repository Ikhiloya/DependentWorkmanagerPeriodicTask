package com.ikhiloyaimokhai.dependentworkmanagerperiodictask;

import android.app.Application;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDao;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.db.EmployeeDatabase;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.service.EmployeeService;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.util.Constant;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.worker.BeneficiaryWorker;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask.worker.StaffWorker;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class EmployeeApp extends Application {

    private static final String TAG = EmployeeApp.class.getSimpleName();
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private EmployeeService employeeService;
    private static EmployeeApp INSTANCE;
    private static EmployeeDao employeeDao;
    private WorkManager workManager;


    public static EmployeeApp get() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        //Gson Builder
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        Timber.plant(new Timber.DebugTree());

        // HttpLoggingInterceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //   OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //Retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        employeeService = mRetrofit.create(EmployeeService.class);
        employeeDao = EmployeeDatabase.getDatabase(getApplicationContext()).employeeDao();

        workManager = WorkManager.getInstance(this);
    }

    public void startWorkers() {
        startStaffWorker();
        startBeneficiaryWorker();
    }


    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public WorkManager getWorkManager() {
        return workManager;
    }


    private void startStaffWorker() {
        Timber.i("starting Staff worker............");
        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(StaffWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(Constant.TAG_SYNC_STAFF)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        this.getWorkManager().enqueueUniquePeriodicWork(
                Constant.SYNC_STAFF_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );
    }

    private void startBeneficiaryWorker() {
        Timber.i("starting Beneficiary worker............");
        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(BeneficiaryWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(Constant.TAG_SYNC_BENEFICIARY)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        this.getWorkManager().enqueueUniquePeriodicWork(
                Constant.SYNC_BENEFICIARY_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );
    }

}
