package com.masai.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.masai.model.Student;
import com.masai.repository.Std_Repository;

@Controller
public class studentController {

	@Autowired
	private Std_Repository sRepo;

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";

	}

	@GetMapping("/saveStudent")
	public String saveStudent() {
		return "saveStudent";

	}

	@PostMapping("/saveStudent")
	public String saveStudent(@RequestParam Integer s_roll, @RequestParam String s_name,
			@RequestParam Integer english_marks,@RequestParam Integer mathematics_marks, @RequestParam Integer science_marks, 
			@RequestParam Integer semester) {
		
		Student student = new Student(s_roll, s_name, english_marks, mathematics_marks, science_marks,semester);
		
		sRepo.save(student);
		
		return "welcome";

	}

	@GetMapping("/getPercentage")
	public String getPercentageView() {
		return "getPercentage";
	}

	@PostMapping("/getPercentage")
	public String getPercentageView(Model model, @RequestParam Integer semester) {
		int sum = 0;
		int count = 0;
		List<Student> student = sRepo.findBySemester(semester);
		for (Student st : student) {
			count++;
			sum = sum + st.getEnglish_marks() + st.getMathematics_marks() +st.getScience_marks();
		}
		sum = sum / count;
		sum = sum * 100 / 300;
		model.addAttribute("avg", sum);
		
		return "getPercentageResult";
	}

	@GetMapping("/avgMarks")
	
	public String avgMarks() {

		return "avgMarks";
	}

	@PostMapping("/avgMarks")
	public String avgMarks(Model model, @RequestParam String subjects) {
		
		Iterable<Student> itr = sRepo.findAll();
		
		List<Student> students = new ArrayList<>();
		
		itr.forEach(students::add);
		int count = 0;
		int sum = 0;
		
		for (Student st : students) {
			System.out.println(st);
			
			count++;
			
			 if (subjects.equalsIgnoreCase("english")) {
				sum += st.getEnglish_marks();
			} else if (subjects.equalsIgnoreCase("mathematics")) {
				sum += st.getMathematics_marks();
			} else if (subjects.equalsIgnoreCase("science")) {
				sum += st.getScience_marks();
			}
		}
		model.addAttribute("avgMarks", sum/count);
		return "avgResult";
	}

	@GetMapping("/topTwo")
	public String topView() {
		return "topTwo";
	}

	@PostMapping("/topTwo")
	public String topView(Model model) {
		Iterable<Student> itr = sRepo.findAll();

		List<Student> students = new ArrayList<>();
		itr.forEach(students::add);
		
		HashMap<String, Integer> map = new HashMap<>();
		
		for (Student st : students) {
			map.put(st.getS_name(), (st.getMathematics_marks() + st.getEnglish_marks() + st.getScience_marks()) / 3);
		}

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}

		});

		List<String> st = new ArrayList<>();
		int count = 1;
		for (Map.Entry<String, Integer> name : list) {
			if (count <= 2) {
				st.add(name.getKey());
				count++;
			}

		}

		model.addAttribute("top2", st);
		return "topTwoResult";
	}
}
