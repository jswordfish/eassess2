package com.assessment.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTFdto {

	String matchLeft1;
	
	String matchLeft2;
	
	String matchLeft3;
	
	String matchLeft4;
	
	String matchLeft5;
	
	String matchLeft6;
	
	String matchRight1;
	
	String matchRight2;
	
	String matchRight3;
	
	String matchRight4;
	
	String matchRight5;
	
	String matchRight6;
	
	String matchRight1Display;
	
	String matchRight1DisplayColour;
	
	String matchRight2Display;
	
	String matchRight2DisplayColour;
	
	String matchRight3Display;
	
	String matchRight3DisplayColour;
	
	String matchRight4Display;
	
	String matchRight4DisplayColour;
	
	String matchRight5Display;
	
	String matchRight5DisplayColour;
	
	String matchRight6Display;
	
	String matchRight6DisplayColour;
	
	
	List<String> rightOptions = new ArrayList<String>();
	
	Map<String, String> option_color = new HashMap();
	
	public void initAnswerColours(){
		if(getMatchRight1() != null){
			option_color.put(getMatchRight1(), "rgb(255, 0, 0)");
		}
		
		if(getMatchRight2() != null){
			option_color.put(getMatchRight2(), "rgb(0, 128, 0)");
		}
		
		if(getMatchRight3() != null){
			option_color.put(getMatchRight3(), "rgb(0, 0, 255)");
		}
		
		if(getMatchRight4() != null){
			option_color.put(getMatchRight4(), "rgb(255, 192, 203)");
		}
		
		if(getMatchRight5() != null){
			option_color.put(getMatchRight5(), "rgb(235, 21, 135)");
		}
		
		if(getMatchRight6() != null){
			option_color.put(getMatchRight6(), "rgb(186, 252, 53)");
		}
	
	}


	public String getMatchLeft1() {
		return matchLeft1;
	}


	public void setMatchLeft1(String matchLeft1) {
		this.matchLeft1 = matchLeft1;
	}


	public String getMatchLeft2() {
		return matchLeft2;
	}


	public void setMatchLeft2(String matchLeft2) {
		this.matchLeft2 = matchLeft2;
	}


	public String getMatchLeft3() {
		return matchLeft3;
	}


	public void setMatchLeft3(String matchLeft3) {
		this.matchLeft3 = matchLeft3;
	}


	public String getMatchLeft4() {
		return matchLeft4;
	}


	public void setMatchLeft4(String matchLeft4) {
		this.matchLeft4 = matchLeft4;
	}


	public String getMatchLeft5() {
		return matchLeft5;
	}


	public void setMatchLeft5(String matchLeft5) {
		this.matchLeft5 = matchLeft5;
	}


	public String getMatchLeft6() {
		return matchLeft6;
	}


	public void setMatchLeft6(String matchLeft6) {
		this.matchLeft6 = matchLeft6;
	}


	public String getMatchRight1() {
		return matchRight1;
	}


	public void setMatchRight1(String matchRight1) {
		this.matchRight1 = matchRight1;
	}


	public String getMatchRight2() {
		return matchRight2;
	}


	public void setMatchRight2(String matchRight2) {
		this.matchRight2 = matchRight2;
	}


	public String getMatchRight3() {
		return matchRight3;
	}


	public void setMatchRight3(String matchRight3) {
		this.matchRight3 = matchRight3;
	}


	public String getMatchRight4() {
		return matchRight4;
	}


	public void setMatchRight4(String matchRight4) {
		this.matchRight4 = matchRight4;
	}


	public String getMatchRight5() {
		return matchRight5;
	}


	public void setMatchRight5(String matchRight5) {
		this.matchRight5 = matchRight5;
	}


	public String getMatchRight6() {
		return matchRight6;
	}


	public void setMatchRight6(String matchRight6) {
		this.matchRight6 = matchRight6;
	}


	public List<String> getRightOptions() {
		return rightOptions;
	}


	public void setRightOptions(List<String> rightOptions) {
		this.rightOptions = rightOptions;
	}


	public String getMatchRight1Display() {
		return matchRight1Display;
	}


	public void setMatchRight1Display(String matchRight1Display) {
		this.matchRight1Display = matchRight1Display;
	}


	public String getMatchRight2Display() {
		return matchRight2Display;
	}


	public void setMatchRight2Display(String matchRight2Display) {
		this.matchRight2Display = matchRight2Display;
	}


	public String getMatchRight3Display() {
		return matchRight3Display;
	}


	public void setMatchRight3Display(String matchRight3Display) {
		this.matchRight3Display = matchRight3Display;
	}


	public String getMatchRight4Display() {
		return matchRight4Display;
	}


	public void setMatchRight4Display(String matchRight4Display) {
		this.matchRight4Display = matchRight4Display;
	}


	public String getMatchRight5Display() {
		return matchRight5Display;
	}


	public void setMatchRight5Display(String matchRight5Display) {
		this.matchRight5Display = matchRight5Display;
	}


	public String getMatchRight6Display() {
		return matchRight6Display;
	}


	public void setMatchRight6Display(String matchRight6Display) {
		this.matchRight6Display = matchRight6Display;
	}


	public String getMatchRight1DisplayColour() {
		if(getMatchRight1Display() != null){
			return option_color.get(getMatchRight1Display());
		}
		return null;
	}


	public String getMatchRight2DisplayColour() {
		if(getMatchRight2Display() != null){
			return option_color.get(getMatchRight2Display());
		}
		return null;
	}


	public String getMatchRight3DisplayColour() {
		if(getMatchRight3Display() != null){
			return option_color.get(getMatchRight3Display());
		}
		return null;
	}


	public String getMatchRight4DisplayColour() {
		if(getMatchRight4Display() != null){
			return option_color.get(getMatchRight4Display());
		}
		return null;
	}


	public String getMatchRight5DisplayColour() {
		if(getMatchRight5Display() != null){
			return option_color.get(getMatchRight5Display());
		}
		return null;
	}


	public String getMatchRight6DisplayColour() {
		if(getMatchRight6Display() != null){
			return option_color.get(getMatchRight6Display());
		}
		return null;
	}

	
}
