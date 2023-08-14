package com.auto.company.controller;

import com.auto.common.controller.BaseController;
import com.auto.common.entity.Result;
import com.auto.common.entity.ResultCode;
import com.auto.company.service.CompanyService;
import com.auto.company.service.DepartmentService;
import com.auto.domain.company.Company;
import com.auto.domain.company.Department;
import com.auto.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/company")
public class DepartmentController extends BaseController{

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;
    /**
     * 保存
     */
    @RequestMapping(value="/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业id
     */
    @RequestMapping(value="/department",method = RequestMethod.GET)
    public Result findAll() {
        String companyId = "1";
        Company company = companyService.findById(companyId);
        List<Department> list = departmentService.findAll(companyId);
        DeptListResult deptListResult = new DeptListResult(company,list);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    /**
     * 修改Department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id,@RequestBody Department department) {
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }


}
