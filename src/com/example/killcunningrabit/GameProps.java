package com.example.killcunningrabit;

public class GameProps {
private int propsId;
private String propsName;
private String propsType;
private String propsLockFlag;
public GameProps(int propsId, String propsName, String propsType,
		String propsLockFlag) {
	super();
	this.propsId = propsId;
	this.propsName = propsName;
	this.propsType = propsType;
	this.propsLockFlag = propsLockFlag;
}
public int getPropsId() {
	return propsId;
}
public void setPropsId(int propsId) {
	this.propsId = propsId;
}
public String getPropsName() {
	return propsName;
}
public void setPropsName(String propsName) {
	this.propsName = propsName;
}
public String getPropsType() {
	return propsType;
}
public void setPropsType(String propsType) {
	this.propsType = propsType;
}
public String getPropsLockFlag() {
	return propsLockFlag;
}
public void setPropsLockFlag(String propsLockFlag) {
	this.propsLockFlag = propsLockFlag;
}

}
