package com.amc.baseclass;

public enum assetTypes {

	Series(1,"Series"), OneOff(2,"One-Off"), Special(3,"Special"), Demo(4,"Demo"), Documentary(5,"Documentary"), 
	Pilot(6,"Pilot"), MadeForTV(7,"Made For TV"),MusicPerformance(8,"Music Performance"), FeatureLength(9,"Feature Length"), 
	ShortFilm(10,"Short Film");  
	
	public String assetType;
	public int rowNumber;
	
	private assetTypes(int rowNumber,String assetType) {
		this.rowNumber = rowNumber;
		this.assetType = assetType;
	}
	
	public int getTestCaseNumber() {
		return rowNumber;
	}
	
	public String getAssetType() {
		return assetType;
	}
	
	
}
