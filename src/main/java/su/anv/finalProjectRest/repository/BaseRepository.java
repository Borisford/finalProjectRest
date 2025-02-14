package su.anv.finalProjectRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.anv.finalProjectRest.entity.Base;
import su.anv.finalProjectRest.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseRepository extends JpaRepository<Base, UUID> {

}
