package com.masai.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Student;

@Repository
public interface Std_Repository extends ElasticsearchRepository<Student, Integer> {

	public List<Student> findBySemester(int semester);

}
