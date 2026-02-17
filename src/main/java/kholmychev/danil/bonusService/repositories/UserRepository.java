package kholmychev.danil.bonusService.repositories;

import kholmychev.danil.bonusService.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	Optional<User> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.userRole = :role WHERE u.id = :id")
	void updateRoleById(@Param("role") String role, @Param("id") int id);
}