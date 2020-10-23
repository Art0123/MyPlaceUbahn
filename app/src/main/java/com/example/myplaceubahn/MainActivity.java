package com.example.myplaceubahn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplaceubahn.mystations.Stations;
import com.example.myplaceubahn.network.NetworkConnection;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private Button u4, u3u6, bus59;
    private TextView toHome, fromHome;
    public Handler h = new Handler();
    public Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        u4 = findViewById(R.id.button);
        u3u6 = findViewById(R.id.buttonWork);
        bus59 = findViewById(R.id.bus59);

        toHome = findViewById(R.id.resultToHome);
        fromHome = findViewById(R.id.resultFromHome);

        u4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.removeCallbacks(runnable);
                if (NetworkConnection.isOnline(MainActivity.this)) {
                    getUbahnInfoHome();
                    h.postDelayed(runnable = new Runnable() {
                        @Override
                        public void run() {
                            u4.performClick();
                        }
                    }, 30000);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No Internet Connection",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        u3u6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.removeCallbacks(runnable);
                if (NetworkConnection.isOnline(MainActivity.this)) {
                    getUbahnInfoOdeon();
                    h.postDelayed(runnable = new Runnable() {
                        @Override
                        public void run() {
                            u3u6.performClick();
                        }
                    }, 30000);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No Internet Connection",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        bus59.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.removeCallbacks(runnable);
                if (NetworkConnection.isOnline(MainActivity.this)) {
                    getBusInfoHome();
                    h.postDelayed(runnable = new Runnable() {
                        @Override
                        public void run() {
                            bus59.performClick();
                        }
                    }, 30000);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }


    public void getUbahnInfoHome() {
       new Thread(new Runnable() {
           final Stations line1 = new Stations();
           final Stations line2 = new Stations();
           @Override
           public void run() {
               final Spannable sp1 = new SpannableString(line1.homeStation());
               final Spannable sp2 = new SpannableString(line2.fromOdeonToHome());

               sp1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sp1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
               sp2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sp2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       fromHome.setText(sp1, TextView.BufferType.SPANNABLE);
                       toHome.setText(sp2, TextView.BufferType.SPANNABLE);
                   }
               });
           }
       }).start();
    }

    public void getBusInfoHome() {
        new Thread(new Runnable() {
            final Stations station = new Stations();
            @Override
            public void run() {
                final Spannable sp1 = new SpannableString(station.homeBusToShop());
                final Spannable sp2 = new SpannableString(station.busFromShopToHome());

                sp1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sp1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, sp2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fromHome.setText(sp1, TextView.BufferType.SPANNABLE);
                        toHome.setText(sp2, TextView.BufferType.SPANNABLE);
                    }
                });
            }
        }).start();
    }

    public void getUbahnInfoOdeon() {
        new Thread(new Runnable() {
            final Stations line1 = new Stations();
            final Stations line2 = new Stations();
            @Override
            public void run() {
                Spannable spToHome = new SpannableString(line1.fromWorkToOdeon());
                Spannable spFromHome = new SpannableString(line2.fromOdeonToWork());

                spToHome.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spToHome.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spFromHome.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spFromHome.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fromHome.setText(spFromHome, TextView.BufferType.SPANNABLE); //z prawa
                        toHome.setText(spToHome, TextView.BufferType.SPANNABLE);

                    }
                });
            }
        }).start();
    }
}
