package phamtanphat.ptp.khoaphamtraining.readjson29072019;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Observer<String>{

    Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<String> stringObservable = Observable.just("A","B","C");

        stringObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (d != null){
            disposable = d;
        }

    }

    @Override
    public void onNext(String s) {
        Log.d("BBB",s);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }
}



