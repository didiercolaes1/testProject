package edu.ap.spring.test;

import org.springframework.stereotype.Service;

@Service
public class Singleton {

	private int grade;
	
	public void setGrade(int g, String test) {
		System.out.println(test + " : " + g);
		this.grade += g;
	}
	
	public int getGrade() {
		return this.grade;
	}
}
