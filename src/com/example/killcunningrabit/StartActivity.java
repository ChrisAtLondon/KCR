package com.example.killcunningrabit;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class StartActivity extends BaseGameActivity {
	private final int CAMERA_WIDTH = 320;
	private final int CAMERA_HEIGHT= 480;
	
	private Camera mCamera;
	private Texture mTexture,mBatTexture;
	private TextureRegion mSplashTextureRegion;
	private TiledTextureRegion mBatTextureRegion;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_start);
	}

	@Override
	public Engine onLoadEngine() {
		this.mHandler = new Handler();
		this.mCamera =new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mTexture=new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromResource((BitmapTextureAtlas) this.mTexture, this, R.drawable.splash_screen, 0, 0);
		this.mBatTexture = new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBatTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource((BitmapTextureAtlas) this.mBatTexture, this, R.drawable.start_bat_tiled, 0, 0, 2, 2) ;
		this.mEngine.getTextureManager().loadTexture(this.mTexture);
		this.mEngine.getTextureManager().loadTexture(this.mBatTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		final int centerX = (CAMERA_WIDTH-this.mSplashTextureRegion.getWidth())/2;
		final int centerY=(CAMERA_HEIGHT-this.mSplashTextureRegion.getHeight())/2;
		final Sprite splash = new Sprite(centerX, centerY, this.mSplashTextureRegion);
		scene.attachChild(splash);
		final AnimatedSprite bat = new AnimatedSprite(centerX+this.mSplashTextureRegion.getWidth()/2, 100, 100, 100, this.mBatTextureRegion);
		bat.animate(100);
		scene.attachChild(bat);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		mHandler.postDelayed(mLaunchTask, 3000);
	}
	
	private Runnable mLaunchTask = new Runnable() {
		
		@Override
		public void run() {
			Intent myIntent = new Intent(StartActivity.this, MainMenuActivity.class);
			StartActivity.this.startActivity(myIntent);
		}
	};

}
