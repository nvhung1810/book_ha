package book.be.gau.book_ha.configs.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import book.be.gau.book_ha.models.Customer;

public class ApplicationAuditAware implements AuditorAware<Integer> {

  @Override
  public Optional<Integer> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();

    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    Customer customerPrincipal = (Customer) authentication.getPrincipal();
    return Optional.ofNullable(customerPrincipal.getId());
  }
}
