package su.anv.finalProjectRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import su.anv.finalProjectRest.entity.Request;
import su.anv.finalProjectRest.entity.Ticker;
import su.anv.finalProjectRest.entity.User;
import su.anv.finalProjectRest.entity.UserRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, UUID> {
    Optional<UserRequest> findByUserAndRequest(User user, Request request);
    Optional<List<UserRequest>> findByUser(User user);
    Optional<List<UserRequest>> findAllByUser(User user);



}
