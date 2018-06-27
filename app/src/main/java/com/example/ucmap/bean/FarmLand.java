package com.example.ucmap.bean;

import com.esri.arcgisruntime.mapping.view.Graphic;

import java.util.List;
import java.util.Map;

public class FarmLand {
	 public Integer TotalNums;//农用地总地块数
	    public Double TotalArea;//农用地总面积
	    public String TotalText;//农用地总体文本
	    public String LandTypeText;//农用地各地类文本
	    public Map<String,LandType> LandTypeMap;//具体地类的数据map
	    public List<Graphic> GraphicList;
	    public String getLandTypeText() {
	        return LandTypeText;
	    }

	    public void setLandTypeText(String landTypeText) {
	        LandTypeText = landTypeText;
	    }



	    public Map<String, LandType> getLandTypeMap() {
	        return LandTypeMap;
	    }

	    public void setLandTypeMap(Map<String, LandType> landTypeMap) {
	        LandTypeMap = landTypeMap;
	    }



	    public Integer getTotalNums() {
	        return TotalNums;
	    }

	    public void setTotalNums(Integer totalNums) {
	        TotalNums = totalNums;
	    }



	    public Double getTotalArea() {
	        return TotalArea;
	    }

	    public void setTotalArea(Double totalArea) {
	        TotalArea = totalArea;
	    }



	    public String getTotalText() {
	        return TotalText;
	    }

	    public void setTotalText(String totalText) {
	        TotalText = totalText;
	    }

}
