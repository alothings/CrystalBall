package com.example.crystalball;


import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalball.ShakeDetector.OnShakeListener;


public class MainActivity extends ActionBarActivity {
	
	public static final String TAG = MainActivity.class.getSimpleName();
	
	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
  
        //We are assigning the views from layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
        
        //Constants that represent what we are trying to get...
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			public void onShake() {
				// call the new method handle new answer
				handleNewAnswer();
				
			}
		});  
        
        // learning toast into one line!!!------------
        //Toast.makeText(this, "Yay! Our activity was created!", Toast.LENGTH_LONG).show();
        Toast welcomeToast = Toast.makeText(this, "Awesome! Our activity was created!", Toast.LENGTH_LONG);
        welcomeToast.setGravity(Gravity.TOP, 0, 0);
        welcomeToast.show();
        // ---------------------------------------------
        
        // Learning Log Cat --- -- - - -- - - -- - -- - - -
        Log.d(TAG, "We're loggin from the onCreate() method!");
        
        
        
        
    }
	
	@Override
	public void onResume(){
		super.onResume();
        mSensorManager.registerListener(mShakeDetector,  mAccelerometer,
        		SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}
	
	private void animateCrystalBall(){
		mCrystalBallImage.setImageResource(R.drawable.ball_animation); //this is the drawable resource
		
		//creates instance animationDrawable (What animates it!)
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
		
		if(ballAnimation.isRunning()){
			ballAnimation.stop();
		}
		
		ballAnimation.start(); //to start animation
	}
	
	private void animateAnswer(){
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0,1);
		fadeInAnimation.setDuration(1700); //in milliseconds
		fadeInAnimation.setFillAfter(true); //so the changes stay after animation done
		
		//AlphaAnimation fadeOutAnimation = new AlphaAnimation(1,0);
		//fadeOutAnimation.setDuration(2000);
		
		mAnswerLabel.setAnimation(fadeInAnimation);
	}

	private void playSound(){
		//mediaPlayer class to play and stream!!
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void handleNewAnswer() {
		String answer = mCrystalBall.getAnAnswer();
		mAnswerLabel.setText(answer);
		
		//Animation
		animateCrystalBall();
		animateAnswer();
		playSound();
	}

    
}
