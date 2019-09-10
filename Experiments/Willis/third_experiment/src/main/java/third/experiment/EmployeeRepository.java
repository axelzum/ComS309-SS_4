package third.experiment;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
  // Don't need to implement because Spring Data JPA understand what to do. As long as parameters are named
  // the same as the instance variables of the object (I think)
  List<Employee> findByLastName(String lastName);
  List<Employee> findByFirstName(String firstName);
}
