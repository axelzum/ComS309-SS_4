package third.experiment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private Boolean customer;

  protected Employee()
  {
  }

  public Employee(String fName, String lName)
  {
    firstName = fName;
    lastName = lName;
    customer = false;
  }

  public Long getId()
  {
    return id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName1)
  {
    firstName = firstName1;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName1)
  {
    lastName = lastName1;
  }

  public Boolean getCustomer()
  {
    return customer;
  }

  public void setCustomer(Boolean cust)
  {
    customer = cust;
  }

  @Override
  public String toString()
  {
    return String.format("Employee[id=%d, firstName='%s', lastName='%s', customer='%b']", id, firstName, lastName,
                         customer);
  }
}
