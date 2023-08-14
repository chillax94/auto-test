CREATE TABLE `co_company`
(
    `id`                   varchar(40) NOT NULL COMMENT 'ID',
    `name`                 varchar(255) DEFAULT NULL COMMENT '公司名称',
    `manager_id`           varchar(255) DEFAULT NULL COMMENT '企业登录账号ID',
    `version`              varchar(255) DEFAULT NULL COMMENT '当前版本',
    `renewal_date`         datetime     DEFAULT NULL COMMENT '续期时间',
    `expiration_date`      datetime     DEFAULT NULL COMMENT '到期时间',
    `company_area`         varchar(255) DEFAULT NULL COMMENT '公司地区',
    `company_address`      text COMMENT '公司地址',
    `business_license_id`  varchar(255) DEFAULT NULL COMMENT '营业执照-图片ID',
    `legal_representative` varchar(255) DEFAULT NULL COMMENT '法人代表',
    `company_phone`        varchar(255) DEFAULT NULL COMMENT '公司电话',
    `mailbox`              varchar(255) DEFAULT NULL COMMENT '邮箱',
    `company_size`         varchar(255) DEFAULT NULL COMMENT '公司规模',
    `industry`             varchar(255) DEFAULT NULL COMMENT '所属行业',
    `remarks`              text COMMENT '备注',
    `audit_state`          varchar(255) DEFAULT NULL COMMENT '审核状态',
    `state`                tinyint(2)   DEFAULT '1' COMMENT '状态',
    `balance`              double       DEFAULT NULL COMMENT '当前余额',
    `create_time`          datetime     DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;


CREATE TABLE `bs_user`
(
    `id`                 varchar(40)  NOT NULL COMMENT 'ID',
    `mobile`             varchar(40)  NOT NULL COMMENT '手机号码',
    `username`           varchar(255) NOT NULL COMMENT '用户名称',
    `password`           varchar(255) DEFAULT NULL COMMENT '密码',
    `enable_state`       int(2)       DEFAULT '1' COMMENT '启用状态 0是禁用，1是启用',
    `create_time`        datetime     DEFAULT NULL COMMENT '创建时间',
    `department_id`      varchar(40)  DEFAULT NULL COMMENT '部门ID',
    `time_of_entry`      datetime     DEFAULT NULL COMMENT '入职时间',
    `form_of_employment` int(1)       DEFAULT NULL COMMENT '聘用形式',
    `work_number`        varchar(20)  DEFAULT NULL COMMENT '工号',
    `form_of_management` varchar(8)   DEFAULT NULL COMMENT '管理形式',
    `working_city`       varchar(16)  DEFAULT NULL COMMENT '工作城市',
    `correction_time`    datetime     DEFAULT NULL COMMENT '转正时间',
    `in_service_status`  int(1)       DEFAULT NULL COMMENT '在职状态 1.在职  2.离职',
    `company_id`         varchar(40)  DEFAULT NULL COMMENT '企业ID',
    `company_name`       varchar(40)  DEFAULT NULL,
    `department_name`    varchar(40)  DEFAULT NULL,
    `level`              varchar(40)  DEFAULT NULL COMMENT '用户级别：saasAdmin，coAdmin，user',
    `staff_photo`        mediumtext,
    `time_of_dimission`  datetime     DEFAULT NULL COMMENT '离职时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_user_phone` (`mobile`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `co_department`
(
    `id`          varchar(40)  NOT NULL,
    `company_id`  varchar(255) NOT NULL COMMENT '企业ID',
    `pid`         varchar(255) DEFAULT NULL COMMENT '父级部门ID',
    `name`        varchar(255) DEFAULT NULL COMMENT '部门名称',
    `code`        varchar(255) DEFAULT NULL COMMENT '部门编码',
    `manager`     varchar(40)  DEFAULT NULL COMMENT '部门负责人',
    `introduce`   text COMMENT '介绍',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `manager_id`  varchar(255) DEFAULT NULL COMMENT '负责人ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `pe_permission`
(
    `id`          varchar(40) NOT NULL COMMENT '主键',
    `description` text COMMENT '权限描述',
    `name`        varchar(255) DEFAULT NULL COMMENT '权限名称',
    `type`        tinyint(4)   DEFAULT NULL COMMENT '权限类型 1为菜单 2为功能 3为API',
    `pid`         varchar(40)  DEFAULT '0' COMMENT '主键',
    `code`        varchar(20)  DEFAULT NULL,
    `en_visible`  int(1)       DEFAULT NULL COMMENT '企业可见性 0：不可见，1：可见',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;


CREATE TABLE `pe_permission_api`
(
    `id`         varchar(40) NOT NULL COMMENT '主键ID',
    `api_level`  varchar(2)   DEFAULT NULL COMMENT '权限等级，1为通用接口权限，2为需校验接口权限',
    `api_method` varchar(255) DEFAULT NULL COMMENT '请求类型',
    `api_url`    varchar(255) DEFAULT NULL COMMENT '链接',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

/*Table structure for table `pe_permission_menu` */

CREATE TABLE `pe_permission_menu`
(
    `id`         varchar(40) NOT NULL COMMENT '主键ID',
    `menu_icon`  varchar(20) DEFAULT NULL COMMENT '权限代码',
    `menu_order` varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

/*Table structure for table `pe_permission_point` */

DROP TABLE IF EXISTS `pe_permission_point`;

CREATE TABLE `pe_permission_point`
(
    `id`           varchar(40) NOT NULL COMMENT '主键ID',
    `point_class`  varchar(20) DEFAULT NULL COMMENT '按钮类型',
    `point_icon`   varchar(20) DEFAULT NULL COMMENT '按钮icon',
    `point_status` int(11)     DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

/*Table structure for table `pe_role` */

CREATE TABLE `pe_role`
(
    `id`          varchar(40) NOT NULL COMMENT '主键ID',
    `name`        varchar(40)  DEFAULT NULL COMMENT '权限名称',
    `description` varchar(255) DEFAULT NULL COMMENT '说明',
    `company_id`  varchar(40)  DEFAULT NULL COMMENT '企业id',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `UK_k3beff7qglfn58qsf2yvbg41i` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

/*Table structure for table `pe_role_permission` */

CREATE TABLE `pe_role_permission`
(
    `role_id`       varchar(40) NOT NULL COMMENT '角色ID',
    `permission_id` varchar(40) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
    KEY `FK74qx7rkbtq2wqms78gljv87a0` (`permission_id`) USING BTREE,
    KEY `FKee9dk0vg99shvsytflym6egxd` (`role_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

/*Table structure for table `pe_user_role` */

DROP TABLE IF EXISTS `pe_user_role`;

CREATE TABLE `pe_user_role`
(
    `role_id` varchar(40) NOT NULL COMMENT '角色ID',
    `user_id` varchar(40) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`role_id`, `user_id`) USING BTREE,
    KEY `FK74qx7rkbtq2wqms78gljv87a1` (`role_id`) USING BTREE,
    KEY `FKee9dk0vg99shvsytflym6egx1` (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;
