/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/12/5 星期三 22:00:12                       */
/*==============================================================*/


drop table if exists rbac_sys_function;

drop table if exists rbac_sys_group;

drop table if exists rbac_sys_operation;

drop table if exists rbac_sys_role;

drop table if exists rbac_sys_role_function;

drop table if exists rbac_sys_role_group;

drop table if exists rbac_sys_user;

drop table if exists rbac_sys_user_group;

drop table if exists rbac_sys_user_role;

/*==============================================================*/
/* Table: rbac_sys_function                                     */
/*==============================================================*/
create table rbac_sys_function
(
   functionid           varchar(32) not null comment '主键标识',
   code                 integer(1) comment '0=浏览;1=增;2=删;3=改;4=查',
   name                 varchar(100) not null comment '功能名称',
   uri                  varchar(500) comment '访问链接地址',
   parent               varchar(36) comment '父功能的id',
   isOperation          boolean not null comment '是否功能操作，true/false',
   description          varchar(100) comment '功能描述',
   functionOrder        int(3) default 1 comment '功能排序',
   primary key (functionid)
);

alter table rbac_sys_function comment '功能列表';

/*==============================================================*/
/* Table: rbac_sys_group                                        */
/*==============================================================*/
create table rbac_sys_group
(
   groupid              varchar(32) not null comment '主键标识',
   name                 varchar(100) not null comment '组名称',
   description          varchar(100) comment '组描述',
   disable              boolean not null comment 'true=可用,false=禁用',
   primary key (groupid)
);

alter table rbac_sys_group comment '用户组';

/*==============================================================*/
/* Table: rbac_sys_operation                                    */
/*==============================================================*/
create table rbac_sys_operation
(
   code                 integer(1) not null comment '0=浏览;1=增;2=删;3=改;4=查;5=提交',
   name                 varchar(10) not null comment '中文名称',
   primary key (code)
);

alter table rbac_sys_operation comment '操作类型';

/*==============================================================*/
/* Table: rbac_sys_role                                         */
/*==============================================================*/
create table rbac_sys_role
(
   roleid               varchar(32) not null comment '主键标识',
   name                 varchar(50) not null comment '角色名称',
   description          varchar(100) comment '角色描述',
   disable              boolean not null comment 'true=可用,false=禁用',
   primary key (roleid)
);

alter table rbac_sys_role comment '角色';

/*==============================================================*/
/* Table: rbac_sys_role_function                                */
/*==============================================================*/
create table rbac_sys_role_function
(
   rfid                 varchar(32) not null comment '主键标识',
   roleid               varchar(32) comment '主键标识',
   functionid           varchar(32) comment '主键标识',
   comp                 integer(1) not null comment '0=只读,1=可写',
   primary key (rfid)
);

alter table rbac_sys_role_function comment '角色表和功能表的关联';

/*==============================================================*/
/* Table: rbac_sys_role_group                                   */
/*==============================================================*/
create table rbac_sys_role_group
(
   ruid                 varchar(32) not null comment '主键标识',
   roleid               varchar(32) comment '主键标识',
   groupid              varchar(32) comment '主键标识',
   primary key (ruid)
);

alter table rbac_sys_role_group comment '角色表和组表的关联';

/*==============================================================*/
/* Table: rbac_sys_user                                         */
/*==============================================================*/
create table rbac_sys_user
(
   userid               varchar(32) not null comment '主键标识',
   username             varchar(32) not null comment '登录的用户名',
   password             varchar(32) not null comment '登录的密码',
   description          varchar(100) comment '用户描述',
   disable              boolean not null comment 'true=可用,false=禁用',
   primary key (userid)
);

alter table rbac_sys_user comment '用户表';

/*==============================================================*/
/* Table: rbac_sys_user_group                                   */
/*==============================================================*/
create table rbac_sys_user_group
(
   ugid                 varchar(32) not null comment '主键标识',
   userid               varchar(32) comment '主键标识',
   groupid              varchar(32) comment '主键标识',
   primary key (ugid)
);

alter table rbac_sys_user_group comment '用户表与组表的关联';

/*==============================================================*/
/* Table: rbac_sys_user_role                                    */
/*==============================================================*/
create table rbac_sys_user_role
(
   urid                 varchar(32) not null comment '主键标识',
   userid               varchar(32) comment '用户主键标识',
   roleid               varchar(32) comment '角色主键标识',
   primary key (urid)
);

alter table rbac_sys_user_role comment '用户表和角色表的关联';

alter table rbac_sys_function add constraint FK_Reference_Function_Operation foreign key (code)
      references rbac_sys_operation (code) on delete restrict on update restrict;

alter table rbac_sys_role_function add constraint FK_Reference_RoleFunction_Function foreign key (functionid)
      references rbac_sys_function (functionid) on delete restrict on update restrict;

alter table rbac_sys_role_function add constraint FK_Reference_RoleFunction_Role foreign key (roleid)
      references rbac_sys_role (roleid) on delete restrict on update restrict;

alter table rbac_sys_role_group add constraint FK_Reference_RoleGroup_Group foreign key (groupid)
      references rbac_sys_group (groupid) on delete restrict on update restrict;

alter table rbac_sys_role_group add constraint FK_Reference_RoleGroup_Role foreign key (roleid)
      references rbac_sys_role (roleid) on delete restrict on update restrict;

alter table rbac_sys_user_group add constraint FK_Reference_UserGroup_Group foreign key (groupid)
      references rbac_sys_group (groupid) on delete restrict on update restrict;

alter table rbac_sys_user_group add constraint FK_Reference_UserGroup_User foreign key (userid)
      references rbac_sys_user (userid) on delete restrict on update restrict;

alter table rbac_sys_user_role add constraint FK_Reference_UserRole_Role foreign key (roleid)
      references rbac_sys_role (roleid) on delete restrict on update restrict;

alter table rbac_sys_user_role add constraint FK_Reference_UserRole_User foreign key (userid)
      references rbac_sys_user (userid) on delete restrict on update restrict;

