package crudboot.repository;

import crudboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>  {

    @Query("SELECT r FROM Role r WHERE role = :roleName")
    Role getRoleByName(@Param("roleName") String roleName);

}
