package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.models.PasswordRecovery;

@Repository
public interface PasswordRecoveryRepo extends JpaRepository<PasswordRecovery, String> {

    @Query(value = "UPDATE password_recovery SET used = ?2 WHERE id = ?1", nativeQuery = true)
    void updatePasswordRecoveryLinkStatus(String passwordRecoveryId, boolean hasBeenUsed);

}
