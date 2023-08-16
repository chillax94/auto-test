package com.auto.company.service;

import com.auto.common.service.BaseService;
import com.auto.common.utils.IdWorker;
import com.auto.company.dao.DepartmentDao;
import com.auto.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department) {
        //设置主键的值
        String id = idWorker.nextId()+"";
        department.setId(id);

        department.setCompanyId("1");
        //调用dao保存部门
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department) {
        Department dept = departmentDao.findById(department.getId()).get();
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        departmentDao.save(dept);
    }

    /**
     * 根据id查询部门
     */
    public Department findById(String id) {
        return departmentDao.findById(id).get();
    }

    /**
     * 查询全部部门列表
     */
    public List<Department> findAll(String companyId) {
        return departmentDao.findAll(getSpec(companyId));
    }

    /**
     * 根据id删除部门
     */
    public void deleteById(String id) {
        departmentDao.deleteById(id);
    }
}
