package org.pattersonclippers.apiappjjc;

 import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

 import java.io.IOException;

 import okhttp3.Call;
 import okhttp3.Callback;
 import okhttp3.OkHttpClient;
 import okhttp3.Request;
 import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView currency1title, currency2title, datetitle, amountTV, ratesTV, curr1amt, curr2amt;
    EditText currency1ET, currency2ET, amountET, dateOfRateET;
    Button submitBTN;
    String curr1, curr2, amount, date, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currency1title = (TextView) findViewById(R.id.currency1title);
        currency2title = (TextView) findViewById(R.id.currency2title);
        curr1amt = (TextView) findViewById(R.id.curr1amt);
        curr2amt = (TextView) findViewById(R.id.curr2amt);
        datetitle = (TextView) findViewById(R.id.datetitle);
        amountTV = (TextView) findViewById(R.id.amountTV);
        ratesTV = (TextView) findViewById(R.id.ratesTV);

        currency1ET = (EditText) findViewById(R.id.currency1ET);
        currency2ET = (EditText) findViewById(R.id.currency2ET);
        amountET = (EditText) findViewById(R.id.amountET);
        dateOfRateET = (EditText) findViewById(R.id.dateOfRateET);

        submitBTN = (Button) findViewById(R.id.submitBTN);

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //currency1ET, currency2ET, amountET, dateOfRateET
                curr1 = currency1ET.getText().toString();
                curr2 = currency2ET.getText().toString();
                amount = amountET.getText().toString();
                date = dateOfRateET.getText().toString();

                currency1title.setText(curr1);
                currency2title.setText(curr2);
                curr1amt.setText(curr1 + " amount:");
                curr2amt.setText(curr2 + " amount:");
                datetitle.setText(date);
                amountTV.setText(amount);

                OkHttpClient client = new OkHttpClient();
                String url = "https://api.frankfurter.app/" + date + "?amount=" + amount + "&from=" + curr1 + "&to=" + curr2;
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("AAAAAAAAAA", myResponse);
                                    double currencyRate = Utils.extractFeatureFromJson(myResponse, curr2);
                                    ratesTV.setText("" + currencyRate);
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}