package phamtanphat.ptp.khoaphamtraining.readjson29072019;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

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
    MainViewModel mainViewModel;
    // Dai quan sat : Noi chua du lieu se phat tan ra ngoai
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtJsonDemo1 = findViewById(R.id.textviewJson);
        btnJsonDemo1 = findViewById(R.id.buttonJsonDemo1);
        mainViewModel = new MainViewModel();
        mainViewModel.mTxtNoiDung.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null){
                    txtJsonDemo1.setText(s);
                }
            }
        });
        btnJsonDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.callDataFromUrl("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json");
            }
        });
        // Viet ra 1 observable cho Doi tuong sinh vien
        // Khi doi tuong sinhvien thay doi thi onNext se chay lai
    }
}