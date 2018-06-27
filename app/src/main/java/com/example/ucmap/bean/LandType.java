package com.example.ucmap.bean;

import java.util.List;

public class LandType {
	    public String LandTypeCode;//该地类编码
	    public String LandTypeName;//该地类名称
	    public Integer TotalNums;//该地类的地块总数
	    public Double TotalAreas;//该地类的总面积
	    public List<com.esri.arcgisruntime.mapping.view.Graphic> Graphic;

	    public String getLandTypeCode() {
	        return LandTypeCode;
	    }

	    public void setLandTypeCode(String landTypeCode) {
	        LandTypeCode = landTypeCode;
	    }
	    public String getLandTypeName() {
	        return LandTypeName;
	    }

	    public void setLandTypeName(String landTypeName) {
	        LandTypeName = landTypeName;
	    }
	    public Integer getTotalNums() {
	        return TotalNums;
	    }

	    public void setTotalNums(Integer totalNums) {
	        TotalNums = totalNums;
	    }
	    public Double getTotalAreas() {
	        return TotalAreas;
	    }

	    public void setTotalAreas(Double totalAreas) {
	        TotalAreas = totalAreas;
	    }
}
