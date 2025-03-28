package tqs.lab6_1;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
public class TestContainerIT {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Container
    @Order(1)
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
        .withUsername("test")
        .withPassword("test")
        .withDatabaseName("test");

    @DynamicPropertySource
    @Order(2)
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }
    
    @Test
    @Order(3)
    public void addEmployeeTest() {
        Employee employee = new Employee(1, "John", "IT", 1000, 30, "johnIT@ua.pt");
        employeeRepo.save(employee);

        Employee employee1 = employeeRepo.findById(1).get();
        assertEquals(employee1.getName(), "John");
        assertEquals(employee1.getDepartment(), "IT");
    }

    @Test
    @Order(4)
    public void updateEmployeeTest() {
        Employee employee1 = employeeRepo.findById(1).get();
        employee1.setDepartment("HR");
        employeeRepo.save(employee1);

        Employee employee2 = employeeRepo.findById(1).get();
        assertEquals(employee2.getDepartment(), "HR");
    }

    @Test
    @Order(5)
    public void deleteEmployeeTest() {
        Employee employee1 = employeeRepo.findById(1).get();
        employeeRepo.delete(employee1);

        assertThat(employeeRepo.findById(1)).isEmpty();;
    }
}
