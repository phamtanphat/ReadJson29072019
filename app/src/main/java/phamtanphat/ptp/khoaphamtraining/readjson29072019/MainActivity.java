package phamtanphat.ptp.khoaphamtraining.readjson29072019;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView txtJsonDemo1;
    Button btnJsonDemo1;
    // Dai quan sat : Noi chua du lieu se phat tan ra ngoai
    Observable<String> mData;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtJsonDemo1 = findViewById(R.id.textviewJson);
        btnJsonDemo1 = findViewById(R.id.buttonJsonDemo1);

        btnJsonDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDataFromUrl();
            }
        });
        // Viet ra 1 observable cho Doi tuong sinh vien
        // Khi doi tuong sinhvien thay doi thi onNext se chay lai
    }
    private void callDataFromUrl() {
        mData = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(docNoiDung_Tu_URL("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json"));
            }
        });
        compositeDisposable.add(mData
                        .subscribeOn(Schedulers.io())
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
                                    Toast.makeText(MainActivity.this, khoahoc , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
    }

    @Override
    protected void onStop() {
        if (compositeDisposable != null){
            compositeDisposable.dispose();
        }
        super.onStop();
    }


}