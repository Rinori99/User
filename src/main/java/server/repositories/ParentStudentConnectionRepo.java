package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.models.ParentStudentConnection;
import server.models.User;

import java.util.List;

@Repository
public interface ParentStudentConnectionRepo extends JpaRepository<ParentStudentConnection, String> {

    List<ParentStudentConnection> findByParentId(User parentId);

}
