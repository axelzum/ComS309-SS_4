package third.experiment;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
  // Don't need to implement because Spring Data JPA understand what to do. As long as parameters are named
  // the same as the instance variables of the object (I think)
  List<Customer> findByLastName(String lastName);
  List<Customer> findByFirstName(String firstName);
}
