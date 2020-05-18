package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.models.ParentStudentConnectionRequest;

import java.util.List;

@Repository
public interface ParentStudentConnectionRequestRepo extends JpaRepository<ParentStudentConnectionRequest, String> {

    List<ParentStudentConnectionRequest> findBySchoolId(String schoolId);

}
