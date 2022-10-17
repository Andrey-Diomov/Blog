package by.diomov.blog.repository;

import by.diomov.blog.model.ERole;
import by.diomov.blog.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
