package com.masai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "product")
public class Student {

	@Id
	private Integer s_roll;
	private String s_name;
	private Integer english_marks;
	private Integer mathematics_marks;
	private Integer science_marks;
	private Integer semester;

}
