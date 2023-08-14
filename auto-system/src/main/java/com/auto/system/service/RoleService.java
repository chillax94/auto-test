package com.auto.system.service;

import com.auto.common.service.BaseService;
import com.auto.common.utils.IdWorker;
import com.auto.common.utils.PermissionConstants;
import com.auto.domain.system.Permission;
import com.auto.domain.system.Role;
import com.auto.system.dao.PermissionDao;
import com.auto.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 角色操作业务逻辑层
 */
@Service
public class RoleService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 分配权限
     */
    public void assignPerms(String roleId,List<String> permIds) {
        Role role = roleDao.findById(roleId).get();
        //构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PERMISSION_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        System.out.println(perms.size());
        //设置角色和权限的关系
        role.setPermissions(perms);
        roleDao.save(role);
    }

    /**
     * 添加角色
     */
    public void save(Role role) {
        //填充其他参数
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 更新角色
     */
    public void update(Role role) {
        Role targer = roleDao.getOne(role.getId());
        targer.setDescription(role.getDescription());
        targer.setName(role.getName());
        roleDao.save(targer);
    }

    /**
     * 根据ID查询角色
     */
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }

    public List<Role> findAll(String companyId) {
        return roleDao.findAll(getSpec(companyId));
    }

    /**
     * 删除角色
     */
    public void delete(String id) {
        roleDao.deleteById(id);
    }

    public Page<Role> findByPage(String companyId, int page, int size) {
        return roleDao.findAll(getSpec(companyId), PageRequest.of(page-1, size));
    }

}
