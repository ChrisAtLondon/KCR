package com.example.killcunningrabit;

public class GameLevel {
	private int levelId;
	private String levelName;
	private String levelLockFlag;
	private String levelThumb;

	public GameLevel(int levelId, String levelName, String levelLockFlag,String levelThumb) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
		this.levelLockFlag = levelLockFlag;
		this.setLevelThumb(levelThumb);
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelLockFlag() {
		return levelLockFlag;
	}

	public void setLevelLockFlag(String levelLockFlag) {
		this.levelLockFlag = levelLockFlag;
	}

	public String getLevelThumb() {
		return levelThumb;
	}

	public void setLevelThumb(String levelThumb) {
		this.levelThumb = levelThumb;
	}

}
