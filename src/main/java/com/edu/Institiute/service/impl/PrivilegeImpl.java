package com.edu.Institiute.service.impl;

import com.edu.Institiute.dto.PrivilegeDto;
import com.edu.Institiute.dto.requestDto.PrivilegeRequestDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.PrivilegeResponseDto;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponsePrivilegeDto;
import com.edu.Institiute.entity.*;
import com.edu.Institiute.entity.Module;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.ModuleRepo;
import com.edu.Institiute.repo.PrivilegeRepo;
import com.edu.Institiute.repo.RoleRepo;
import com.edu.Institiute.service.PrivilegeService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.ModuleMapper;
import com.edu.Institiute.utill.mapper.PrivilegeMapper;
import com.edu.Institiute.utill.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrivilegeImpl implements PrivilegeService {

    @Autowired
    private Generator generator;

    @Autowired
    private PrivilegeRepo privilegeRepo;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleRepo moduleRepo;

    @Override
    public CommonResponseDto savePrivilege(PrivilegeRequestDto dto) {
        try {
            System.out.println(dto);

            String privilegeId = "P" + "-" + generator.generateFourNumbers();
            Optional<Role> role = roleRepo.findById(dto.getRoleId());
            Optional<Module> module = moduleRepo.findById(dto.getModuleId());

            PrivilegeDto privilegeDto = new PrivilegeDto(
                    privilegeId,
                    roleMapper.toRoleSetDto(role.get()),
                    moduleMapper.toModuleDto(module.get()),
                    dto.getSel(),
                    dto.getIns(),
                    dto.getUpd(),
                    dto.getDel()

            );
            privilegeRepo.save(privilegeMapper.dtoToPrivilegeEntity(privilegeDto));

            return new CommonResponseDto(201, "Privilege saved!", privilegeDto.getModuleId(), new ArrayList<>());
        }catch (Exception e){
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e);
        }
    }

    @Override
    public CommonResponseDto updatePrivilege(PrivilegeRequestDto dto, String privilegeId) {
        try {
            System.out.println("=== START UPDATE PRIVILEGE ===");
            System.out.println("Received privilegeId: " + privilegeId);
            System.out.println("Received DTO: " + dto);
            System.out.println("DTO roleId: " + dto.getRoleId());
            System.out.println("DTO moduleId: " + dto.getModuleId());

            // 1. Find the existing privilege
            Privilege allPrivileges = privilegeRepo.findByIdForPrivilege(privilegeId);
            System.out.println("Found privilege: " + allPrivileges);

            if (allPrivileges == null) {
                System.out.println("ERROR: Privilege not found with ID: " + privilegeId);
                throw new EntryNotFoundException("Privilege not found with ID: " + privilegeId);
            }

            // 2. Find module
            Optional<Module> module = moduleRepo.findById(dto.getModuleId());
            System.out.println("Module found: " + module.isPresent());
            if (module.isEmpty()) {
                System.out.println("ERROR: Module not found with ID: " + dto.getModuleId());
                throw new EntryNotFoundException("Module not found");
            }

            // 3. Find role
            Optional<Role> role = roleRepo.findById(dto.getRoleId());
            System.out.println("Role found: " + role.isPresent());
            if (role.isEmpty()) {
                System.out.println("ERROR: Role not found with ID: " + dto.getRoleId());
                throw new EntryNotFoundException("Role not found");
            }

            // 4. Update the privilege
            allPrivileges.setModuleId(module.get());
            allPrivileges.setRoleId(role.get());
            allPrivileges.setSel(dto.getSel());
            allPrivileges.setIns(dto.getIns());
            allPrivileges.setUpd(dto.getUpd());
            allPrivileges.setDel(dto.getDel());

            System.out.println("Updated privilege: " + allPrivileges);

            // 5. Save
            Privilege savedPrivilege = privilegeRepo.save(allPrivileges);
            System.out.println("Saved privilege ID: " + savedPrivilege.getId());

            System.out.println("=== END UPDATE PRIVILEGE ===");
            return new CommonResponseDto(201, "privilege updated!", savedPrivilege.getId(), new ArrayList<>());

        } catch (Exception e) {
            System.out.println("ERROR in updatePrivilege: " + e.getMessage());
            e.printStackTrace();
            throw new EntryNotFoundException("Can't Save because of this Error -->  " + e.getMessage());
        }
    }

    @Override
    public PaginatedResponsePrivilegeDto privilegeById(String privilegeCode) throws SQLException {
        try {
            List<Privilege> privilegeByID = privilegeRepo.privilegeById(privilegeCode);
            System.out.println(privilegeByID);
            List<PrivilegeResponseDto> privilegeResponseDto = new ArrayList<>();

            for (Privilege p : privilegeByID) {
                privilegeResponseDto.add(
                        new PrivilegeResponseDto(
                                p.getId(),
                                roleMapper.toRoleSetDto(p.getRoleId()),
                                moduleMapper.toModuleDto(p.getModuleId()),
                                p.getSel(),
                                p.getIns(),
                                p.getUpd(),
                                p.getDel()
                        )
                );
            }

            return new PaginatedResponsePrivilegeDto(
                    privilegeRepo.count(),
                    privilegeResponseDto
            );
        }catch (Exception e){
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponsePrivilegeDto allPrivilege() throws SQLException {
        try {
            List<Privilege> allPrivilege = privilegeRepo.findAll();
            List<PrivilegeResponseDto> privilegeResponseDto = new ArrayList<>();

            for (Privilege r : allPrivilege) {
                privilegeResponseDto.add(
                        new PrivilegeResponseDto(
                                r.getId(),
                                roleMapper.toRoleSetDto(r.getRoleId()),
                                moduleMapper.toModuleDto(r.getModuleId()),
                                r.getSel(),
                                r.getIns(),
                                r.getUpd(),
                                r.getDel()
                        )
                );
            }
            return new PaginatedResponsePrivilegeDto(
                    privilegeRepo.count(),
                    privilegeResponseDto
            );
        }catch (Exception e){
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }

    @Override
    public PaginatedResponsePrivilegeDto getPrivilegeForComponent(String roleName, String moduleID) throws SQLException {
        try {
            List<Privilege> allPrivilegeForComponent = privilegeRepo.privilegeForComponent(roleName,moduleID);
            List<PrivilegeResponseDto> privilegeResponseDto = new ArrayList<>();

            for (Privilege r : allPrivilegeForComponent) {
                privilegeResponseDto.add(
                        new PrivilegeResponseDto(
                                r.getId(),
                                roleMapper.toRoleSetDto(r.getRoleId()),
                                moduleMapper.toModuleDto(r.getModuleId()),
                                r.getSel(),
                                r.getIns(),
                                r.getUpd(),
                                r.getDel()
                        )
                );
            }
            return new PaginatedResponsePrivilegeDto(
                    privilegeRepo.count(),
                    privilegeResponseDto
            );
        }catch (Exception e){
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }
}
