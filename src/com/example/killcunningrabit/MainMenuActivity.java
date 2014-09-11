package com.example.killcunningrabit;


import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
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
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Toast;

public class MainMenuActivity extends BaseGameActivity implements IOnMenuItemClickListener{
	
	private static final int CAMERA_WIDTH=320;
	private static final int CAMERA_HEIGHT=480;
	
	protected static final int MENU_PLAY=1;
	protected static final int MENU_SHOP=MENU_PLAY+1;
	protected static final int MENU_SCORES=MENU_SHOP+1;
	protected static final int MENU_OPTIONS=MENU_SCORES+1;
	protected static final int MENU_HELP=MENU_OPTIONS+1;
	protected static final int MENU_ABOUT=MENU_HELP+1;
	protected static final int MENU_HELP_BACK=MENU_ABOUT+1;
	
	
	
	private Camera mCamera;
	private Scene mMainScene;
	private MenuScene mStaticMenuScene,mHelpMenuScene;
	private Texture mFontTexture,mMenuBgTexture,mPopupTexture,mHelpBgTexture;
	private TextureRegion mMenuBgTextureRegion,mPopupAboutTextureRegion,mHelpBgTextureRegion;
	private Font mFont;
	private Handler mHandler;

	@Override
	public Engine onLoadEngine() {
		this.mHandler = new Handler();
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
		this.mMenuBgTexture = new BitmapTextureAtlas(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromResource((BitmapTextureAtlas) this.mMenuBgTexture, this, R.drawable.main_menu_bg, 0, 0);
		this.mEngine.getTextureManager().loadTexture(mMenuBgTexture);
		//load Help related resource
		this.mHelpBgTexture = new BitmapTextureAtlas(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mHelpBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromResource((BitmapTextureAtlas) this.mHelpBgTexture, this, R.drawable.main_help_bg, 0, 0);
		this.mEngine.getTextureManager().loadTexture(mHelpBgTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.createStaticMenuScene();
		//this.createPopupMenuScene();
		final int centerX = (CAMERA_WIDTH-this.mMenuBgTextureRegion.getWidth())/2;
		final int centerY = (CAMERA_HEIGHT-this.mMenuBgTextureRegion.getHeight())/2;
		this.mMainScene = new Scene();
		final Sprite menuBg = new Sprite(centerX, centerY, this.mMenuBgTextureRegion);
		mMainScene.attachChild(menuBg);
		mStaticMenuScene.setPosition(0, -80);
		mMainScene.setChildScene(mStaticMenuScene);
		createPopupHelpScene(centerX,centerY);
		//mHelpScene.setChildScene(mHelpMenuScene);
		return this.mMainScene;
	}

/*	private void createPopupMenuScene() {
		this.mPopupMenuScene = new MenuScene(mCamera);
		final SpriteMenuItem aboutMenuItem = new SpriteMenuItem(MENU_ABOUT, this.mPopupAboutTextureRegion);
		aboutMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPopupMenuScene.addMenuItem(aboutMenuItem);
		this.mPopupMenuScene.setMenuAnimator(new SlideMenuAnimator());
		this.mPopupMenuScene.buildAnimations();
		this.mPopupMenuScene.setBackgroundEnabled(false);
		this.mPopupMenuScene.setOnMenuItemClickListener(this);		
	}*/
	
	private void createPopupHelpScene(int centerX, int centerY) {
		final Sprite helpBg = new Sprite(centerX, centerY, this.mHelpBgTextureRegion);
		this.mHelpMenuScene = new MenuScene(mCamera);
		this.mHelpMenuScene.attachChild(helpBg);
		final IMenuItem mHelpBackMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_HELP_BACK, mFont, "Back"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		mHelpBackMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mHelpBackMenuItem.setPosition(100, 100);
		this.mHelpMenuScene.addMenuItem(mHelpBackMenuItem);
		this.mHelpMenuScene.setMenuAnimator(new SlideMenuAnimator());
		this.mHelpMenuScene.buildAnimations();
		this.mHelpMenuScene.setBackgroundEnabled(false);
		this.mHelpMenuScene.setOnMenuItemClickListener(this);	
		this.mHelpMenuScene.setPosition((CAMERA_WIDTH-this.mHelpBgTextureRegion.getWidth())/2, (CAMERA_HEIGHT-this.mHelpBgTextureRegion.getHeight())/2);
	}

	private void createStaticMenuScene() {
		this.mStaticMenuScene = new MenuScene(this.mCamera);
		final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_PLAY, mFont, "Play"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		playMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(playMenuItem);
		
		final IMenuItem shopMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_SHOP, mFont, "Shop"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		shopMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(shopMenuItem);
		
		final IMenuItem scoresMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_SCORES, mFont, "Scores"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		scoresMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(scoresMenuItem);
		
		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_OPTIONS, mFont, "Options"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		optionsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(optionsMenuItem);
				
		final IMenuItem helpMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_HELP, mFont, "Help"), 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		helpMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(helpMenuItem);
		this.mStaticMenuScene.buildAnimations();
		this.mStaticMenuScene.setBackgroundEnabled(false);
		this.mStaticMenuScene.setOnMenuItemClickListener(this);
						
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_ABOUT:
			Toast.makeText(MainMenuActivity.this, "About selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_PLAY:
			this.mMainScene.registerEntityModifier(new ScaleModifier(1.0f, 1.0f, 0.0f));
			this.mStaticMenuScene.registerEntityModifier(new ScaleModifier(1.0f, 1.0f, 0.0f));
			mHandler.postDelayed(mLaunchLevelRoadMap, 3000);
			return true;
		case MENU_SHOP:
			Toast.makeText(MainMenuActivity.this, "Shop selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_SCORES:
			Toast.makeText(MainMenuActivity.this, "Scores selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_OPTIONS:
			Toast.makeText(MainMenuActivity.this, "Options selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_HELP:
			//Toast.makeText(MainMenuActivity.this, "Help selected", Toast.LENGTH_SHORT).show();
			this.mMainScene.setChildScene(this.mHelpMenuScene, true, true, true);
			return true;
		case MENU_HELP_BACK:
			//Toast.makeText(MainMenuActivity.this, "Back selected", Toast.LENGTH_SHORT).show();
			this.mHelpMenuScene.back();
			mMainScene.setChildScene(mStaticMenuScene);
			return true;
		default:
			return false;
		}
	}
	
    private Runnable mLaunchLevelRoadMap = new Runnable() {
        public void run() {
    		Intent myIntent = new Intent(MainMenuActivity.this, LevelRoadMapActivity.class);
    		MainMenuActivity.this.startActivity(myIntent);
        }
     };

}