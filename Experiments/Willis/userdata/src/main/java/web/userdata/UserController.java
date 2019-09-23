package web.userdata;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserController extends PagingAndSortingRepository<User, Long>
{
  List<User> findByLastName(@Param("name") String name);
  List<User> findByFirstName(@Param("name") String name);
  List<User> findByEmail(@Param("email") String email);
}
