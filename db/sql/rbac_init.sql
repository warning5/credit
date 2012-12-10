/*
SQLyog Ultimate v8.32 
MySQL - 5.6.5-m8 : Database - crebas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crebas` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `crebas`;

/*Table structure for table `rbac_sys_function` */

DROP TABLE IF EXISTS `rbac_sys_function`;

CREATE TABLE `rbac_sys_function` (
  `functionid` varchar(32) NOT NULL COMMENT '主键标识',
  `code` int(1) DEFAULT NULL COMMENT '0=浏览;1=增;2=删;3=改;4=查',
  `name` varchar(100) NOT NULL COMMENT '功能名称',
  `uri` varchar(500) DEFAULT NULL COMMENT '访问链接地址',
  `parent` varchar(36) DEFAULT NULL COMMENT '父功能的id',
  `description` varchar(100) DEFAULT NULL COMMENT '功能描述',
  `isOperation` mediumint(1) NOT NULL COMMENT '是否是功能操作，true/false',
  PRIMARY KEY (`functionid`),
  KEY `FK_Reference_9` (`code`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`code`) REFERENCES `rbac_sys_operation` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能列表';

/*Data for the table `rbac_sys_function` */

insert  into `rbac_sys_function`(`functionid`,`code`,`name`,`uri`,`parent`,`description`,`isOperation`) values ('0a747791e9daa8d57757b1250caafbc3',5,'执行添加角色','roleManager_addRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行添加角色',1),('0b42952892aa6fc77e8df67ae8648d38',5,'执行删除组','roleAllocateGroup_disallocateGroup.action','62a4765e55e5d53bbca5da637d943250','执行取消分配角色组',1),('1607a9fdacdcca5a1eea815ade0e341b',5,'执行分配角色','userAllocateRole_addRole.action','e1fe09dd27e97fef10f3d54af1b097fe','执行分配角色给用户',1),('16b3dddc497f6e549750e84306deeb9f',5,'执行解禁组','groupManager_enableGroup.action','3e09bff67a99411066a473501d04b128','执行解禁组',1),('16f4d5b96e063fabe7864ebb1ba57d45',5,'执行删除组','groupManager_deleteGroup.action','3e09bff67a99411066a473501d04b128','执行删除组',1),('19aeda50232bdd2b20293f5bed26a621',5,'执行删除功能','funcManager_deleteFunc.action','459b4d2258848fcd170be668433ab972','执行删除功能',1),('1b02224cc91f48d1e86918e2e5ed4c4a',0,'添加角色功能','roleAllocateFunction_showAddFunction.action','a7a3acda25aa61b68df481b48f422acc','添加角色功能页面',1),('1be858223db9e8a700a55c6c9bbb0416',5,'执行修改角色','roleManager_editRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行修改角色',1),('1dd6f5e857658293d764553639fef2be',0,'用户角色列表','userAllocateRole_showUserRole.action','e1fe09dd27e97fef10f3d54af1b097fe','用户角色列表页面',1),('1f3ba0870279fa625eb947ef7580df4a',5,'执行修改信息','userInfo_editUser.action','30c0911079ba41404adfc112d126e910','执行修改个人信息',1),('2',5,'登录','logon_doLogon.action',NULL,'用户登录',1),('20d072c2ef85c94a73dc958e0d6fd1ec',5,'执行修改功能','funcManager_editFunc.action','459b4d2258848fcd170be668433ab972','执行修改功能',1),('22c3df5cb702e1ee9ab8366a75169f03',0,'角色组列表','roleAllocateGroup_showRoleGroup.action','62a4765e55e5d53bbca5da637d943250','角色组列表页面',1),('29a79d5ceda6a30f85399fcf92d4d189',0,'所属组','userInfo_showGroup.action','30c0911079ba41404adfc112d126e910','所属组页面',0),('3',0,'首页','main.action',NULL,'首页',1),('3074acb0e34fb16529dbe271a88e4d0b',3,'修改组','groupManager_showEdit.action','3e09bff67a99411066a473501d04b128','修改组页面',1),('30c0911079ba41404adfc112d126e910',0,'个人信息','none',NULL,'个人信息页面',0),('31b18197ebbb7f1f40b9a8b0f0656aae',1,'分配用户组','userAllocateGroup_showAddGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','组树页面',1),('34',0,'查询市','dic_city.action',NULL,'查询市',1),('34ed49b9f4cd01a14c22c11497084ffc',0,'所属角色','userInfo_showRole.action','30c0911079ba41404adfc112d126e910','所属角色页面',0),('35',0,'查询县区','dic_area.action',NULL,'查询县区',1),('36',0,'新增评级业务','ratingBiz_addRatingBiz.action',NULL,'新增评级业务',1),('37',0,'显示新增评级业务','ratingBiz_showAdd.action',NULL,'显示新增评级业务',1),('38',0,'显示修改评级业务','ratingBiz_showEdit.action',NULL,'显示修改评级业务',1),('39',0,'删除评级业务','ratingBiz_deleteBizs.action',NULL,'删除评级业务',1),('3b9ac48532ae1881c02b73d393e399c1',5,'执行删除用户','userManager_deleteUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行删除用户',1),('3e09bff67a99411066a473501d04b128',0,'组管理','groupManager.action','e4a1ca7b3b69713c13c6396c761f94ad','组管理页面',0),('40',0,'修改评级业务','ratingBiz_editRatingBiz.action',NULL,'修改评级业务',1),('41',0,'上报评级业务','ratingBiz_updateStates.action',NULL,'上报评级业务',1),('42',0,'浏览上报评级业务','ratingBiz_brower.action',NULL,'浏览上报评级业务',1),('43',0,'批准评级业务','handleBiz_approve.action',NULL,'批准评级业务',1),('44',0,'显示回退页面','handleBiz_showBack.action',NULL,'显示回退页面',1),('45',0,'业务退回','handleBiz_back.action',NULL,'业务退回',1),('459b4d2258848fcd170be668433ab972',0,'功能管理','funcManager.action','e4a1ca7b3b69713c13c6396c761f94ad','功能管理页面',0),('45b26b37df8ed64c110a58324366bb2a',5,'执行添加功能','funcManager_addFunc.action','459b4d2258848fcd170be668433ab972','执行添加功能',1),('46',0,'已批准业务','handleBiz_approvalList.action',NULL,'以批准业务',1),('466e77a7db14c22b6b81ce5f525a1c5a',0,'角色管理','roleManager.action','e4a1ca7b3b69713c13c6396c761f94ad','角色管理页面',0),('47',0,'已退回业务','handleBiz_backList.action',NULL,'已退回业务',1),('48',0,'显示回退原因','handleBiz_showReason.action',NULL,'显示回退原因',1),('49',0,'显示后报备业务','afterRatingBiz_showAddAfter.action',NULL,'显示后报备业务',1),('4a97d695ccfec2d3a3a836bcd1a76dfb',1,'用户组列表','userAllocateGroup_showUserGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','用户组列表',1),('4f25729dd4e5fa78f7ea6ef62f9419c6',5,'执行禁用用户','userManager_disableUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行禁用用户',1),('50',0,'添加后报备业务','afterRatingBiz_addRatingReportBiz.action',NULL,'添加后报备业务',1),('51',0,'显示修改后报备业务','afterRatingBiz_showEdit.action',NULL,'显示修改后报备业务',1),('52',0,'修改后报备业务','afterRatingBiz_editRatingReportBiz.action',NULL,'修改后报备业务',1),('53',0,'删除后报备业务','afterRatingBiz_deleteRatingReportBizs.action',NULL,'删除后报备业务',1),('5319068ab297603b110b166eeafe88ca',3,'修改功能','funcManager_showEdit.action','459b4d2258848fcd170be668433ab972','修改功能页面',1),('56',0,'评级业务','',NULL,'评级业务',0),('561',0,'前报备业务','ratingBiz.action','56','前报备业务',0),('562',0,'已报备业务','ratingBiz_submittedRatingBiz.action','56','已报备业务',0),('563',0,'待处理业务','handleBiz.action','56','待处理业务',0),('564',0,'已处理业务','pages/rate/handledlist.html','56','已处理业务',0),('565',0,'后报备业务','afterRatingBiz.action','56','后报备业务',0),('5e4296d7f9e25e8396236e746f992d0b',1,'分配用户角色','userAllocateRole_showAddRole.action','e1fe09dd27e97fef10f3d54af1b097fe','角色树页面',1),('62a4765e55e5d53bbca5da637d943250',0,'分配组','roleAllocateGroup.action','466e77a7db14c22b6b81ce5f525a1c5a','给角色分配组',0),('6b2ef4820f8dae21a7e5f69aa25e880c',5,'执行删除角色','roleManager_deleteRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行删除角色',1),('6db9d77da526ceefffa7ccb7fe74e42a',0,'角色功能列表','roleAllocateFunction_showRoleFunction.action','a7a3acda25aa61b68df481b48f422acc','角色功能列表页面',1),('6f8766e906dbb8434adb57b203257bfc',5,'执行添加组','groupManager_addGroup.action','3e09bff67a99411066a473501d04b128','执行添加组',1),('712cbbaca146f05a444b91233b735782',5,'执行分配角色功能','roleAllocateFunction_addFunction.action','a7a3acda25aa61b68df481b48f422acc','执行分配角色功能',1),('713408074845e4df20a18f3a79960ee4',4,'查询用户','userManager_searchUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行查询用户',1),('72a66514b88dcb8667417dc3dd760704',5,'执行删除功能','roleAllocateFunction_disallocateFunction.action','a7a3acda25aa61b68df481b48f422acc','执行取消分配角色功能',1),('749939bff702a796497ea19dc5b232eb',3,'修改用户','userManager_showEdit.action','f9a67e8dcd4c90ed2c0916f3e86c424c','修改用户页面',1),('819e4d5a1ad65be9cd9aad5e15ff2e8f',5,'执行解禁用户','userManager_enableUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行解禁用户',1),('84d0fad8007df4be99e8734feb7e34a0',4,'查询组','groupManager_searchGroup.action','3e09bff67a99411066a473501d04b128','查询组信息',1),('86539c1a44d4b85acfb604b1fc6fa747',3,'修改信息','userInfo.action','30c0911079ba41404adfc112d126e910','修改个人信息页面',0),('8aec30dde9780cbe6cf4bb0f87d9535a',5,'执行禁用角色','roleManager_disableRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行禁用角色',1),('8db454f2855d7f7ff60957a8c8272f88',5,'执行解禁角色','roleManager_enableRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行解禁角色',1),('9b03dbb48f164dbe8565fa5511d77894',1,'添加用户','userManager_showAdd.action','f9a67e8dcd4c90ed2c0916f3e86c424c','添加用户页面',1),('a7a3acda25aa61b68df481b48f422acc',0,'分配功能','roleAllocateFunction.action','466e77a7db14c22b6b81ce5f525a1c5a','给角色分配功能',0),('aa3daa58fff761dedbda4da0f56a704b',5,'执行删除组','userAllocateGroup_disallocateGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','执行取消分配用户组',1),('ade65031e5335a790af51af62b6df1ad',5,'执行修改组','groupManager_editGroup.action','3e09bff67a99411066a473501d04b128','执行修改组',1),('b6ff4812abc9f641a524a99ef96e22be',3,'修改角色','roleManager_showEdit.action','466e77a7db14c22b6b81ce5f525a1c5a','修改角色页面',1),('c1e3bd0662787213c5604b79605ad5a2',1,'添加角色组','roleAllocateGroup_showAddGroup.action','62a4765e55e5d53bbca5da637d943250','组树页面',1),('c71c78c6ee0f8a247f97e255f930cdb0',0,'分配组','userAllocateGroup.action','f9a67e8dcd4c90ed2c0916f3e86c424c','给用户分配组',0),('c791feb154e82f25fccf836f46983176',5,'执行禁用组','groupManager_disableGroup.action','3e09bff67a99411066a473501d04b128','执行禁用组',1),('c8e22ed7ade5cc88c5ee2f106d599922',5,'执行添加用户','userManager_addUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行添加用户',1),('cf24f9ec7cd2cef1dd4ba1d167bd0c80',5,'执行分配用户组','userAllocateGroup_addGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','执行分配用户组',1),('daed5775b8b537c9bc82d5d347dd8e68',1,'添加角色','roleManager_showAdd.action','466e77a7db14c22b6b81ce5f525a1c5a','添加角色页面',1),('de8e54904fce5eb389fd751223f4443d',4,'查询角色','roleManager_searchRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行查询角色',1),('e1fe09dd27e97fef10f3d54af1b097fe',0,'分配角色','userAllocateRole.action','f9a67e8dcd4c90ed2c0916f3e86c424c','用户分配角色页面',0),('e4a1ca7b3b69713c13c6396c761f94ad',0,'权限管理',NULL,NULL,'权限管理页面',0),('f3229139692be9968dc8e6fbbee79aff',1,'添加功能','funcManager_showAdd.action','459b4d2258848fcd170be668433ab972','添加功能页面',1),('f3bbe986451be316082393701c4999f4',5,'执行分配角色组','roleAllocateGroup_addGroup.action','62a4765e55e5d53bbca5da637d943250','执行分配角色组',1),('f5a1aa0e31e6cb342b4345adfac1720e',5,'执行删除角色','userAllocateRole_disallocateRole.action','e1fe09dd27e97fef10f3d54af1b097fe','执行取消分配用户角色',1),('f9a67e8dcd4c90ed2c0916f3e86c424c',0,'用户管理','userManager.action','e4a1ca7b3b69713c13c6396c761f94ad','用户管理页面',0),('fcae7af2109674b7d3baf921d7e78a36',5,'执行修改用户','userManager_editUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行修改用户',1),('fd63dd0d94add6c287090528f8f20171',1,'添加组','groupManager_showAdd.action','3e09bff67a99411066a473501d04b128','添加组页面',1);

/*Table structure for table `rbac_sys_group` */

DROP TABLE IF EXISTS `rbac_sys_group`;

CREATE TABLE `rbac_sys_group` (
  `groupid` varchar(32) NOT NULL COMMENT '主键标识',
  `name` varchar(100) NOT NULL COMMENT '组名称',
  `description` varchar(100) DEFAULT NULL COMMENT '组描述',
  `disable` tinyint(1) NOT NULL COMMENT 'true=可用,false=禁用',
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

/*Data for the table `rbac_sys_group` */

/*Table structure for table `rbac_sys_operation` */

DROP TABLE IF EXISTS `rbac_sys_operation`;

CREATE TABLE `rbac_sys_operation` (
  `code` int(1) NOT NULL COMMENT '0=浏览;1=增;2=删;3=改;4=查;5=提交',
  `name` varchar(10) NOT NULL COMMENT '中文名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作类型';

/*Data for the table `rbac_sys_operation` */

insert  into `rbac_sys_operation`(`code`,`name`) values (0,'浏览'),(1,'增加'),(2,'删除'),(3,'修改'),(4,'查询'),(5,'提交');

/*Table structure for table `rbac_sys_role` */

DROP TABLE IF EXISTS `rbac_sys_role`;

CREATE TABLE `rbac_sys_role` (
  `roleid` varchar(32) NOT NULL COMMENT '主键标识',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `disable` tinyint(1) NOT NULL COMMENT 'true=可用,false=禁用',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `rbac_sys_role` */

insert  into `rbac_sys_role`(`roleid`,`name`,`description`,`disable`) values ('2','访客','未登录时的角色',0),('5','系统管理员','系统管理员',0),('7','默认角色','默认角色',0);

/*Table structure for table `rbac_sys_role_function` */

DROP TABLE IF EXISTS `rbac_sys_role_function`;

CREATE TABLE `rbac_sys_role_function` (
  `rfid` varchar(32) NOT NULL COMMENT '主键标识',
  `roleid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  `functionid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  `comp` int(1) NOT NULL COMMENT '0=只读,1=可写',
  PRIMARY KEY (`rfid`),
  KEY `FK_Reference_7` (`roleid`),
  KEY `FK_Reference_8` (`functionid`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`roleid`) REFERENCES `rbac_sys_role` (`roleid`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`functionid`) REFERENCES `rbac_sys_function` (`functionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表和功能表的关联';

/*Data for the table `rbac_sys_role_function` */

insert  into `rbac_sys_role_function`(`rfid`,`roleid`,`functionid`,`comp`) values ('10','5','3e09bff67a99411066a473501d04b128',1),('11','5','e4a1ca7b3b69713c13c6396c761f94ad',1),('12','2','2',1),('120','5','561',1),('121','5','56',1),('122','5','34',1),('123','5','35',1),('124','5','36',1),('125','5','37',1),('126','5','38',1),('127','5','39',1),('128','5','40',1),('129','5','41',1),('13','5','3',0),('130','5','42',1),('131','5','43',1),('132','5','44',1),('133','5','45',1),('134','5','46',1),('135','5','47',1),('136','5','48',1),('137','5','49',1),('138','5','50',1),('139','5','51',1),('14','5','9b03dbb48f164dbe8565fa5511d77894',1),('140','5','52',1),('141','5','53',1),('15','5','c8e22ed7ade5cc88c5ee2f106d599922',1),('16','5','749939bff702a796497ea19dc5b232eb',1),('17','5','713408074845e4df20a18f3a79960ee4',1),('18','5','fcae7af2109674b7d3baf921d7e78a36',1),('19','5','3b9ac48532ae1881c02b73d393e399c1',1),('20','5','4f25729dd4e5fa78f7ea6ef62f9419c6',1),('201','5','562',1),('202','5','563',1),('203','5','564',1),('204','5','565',1),('21','5','819e4d5a1ad65be9cd9aad5e15ff2e8f',1),('22','5','20d072c2ef85c94a73dc958e0d6fd1ec',1),('23','5','5319068ab297603b110b166eeafe88ca',1),('24','5','f3229139692be9968dc8e6fbbee79aff',1),('25','5','45b26b37df8ed64c110a58324366bb2a',1),('26','5','19aeda50232bdd2b20293f5bed26a621',1),('26214d07071ccb1572eef63c1593e46b','5','86539c1a44d4b85acfb604b1fc6fa747',1),('27','5','de8e54904fce5eb389fd751223f4443d',1),('28','5','daed5775b8b537c9bc82d5d347dd8e68',1),('29','5','0a747791e9daa8d57757b1250caafbc3',1),('30','5','b6ff4812abc9f641a524a99ef96e22be',1),('31','5','1be858223db9e8a700a55c6c9bbb0416',1),('32','5','6b2ef4820f8dae21a7e5f69aa25e880c',1),('33','5','8aec30dde9780cbe6cf4bb0f87d9535a',1),('34','5','8db454f2855d7f7ff60957a8c8272f88',1),('35','5','16b3dddc497f6e549750e84306deeb9f',1),('36','5','16f4d5b96e063fabe7864ebb1ba57d45',1),('37','5','3074acb0e34fb16529dbe271a88e4d0b',1),('38','5','6f8766e906dbb8434adb57b203257bfc',1),('39','5','84d0fad8007df4be99e8734feb7e34a0',1),('3bcba40bd9c2874120a1e54e816f99f1','7','29a79d5ceda6a30f85399fcf92d4d189',1),('40','5','ade65031e5335a790af51af62b6df1ad',1),('41','5','c791feb154e82f25fccf836f46983176',1),('42','5','fd63dd0d94add6c287090528f8f20171',1),('43','5','30c0911079ba41404adfc112d126e910',1),('44','5','34ed49b9f4cd01a14c22c11497084ffc',1),('45','5','29a79d5ceda6a30f85399fcf92d4d189',1),('46','5','62a4765e55e5d53bbca5da637d943250',1),('47','5','a7a3acda25aa61b68df481b48f422acc',1),('48','5','c71c78c6ee0f8a247f97e255f930cdb0',1),('49','5','e1fe09dd27e97fef10f3d54af1b097fe',1),('50','5','1dd6f5e857658293d764553639fef2be',1),('51','5','5e4296d7f9e25e8396236e746f992d0b',1),('52','5','4a97d695ccfec2d3a3a836bcd1a76dfb',1),('53','5','31b18197ebbb7f1f40b9a8b0f0656aae',1),('54','5','22c3df5cb702e1ee9ab8366a75169f03',1),('55','5','c1e3bd0662787213c5604b79605ad5a2',1),('56','5','6db9d77da526ceefffa7ccb7fe74e42a',1),('57','5','1607a9fdacdcca5a1eea815ade0e341b',1),('58','5','cf24f9ec7cd2cef1dd4ba1d167bd0c80',1),('59','5','f3bbe986451be316082393701c4999f4',1),('5cb81f35d2a7f46fe0a4c3a1f1b7799e','7','34ed49b9f4cd01a14c22c11497084ffc',1),('60','5','f5a1aa0e31e6cb342b4345adfac1720e',1),('61','5','aa3daa58fff761dedbda4da0f56a704b',1),('62','5','0b42952892aa6fc77e8df67ae8648d38',1),('63','5','1b02224cc91f48d1e86918e2e5ed4c4a',1),('63ca7e9ff20072bf6fdb94348052aaa7','7','3',1),('64','5','712cbbaca146f05a444b91233b735782',1),('65','5','72a66514b88dcb8667417dc3dd760704',1),('7','5','f9a67e8dcd4c90ed2c0916f3e86c424c',1),('7a4abc2a9e437b18a0f44f08386b1026','7','30c0911079ba41404adfc112d126e910',1),('8','5','466e77a7db14c22b6b81ce5f525a1c5a',1),('8ce51a61ab8f9b00be1e7a2a11203dcb','7','30c0911079ba41404adfc112d126e910',1),('9','5','459b4d2258848fcd170be668433ab972',1),('92bba0e8053392c057cd94e3de6be35a','7','1f3ba0870279fa625eb947ef7580df4a',1),('befe1f4a1b5f92e05da622a5e561e969','5','1f3ba0870279fa625eb947ef7580df4a',1),('c4b77a4fca03e636bb4ecc72044b7c44','7','86539c1a44d4b85acfb604b1fc6fa747',1),('ecb151370d9ad2dfab2fab69266891d9','5','30c0911079ba41404adfc112d126e910',1);

/*Table structure for table `rbac_sys_role_group` */

DROP TABLE IF EXISTS `rbac_sys_role_group`;

CREATE TABLE `rbac_sys_role_group` (
  `rgid` varchar(32) NOT NULL COMMENT '主键标识',
  `roleid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  `groupid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  PRIMARY KEY (`rgid`),
  KEY `FK_Reference_10` (`roleid`),
  KEY `FK_Reference_11` (`groupid`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`roleid`) REFERENCES `rbac_sys_role` (`roleid`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`groupid`) REFERENCES `rbac_sys_group` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表和组表的关联';

/*Data for the table `rbac_sys_role_group` */

/*Table structure for table `rbac_sys_user` */

DROP TABLE IF EXISTS `rbac_sys_user`;

CREATE TABLE `rbac_sys_user` (
  `userid` varchar(32) NOT NULL COMMENT '主键标识',
  `username` varchar(32) NOT NULL COMMENT '登录的用户名',
  `password` varchar(32) NOT NULL COMMENT '登录的密码',
  `description` varchar(100) DEFAULT NULL COMMENT '用户描述',
  `disable` tinyint(1) NOT NULL COMMENT 'true=可用,false=禁用',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `rbac_sys_user` */

insert  into `rbac_sys_user`(`userid`,`username`,`password`,`description`,`disable`) values ('3','guest','084e0343a0486ff05530df6c705c8bb4','guest',0),('5','admin','21232f297a57a5a743894a0e4a801fc3','系统管理员',0),('50a817e5755a91f2397417cdfb00d61a','test','098f6bcd4621d373cade4e832627b4f6','测试用户',0),('f00aeb0aac2e3bb965a5f412cc401412','66667','e9510081ac30ffa83f10b68cde1cac07','666666',1);

/*Table structure for table `rbac_sys_user_group` */

DROP TABLE IF EXISTS `rbac_sys_user_group`;

CREATE TABLE `rbac_sys_user_group` (
  `ugid` varchar(32) NOT NULL COMMENT '主键标识',
  `userid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  `groupid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  PRIMARY KEY (`ugid`),
  KEY `FK_Reference_3` (`userid`),
  KEY `FK_Reference_4` (`groupid`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`userid`) REFERENCES `rbac_sys_user` (`userid`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`groupid`) REFERENCES `rbac_sys_group` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表与组表的关联';

/*Data for the table `rbac_sys_user_group` */

/*Table structure for table `rbac_sys_user_role` */

DROP TABLE IF EXISTS `rbac_sys_user_role`;

CREATE TABLE `rbac_sys_user_role` (
  `urid` varchar(32) NOT NULL COMMENT '主键标识',
  `userid` varchar(32) DEFAULT NULL COMMENT '用户主键标识',
  `roleid` varchar(32) DEFAULT NULL COMMENT '角色主键标识',
  PRIMARY KEY (`urid`),
  KEY `FK_Reference_1` (`userid`),
  KEY `FK_Reference_2` (`roleid`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`userid`) REFERENCES `rbac_sys_user` (`userid`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`roleid`) REFERENCES `rbac_sys_role` (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表和角色表的关联';

/*Data for the table `rbac_sys_user_role` */

insert  into `rbac_sys_user_role`(`urid`,`userid`,`roleid`) values ('1','5','2'),('1f687ca6895c2495866fe6d7744ee307','5','7'),('3','3','2'),('7','5','5'),('edf58d744149f310394ef058f0858ef7','50a817e5755a91f2397417cdfb00d61a','7');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
