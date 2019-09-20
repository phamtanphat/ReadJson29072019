package phamtanphat.ptp.khoaphamtraining.readjson29072019;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView txtJsonDemo1;
    Button btnJsonDemo1;
    // Dai quan sat : Noi chua du lieu se phat tan ra ngoai
    Observable<String> mData;
    Disposable disposable;
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
                return Observable.just(docNoiDung_Tu_URL("https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json"));
            }
        });
    }

    @Override
    protected void onStop() {
        if (disposable != null){
            disposable.dispose();
        }
        super.onStop();
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