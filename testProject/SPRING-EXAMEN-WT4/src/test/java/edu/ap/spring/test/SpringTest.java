package edu.ap.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import org.junit.Before;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {
    
	private String GET_URL = "http://localhost:8080/list?student=";
	private String POST_URL = "http://localhost:8080/new";
	
	@Autowired
	private Singleton singleton;
	
    @Before
    public void setUp() {}
        
    @Test
    public void testRepo() {
    		String params = "student=opiskelija1&exam=ylioppilastutkinto1&reason=peruste1";
    		this.doPost(params);
        String found =  this.doGet("opiskelija1");

        if(found.contains("opiskelija")) {
        		singleton.setGrade(4, "testRepo");
        }
    }
    
    @Test
    public void testUnique() {
    		this.doPost("student=opiskelija1&exam=ylioppilastutkinto1&reason=peruste1");
    		this.doPost("student=opiskelija1&exam=ylioppilastutkinto1&reason=peruste1");
    		this.doPost("student=opiskelija1&exam=ylioppilastutkinto1&reason=peruste1");
    		String found = this.doGet("opiskelija1");
    		
    		if(this.countOccurrences(found, "opiskelija1") < 2) {
    			singleton.setGrade(3, "testUnique");
    		}
    }
    
    @Test
    public void testList() {
    		for(int i = 2; i < 5; i++) {
    			this.doPost("student=opiskelija" + i + "&exam=ylioppilastutkinto" + i + "&reason=peruste" + i);
    		}
    		String found = this.doGet("opiskelija2") + this.doGet("opiskelija3");
    		
    		if(found.contains("ylioppilastutkinto2") && found.contains("ylioppilastutkinto3")) {
    			singleton.setGrade(4, "testList");
    		}
     }
       
    @Test
    public void testSort() {
    		try {
        		this.doPost("student=opiskelija5&exam=ylioppilastutkinto5&reason=" + "DFG");
        		this.doPost("student=opiskelija5&exam=ylioppilastutkinto5&reason=" + "ZBS");
        		this.doPost("student=opiskelija5&exam=ylioppilastutkinto5&reason=" + "AEG");
        		this.doPost("student=opiskelija5&exam=ylioppilastutkinto5&reason=" + "FFH");
        		
        		String found = this.doGet("opiskelija5");
        		int a = found.indexOf("AEG");
        		int d = found.indexOf("DFG");
        		int f = found.indexOf("FFH");
        		int z = found.indexOf("ZBS");
        		
        		if(a < d && a < f && a < z && z > f && z > d && d < f) {
        			singleton.setGrade(3, "testSort");
        		}
    		}
    		catch(Exception ex) {
    			System.out.println(ex.getMessage());
    		}
    }
    
    @Test
    public void getFileChanged() {
    		String path = SpringTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    		File f = new File(path.substring(0, path.indexOf("/target")) + "/src/test/java/edu/ap/spring/test/SpringTest.java");
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		System.out.println("SpringTest last modified : " + sdf.format(f.lastModified()));
    }
     
    private String doGet(String student) {

        URL obj;
		try {
			obj = new URL(this.GET_URL + student);
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
    
    private String doPost(String urlParameters) {
    		
    		try {
    			URL obj = new URL(this.POST_URL);
    			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    			con.setRequestMethod("POST");
    			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    			con.setDoOutput(true);
    			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    			wr.writeBytes(urlParameters);
    			wr.flush();
    			wr.close();

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
