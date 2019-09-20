package mysql.database.user;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer>
{
  Student findByUserName(String userName);

  Iterable<Student> findAllByFirstName(String firstName);

  Iterable<Student> findAllByLastName(String lastName);

  Iterable<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
}
