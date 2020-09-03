package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.models.PasswordHistory;

import java.util.List;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, String> {

    @Query(value = "SELECT * FROM password_history WHERE user_id=?1 ORDER BY date_created DESC LIMIT ?2", nativeQuery = true)
    List<PasswordHistory> findLastNPasswordsByUser(String userId, int n);

}
