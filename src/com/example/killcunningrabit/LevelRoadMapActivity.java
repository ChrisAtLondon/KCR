package com.example.killcunningrabit;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;

public class LevelRoadMapActivity extends BaseGameActivity implements
		IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================
	public static final String LEVEL_THUMB_LOCATION = "";
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private Scene mLevelScene;
	private MenuScene mLevelMenuScene;
	private Texture mFontTexture,mLevelBgTexture;
	private TextureRegion mLevelBgTextureRegion;
	private Font mFont;
	private Handler mHandler;
	protected Camera mCamera;
	private List<GameLevel> glList;
	
	

	
	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mFontTexture =new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		this.mFont=FontFactory.createFromAsset(this.mFontTexture, this, "Flubber.ttf", 32, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		this.mLevelBgTexture = new BitmapTextureAtlas(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mLevelBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromResource((BitmapTextureAtlas) this.mLevelBgTexture, this, R.drawable.main_menu_bg, 0, 0);
		this.mEngine.getTextureManager().loadTexture(mLevelBgTexture);
		//¹Ø¿¨ÐÅÏ¢
		GameLevelDAO glDao = new GameLevelDAO(this);
		glList = glDao.queryUnlockedGameLevels();
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.createLevelMenuScene();
		final int centerX = (CAMERA_WIDTH-this.mLevelBgTextureRegion.getWidth())/2;
		final int centerY = (CAMERA_HEIGHT-this.mLevelBgTextureRegion.getHeight())/2;
		this.mLevelScene = new Scene();
		final Sprite menuBg = new Sprite(centerX, centerY, this.mLevelBgTextureRegion);
		mLevelScene.attachChild(menuBg);
		//mLevelMenuScene.setPosition(0, -80);
		mLevelScene.setChildScene(mLevelMenuScene);
		//createPopupHelpScene(centerX,centerY);
		//mHelpScene.setChildScene(mHelpMenuScene);
		return this.mLevelScene;
	}

	private void createLevelMenuScene() {
		
		this.mLevelMenuScene = new MenuScene(this.mCamera);
		if (glList!=null) {
		for (GameLevel gL : glList) {
			IMenuItem levelMenuItem = new ColorMenuItemDecorator(new TextMenuItem(gL.getLevelId(), mFont, gL.getLevelName()), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
			levelMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.mLevelMenuScene.addMenuItem(levelMenuItem);	
		}
		}
		this.mLevelMenuScene.buildAnimations();
		this.mLevelMenuScene.setBackgroundEnabled(false);
		this.mLevelMenuScene.setOnMenuItemClickListener(this);
	
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

}
