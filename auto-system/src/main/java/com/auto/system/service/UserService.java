package com.auto.system.service;

import com.auto.common.utils.IdWorker;
import com.auto.domain.system.Role;
import com.auto.domain.system.User;
import com.auto.system.dao.RoleDao;
import com.auto.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 根据mobile查询用户
     */
    public User findByUsername(String mobile) {
        return userDao.findByUsername(mobile);
    }

    /**
     * 保存用户
     */
    public void save(User user) {
        String id = idWorker.nextId() + "";
        user.setPassword("123456");
        user.setEnableState(1);
        user.setId(id);
        userDao.save(user);
    }

    /**
     * 更新用户
     */
    public void update(User user) {
        User target = userDao.findById(user.getId()).get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        userDao.save(target);
    }

    /**
     * 根据id查询用户
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 查询全部用户列表
     */
    public Page findAll(Map<String, Object> map, int page, int size) {
        Specification<User> spec = new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的companyId是否为空构造查询条件
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), (String) map.get("departmentId")));
                }
                if (!StringUtils.isEmpty(map.get("mobile"))) {
                    list.add(criteriaBuilder.equal(root.get("mobile").as(String.class), (String) map.get("mobile")));
                }
                if (!StringUtils.isEmpty(map.get("username"))) {
                    list.add(criteriaBuilder.equal(root.get("username").as(String.class), (String) map.get("username")));
                }
                if (!StringUtils.isEmpty(map.get("departmentName"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentName").as(String.class), (String) map.get("departmentName")));
                }
                if (!StringUtils.isEmpty(map.get("workNumber"))) {
                    list.add(criteriaBuilder.equal(root.get("workNumber").as(String.class), (String) map.get("workNumber")));
                }
                if (!StringUtils.isEmpty(map.get("hasDept"))) {
                    //根据请求的hasDept判断  是否分配部门 0未分配（departmentId = null），1 已分配 （departmentId ！= null）
                    if ("0".equals((String) map.get("hasDept"))) {
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    } else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        Page<User> pageUser = userDao.findAll(spec, new PageRequest(page - 1, size));
        return pageUser;
    }

    /**
     * 根据id删除用户
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId, List<String> roleIds) {
        User user = userDao.findById(userId).get();
        //设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色集合的关系
        user.setRoles(roles);
        userDao.save(user);
    }
}
