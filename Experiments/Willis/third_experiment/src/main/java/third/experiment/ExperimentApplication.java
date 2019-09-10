package third.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExperimentApplication
{
  private static final Logger log = LoggerFactory.getLogger(ExperimentApplication.class);

  public static void main(String[] args)
  {
    SpringApplication.run(ExperimentApplication.class, args);
  }

  @Bean
  public CommandLineRunner display(CustomerRepository customerRepository, EmployeeRepository employeeRepository)
  {
    return (args) -> {
      customerRepository.save(new Customer("Jane", "Schmoe"));
      customerRepository.save(new Customer("Jane", "Doe"));
      customerRepository.save(new Customer("Willis", "Knox"));
      customerRepository.save(new Customer("Axel", "Zumwalt"));
      customerRepository.save(new Customer("Morgan", "Smith"));
      customerRepository.save(new Customer("Brett", "Santema"));
      customerRepository.save(new Customer("Joe", "Schmoe"));
      customerRepository.save(new Customer("Schmoe", "Grane"));

      employeeRepository.save(new Employee("Tian","Meegs"));
      employeeRepository.save(new Employee("Willis", "Knox"));
      employeeRepository.save(new Employee("Axel", "Zumwalt"));
      employeeRepository.save(new Employee("Morgan", "Smith"));
      employeeRepository.save(new Employee("Brett", "Santema"));
      employeeRepository.save(new Employee("John","Ryner"));


      //display all customers
      log.info("All Customers in repository");
      log.info("---------------------------");
      for (Customer cus : customerRepository.findAll())
      {
        log.info(cus.toString());
      }
      log.info("\n");

      //display all customers with last name "Schmoe"
      log.info("All Customers in repository with last name Schmoe");
      log.info("-------------------------------------------------");
      for (Customer cus : customerRepository.findByLastName("Schmoe"))
      {
        log.info(cus.toString());
      }
      log.info("\n");

      //display all customers with first name "Jane"
      log.info("All Customers in repository with first name Jane");
      log.info("------------------------------------------------");
      for (Customer cus : customerRepository.findByFirstName("Jane"))
      {
        log.info(cus.toString());
      }
      log.info("\n");

      //display all customers with first name "Schmoe"
      log.info("All Customers in repository with first name Schmoe");
      log.info("--------------------------------------------------");
      for (Customer cus : customerRepository.findByFirstName("Schmoe"))
      {
        log.info(cus.toString());
      }
      log.info("\n");


      //display all employees and their values before seeing if they are customers
      log.info("All Employees in repository");
      log.info("---------------------------");
      for (Employee emp : employeeRepository.findAll())
      {
        log.info(emp.toString());
      }
      log.info("\n");

      //display all employees and their values. Updates employees customer value to true
      //if the employee shops at the store
      log.info("Update Employees to see if they are Customers too");
      log.info("-------------------------------------------------");
      for (Employee emp : employeeRepository.findAll())
      {
        for(Customer c : customerRepository.findAll())
        {
          if(c.getFirstName().equals(emp.getFirstName()) && c.getLastName().equals(emp.getLastName()))
          {
            emp.setCustomer(true);
          }
        }
        log.info("Employee updated: " + emp.toString());
      }
      log.info("\n");

    };
  }
}
