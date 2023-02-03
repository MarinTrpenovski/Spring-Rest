package com.springgoals.service.impl;

import com.springgoals.dao.impl.StudentDAOImpl;
import com.springgoals.model.Student;
import com.springgoals.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAOImpl studentDAO;

    @Override
    public Student getById(Integer id) throws SQLException {
        Student student = studentDAO.getById(id);
        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        return studentDAO.getAll();
    }

    @Override
    public void update(Student student) throws SQLException {
        studentDAO.update(student);
    }

    @Override
    public void save(Student student) throws SQLException {
        studentDAO.save(student);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        studentDAO.delete(id);
    }
}
