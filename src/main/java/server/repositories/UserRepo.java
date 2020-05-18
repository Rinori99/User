package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findByEmail(String email);

    @Query(value = "UPDATE user SET password=?2 WHERE id=?1", nativeQuery = true)
    User updateUserPassword(String userId, String password);



}
