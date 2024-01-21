package book.be.gau.book_ha.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import book.be.gau.book_ha.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  @Query("SELECT c FROM Customer c WHERE c.customer_staff_email = ?1")
  Optional<Customer> findByCustomerStaffEmail(String customerStaffEmail);
}
