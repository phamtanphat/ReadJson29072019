package phamtanphat.ptp.khoaphamtraining.readjson29072019;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements LifecycleObserver {

    public MainViewModel() {

    }

    MutableLiveData<String> mTxtNoiDung = new MutableLiveData<>();
    @SuppressLint("CheckResult")
    public void callDataFromUrl(final String url){
        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.just(docNoiDung_Tu_URL(url));
            }
        }).subscribeOn(Schedulers.io())
        .map(new Function<Object, String>() {

            @Override
            public String apply(Object o) throws Exception {
                return o.toString();
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                // 1 : Convert du lieu ve dang json neu nhu dang string
                // 2 : Dem bao nhieu the mo de doc duoc gia tri minh can thi khai bay nhiu json
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("danhsach");
                for (int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObjectKhoahoc = jsonArray.getJSONObject(i);
                    String khoahoc = jsonObjectKhoahoc.getString("khoahoc");
                    mTxtNoiDung.setValue(khoahoc);
                }
            }
        });
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
