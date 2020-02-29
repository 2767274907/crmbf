package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.repository.RightRepostitory;
import com.t248.lmf.crm.repository.RoleRepository;
import com.t248.lmf.crm.repository.SysRoleRightRepostitor;
import com.t248.lmf.crm.service.IRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("iRoleService")
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RightRepostitory rightRepostitory;

    @Resource
    SysRoleRightRepostitor sysRoleRightRepostitor;

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRolebyUser(User user) {
        return roleRepository.findRoleByUsers(user);
    }

    @Override
    public Role getRoleByroleId(Long roleId) {
        return roleRepository.getOne(roleId);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }


    @Override
    public Page<Role> findRoles(String roleName , Pageable pageable) {
        return roleRepository.findAll(new Specification<Role>(){
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (roleName != null && roleName != "") {
                    predicates.add(criteriaBuilder.like(root.get("roleName"),"%"+roleName+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
    }

    @Override
    public List<Right> findAllRights() {
        return rightRepostitory.findAll();
    }

    @Override
    public List<Role> findRolesByNameLink(String roleName) {
        return roleRepository.findRolesByRoleNameLike("%"+roleName+"%");
    }

    @Override
    public void deleRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public void delSysRoleRight(Long roleId) {
        sysRoleRightRepostitor.deleteByRole(roleRepository.getOne(roleId));
    }
}
