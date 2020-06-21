package edu.ap.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import org.junit.Before;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DjangoTest {
    
	private String GET_URL = "http://localhost:8000/urlparts/";
	
	@Autowired
	private Singleton singleton;
	
    @Before
    public void setUp() {}
        
    @Test
    public void testList() {
        String found =  this.doGet("ylioppilastutkinto1/ylioppilastutkinto2/ylioppilastutkinto3");
        found += this.doGet("opiskelija1/opiskelija2/");
        found += this.doGet("peruste1/peruste2/peruste3/peruste4");

        if(found.contains("ylioppilastutkinto2") && found.contains("opiskelija1") && found.contains("peruste4")) {
        		singleton.setGrade(4, "testList");
        }
    }
        
    @Test
    public void testEmpty() {
    		String found =  this.doGet("pietarsaari//helsinki");
        found += this.doGet("pietarsaari///opiskelija2/");
        found += this.doGet("/helsinki/peruste3/peruste4");
        System.out.println(found);
        
    		if(this.countOccurrences(found, "pietarsaari") == 0 
    						&& this.countOccurrences(found, "helsinki") == 0) {
    			singleton.setGrade(2, "testEmpty");
    		}
     }
     
    @Test
    public void getFileChanged() {
    		String path = DjangoTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    		File f = new File(path.substring(0, path.indexOf("/target")) + "/src/test/java/edu/ap/spring/test/DjangoTest.java");
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		System.out.println("DjangoTest last modified : " + sdf.format(f.lastModified()));
    }

    private String doGet(String pattern) {

        URL obj;
		try {
			obj = new URL(this.GET_URL + pattern);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        return response.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
    }

	private int countOccurrences(String str, String subStr) {
	  return (str.length() - str.replaceAll(Pattern.quote(subStr), "").length()) / subStr.length();
	}
}
