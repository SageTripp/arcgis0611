package com.example.ucmap.bean;

import com.esri.arcgisruntime.mapping.view.Graphic;

import java.util.List;
import java.util.Map;

public class BuildLand {
	 public Integer TotalNums;//建设用地总地块数
	    public Double TotalArea;//建设用地总面积
	    public String TotalText;//建设用地各类用地统计文本
	    public String LandTypeText;//建设用地各类用地各地类文本
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

	    public Map<String,LandType> LandTypeMap;//具体地类的数据map

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
