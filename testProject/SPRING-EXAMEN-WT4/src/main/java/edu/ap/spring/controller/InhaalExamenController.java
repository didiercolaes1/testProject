package edu.ap.spring.controller;

import java.security.PrivateKey;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import edu.ap.spring.model.*;
import edu.ap.spring.redis.RedisService;

@Controller
@Scope("session")
public class InhaalExamenController {

   private RedisService service; // pattern : "exams":exam:studentv:stA:stutel:reason:date
		
   @Autowired
   public void setRedisService(RedisService service) {
	   this.service = service;
   }

   public InhaalExamenController() {}
 
   @RequestMapping("/list")
   @ResponseBody
   public String listExams(Student student) {
//	   http://localhost:8080/list?voornaam=v1&Achternaam=a1&telefoon=tel
	   List<InhaalExamen> exams = new ArrayList<InhaalExamen>();
	   Set<String> keys = service.keys("exams:*");
	   for(String key : keys) {
		   String[] parts = key.split(":");
		   if(parts[2].equalsIgnoreCase(student.getVoornaam())) {
			   exams.add(new InhaalExamen(new Student(parts[2], parts[3], parts[4]), parts[1], parts[5], parts[6]));
		   }
	   }
	   exams.sort(Comparator.comparing(InhaalExamen::getReason));
	   
	   StringBuilder b = new StringBuilder();
	   b.append("<html><body><table>");
	   b.append("<tr><th>Datum</th><th>Student</th><th>Examen</th><th>Reden</th></tr>");
	   
	   for(InhaalExamen ex : exams) {
		   b.append("<tr>");
		   		b.append("<td>");
		   			b.append(ex.getDate());
		   		b.append("</td>");
		   		b.append("<td>");
		   			b.append(ex.getStudent().getVoornaam());
		   		b.append("</td>");
               b.append("<td>");
               b.append(ex.getStudent().getAchternaam());
               b.append("</td>");
               b.append("<td>");
               b.append(ex.getStudent().getTelefoon());
               b.append("</td>");
		   		b.append("<td>");
		   			b.append(ex.getExam());
		   		b.append("</td>");
		   		b.append("<td>");
		   			b.append(ex.getReason());
		   		b.append("</td>");
		   b.append("</tr>");
	   }
	   b.append("</table></body></html>");
	   
	   return b.toString();
   }
   
   @RequestMapping("/listall")
   @ResponseBody
   public String listAllExams() {
	   List<InhaalExamen> exams = new ArrayList<InhaalExamen>();
	   Set<String> keys = service.keys("exams:*");
	   for(String key : keys) {
		   String[] parts = key.split(":");
		   exams.add(new InhaalExamen(new Student(parts[2], parts[3], parts[4]), parts[1], parts[5], parts[6]));
	   }
	   
	   StringBuilder b = new StringBuilder();
	   b.append("<html><body><table>");
	   b.append("<tr><th>Datum</th><th>Student</th><th>Examen</th><th>Reden</th></tr>");
	   
	   for(InhaalExamen ex : exams) {
           b.append("<tr>");
           b.append("<td>");
           b.append(ex.getDate());
           b.append("</td>");
           b.append("<td>");
           b.append(ex.getStudent().getVoornaam());
           b.append("</td>");
           b.append("<td>");
           b.append(ex.getStudent().getAchternaam());
           b.append("</td>");
           b.append("<td>");
           b.append(ex.getStudent().getTelefoon());
           b.append("</td>");
           b.append("<td>");
           b.append(ex.getExam());
           b.append("</td>");
           b.append("<td>");
           b.append(ex.getReason());
           b.append("</td>");
           b.append("</tr>");
	   }
	   b.append("</table></body></html>");
	   
	   return b.toString();
   }
   
   @GetMapping("/new")
   @ResponseBody
   public String makeReservation(Student student,
		   						@RequestParam("exam") String exam,
		   						@RequestParam("reason") String reason) {
	   // localhost:8080/new?voornaam=v1&Achternaam=a1&telefoon=tel&exam=e1&reason=smooi
	   String pattern = "exams:" + exam + ":" + student.getVoornaam() + ":" + reason + ":*";
	   Set<String> keys = service.keys(pattern);
	   if(keys.size() == 0) {
		   InhaalExamen newExam = new InhaalExamen(student, exam, reason);
		   service.setKey("exams:" + exam + ":" + student.getVoornaam() + ":" + student.getAchternaam() + ":" + student.getTelefoon() + ":" + reason + ":" + newExam.getDate(), "");
		   return "InhaalExamen toegevoegd";
	   }
	   else {
		   return "InhaalExamen bestaat reeds!";
	   }
   }
}