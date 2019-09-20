package mysql.database.user;

import org.springframework.data.repository.CrudRepository;

/**
 * This is the interface for search the MySQL database (making queries). I do not need to implement these methods
 * because Spring Boot is smart enough to know how to implement them automatically. If the time arises that we need a
 * complex query (and it will), we will need to implement them, but we will cross that bridge when we get there.
 */
public interface StudentRepository extends CrudRepository<Student, Integer>
{
  Student findByUserName(String userName);

  Iterable<Student> findAllByFirstName(String firstName);

  Iterable<Student> findAllByLastName(String lastName);

  Iterable<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
}
