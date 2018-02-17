package io.github.devbhuwan.data.model.repository;

import io.github.devbhuwan.data.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, QueryByExampleExecutor<Student> {
}
