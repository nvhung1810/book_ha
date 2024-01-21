package book.be.gau.book_ha.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import book.be.gau.book_ha.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  Object save = null;

  // u: is Customer, t: is Token
  @Query(value = """
      select t from Token t inner join Customer u\s
      on t.customer.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(@Param("id") Integer id);

  Optional<Token> findByToken(String token);
}
