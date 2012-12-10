/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/11/4 星期日 19:05:34                       */
/*==============================================================*/


drop table if exists rbac_org_company;

drop table if exists rbac_org_department;

drop table if exists rbac_org_employee;

drop table if exists rbac_org_position;

/*==============================================================*/
/* Table: rbac_org_company                                      */
/*==============================================================*/
create table rbac_org_company
(
   companyid            varchar(32) not null comment '主键标识',
   name                 varchar(32) not null comment '公司名称',
   parent               varchar(32) comment '上级公司',
   description          varchar(128) comment '描述',
   primary key (companyid)
);

/*==============================================================*/
/* Table: rbac_org_department                                   */
/*==============================================================*/
create table rbac_org_department
(
   departmentid         varchar(32) not null comment '主键标识',
   name                 varchar(32) not null comment '部门名称',
   parent               varchar(32) comment '上级部门',
   companyid            varchar(32) not null comment '所属公司',
   description          varchar(128) comment '部门描述',
   primary key (departmentid)
);

alter table rbac_org_department comment '部门';

/*==============================================================*/
/* Table: rbac_org_employee                                     */
/*==============================================================*/
create table rbac_org_employee
(
   employeeid           varchar(32) not null comment '主键标识',
   departmentid         varchar(32) comment '主键标识',
   primary key (employeeid)
);

alter table rbac_org_employee comment '职工';

/*==============================================================*/
/* Table: rbac_org_position                                     */
/*==============================================================*/
create table rbac_org_position
(
   positionid           varchar(32) not null comment '主键id',
   name                 varchar(32) not null comment '职位名称',
   departmentid         varchar(32) not null comment '所属部门',
   parent               varchar(32) comment '上级职位',
   description          varchar(128) comment '职位描述',
   primary key (positionid)
);

alter table rbac_org_department add constraint FK_Reference_1 foreign key (companyid)
      references rbac_org_company (companyid) on delete restrict on update restrict;

alter table rbac_org_employee add constraint FK_Reference_3 foreign key (departmentid)
      references rbac_org_department (departmentid) on delete restrict on update restrict;

alter table rbac_org_position add constraint FK_Reference_2 foreign key (departmentid)
      references rbac_org_department (departmentid) on delete restrict on update restrict;

