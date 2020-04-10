package ar.edu.davinci.audioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
MediaPlayer mySong;
ImageView mImageView;
TextView mResultTv;
View mColorView;

Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void colorIT (View v){
        mImageView = findViewById(R.id.imageView);
        mResultTv = findViewById(R.id.resultTv);
        mColorView = findViewById(R.id.colorView);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);

        //image view on touche listener
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = mImageView.getDrawingCache();

                    int pixel = bitmap.getPixel((int)motionEvent.getX(), (int)motionEvent.getY());

                    //getting RGB values

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    //getting Hex value

                    String hex = "#" + Integer.toHexString(pixel);

                    //setBackground color according to the picker color
                    mColorView.setBackgroundColor(Color.rgb(r,g,b));

                    //set RGB, HEX values to textview

                    mResultTv.setText(("RGB: "+ r + ", "+ g + ", " + b + "\nHEX: " + hex));
                }
                return true;
            }
        });

    }

    public void playIT (View v){

        if(mySong == null){
            mySong=MediaPlayer.create(MainActivity.this,R.raw.save);
            mySong.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSong();
                }
            });
        }

        mySong.start();
    }
    public void pauseIT(View v){
        if(mySong != null) {
            mySong.pause();
        }
    }

    public void stopIT(View v){
        stopSong();
    }

    private void stopSong(){
        if(mySong != null){
            mySong.release();
            mySong = null;
            Toast.makeText(this, "Media Player released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSong();
    }
}
