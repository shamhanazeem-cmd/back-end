package com.edu.Institiute.repo;

import com.edu.Institiute.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege,String> {

    @Query(value = "SELECT * FROM privilege WHERE id=:privilegeId", nativeQuery = true)
    Privilege findByIdForPrivilege(@Param("privilegeId") String privilegeId);

    @Query(value = "SELECT * FROM privilege WHERE id=:privilegeId", nativeQuery = true)
    List<Privilege> privilegeById(@Param("privilegeId") String privilegeId);

    @Query(value = "SELECT * FROM privilege as p where p.roles_id=:roleName and p.module_id=:moduleID", nativeQuery = true)
    List<Privilege> privilegeForComponent(@Param("roleName") String roleName, @Param("moduleID") String moduleID);

}
