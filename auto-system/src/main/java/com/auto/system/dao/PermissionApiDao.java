package com.auto.system.dao;



import com.auto.domain.system.PermissionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
  * 企业数据访问接口
  */
public interface PermissionApiDao extends JpaRepository<PermissionApi, String>, JpaSpecificationExecutor<PermissionApi> {

}