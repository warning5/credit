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

/*Table structure for table `credit_org_group` */

DROP TABLE IF EXISTS `credit_org_group`;

CREATE TABLE `credit_org_group` (
  `ogid` varchar(32) NOT NULL COMMENT '主键标识',
  `orgid` varchar(32) NOT NULL COMMENT '企业、评级机构、监管机构id',
  `groupid` varchar(32) NOT NULL COMMENT '组id',
  PRIMARY KEY (`ogid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `credit_org_group` */

insert  into `credit_org_group`(`ogid`,`orgid`,`groupid`) values ('1','6','b3dc1638fe295e111ef4a62c17818002'),('56b347eb705a4a1113cbb504a369d01b','9','2ed83a1881b12c5cd34ad5592be4b7ea'),('78dc15626b1ec8c33af1b7ecd8a8f565','7','3f3c5310fa478b2e480883906cccb206'),('bb0a19053beea7ae9cdd49c1690beb1f','8','4f416f2d02a3151d20005d0d67d5cb71'),('cfa9eb1fe7633939c6773d6bd95b4db4','5','064bb00340535ffe960a4e2274a0957f');

/*Table structure for table `rbac_org_company` */

DROP TABLE IF EXISTS `rbac_org_company`;

CREATE TABLE `rbac_org_company` (
  `companyid` varchar(32) NOT NULL COMMENT '主键标识',
  `name` varchar(32) NOT NULL COMMENT '公司名称',
  `parent` varchar(32) DEFAULT NULL COMMENT '上级公司',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rbac_org_company` */

insert  into `rbac_org_company`(`companyid`,`name`,`parent`,`description`) values ('1','监管机构',NULL,'监管机构节点'),('2','评级机构',NULL,'评级机构节点'),('3','金融机构',NULL,'金融机构节点'),('4','企业机构',NULL,'企业机构节点'),('5','中国建设银行','3','中国建设银行'),('6','中国人民银行','1','中国人民银行'),('7','北京银监会','2','北京银监会'),('8','北京自由行网络有限公司','4','北京自由行网络有限公司'),('9','评级机构A','2','评级机构A');

/*Table structure for table `rbac_org_department` */

DROP TABLE IF EXISTS `rbac_org_department`;

CREATE TABLE `rbac_org_department` (
  `departmentid` varchar(32) NOT NULL COMMENT '主键标识',
  `name` varchar(32) NOT NULL COMMENT '部门名称',
  `parent` varchar(32) DEFAULT NULL COMMENT '上级部门',
  `companyid` varchar(32) NOT NULL COMMENT '所属公司',
  `description` varchar(128) DEFAULT NULL COMMENT '部门描述',
  PRIMARY KEY (`departmentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

/*Data for the table `rbac_org_department` */

/*Table structure for table `rbac_org_employee` */

DROP TABLE IF EXISTS `rbac_org_employee`;

CREATE TABLE `rbac_org_employee` (
  `employeeid` varchar(32) NOT NULL COMMENT '主键标识',
  `departmentid` varchar(32) DEFAULT NULL COMMENT '主键标识',
  PRIMARY KEY (`employeeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职工';

/*Data for the table `rbac_org_employee` */

/*Table structure for table `rbac_org_position` */

DROP TABLE IF EXISTS `rbac_org_position`;

CREATE TABLE `rbac_org_position` (
  `positionid` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(32) NOT NULL COMMENT '职位名称',
  `departmentid` varchar(32) NOT NULL COMMENT '所属部门',
  `parent` varchar(32) DEFAULT NULL COMMENT '上级职位',
  `description` varchar(128) DEFAULT NULL COMMENT '职位描述',
  PRIMARY KEY (`positionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rbac_org_position` */

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

insert  into `rbac_sys_function`(`functionid`,`code`,`name`,`uri`,`parent`,`description`,`isOperation`) values ('0693e14062b205695eb4ceff59ae0d5f',4,'查询机构','supervisorManager_searchSupervisor.action','4b41d098d6bbd29d8a21305666968e35','执行查询机构',1),('0a747791e9daa8d57757b1250caafbc3',5,'执行添加角色','roleManager_addRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行添加角色',1),('0b42952892aa6fc77e8df67ae8648d38',5,'执行删除组','roleAllocateGroup_disallocateGroup.action','62a4765e55e5d53bbca5da637d943250','执行取消分配角色组',1),('0cd7e6daea6496bd09c66c588b66cbb8',5,'执行删除组','orgAllocateGroup_disallocateGroup.action','d58652e4bb1486a77973a53fb745a495','执行删除组',1),('0f518595d39a4f9b93d5db9fa8541fae',3,'修改评级机构','agc_editAgc.action','de36a47f82d4c43f52bd868538718986','修改评级机构',1),('0f536bfc241c4309b7578ae66da87846',0,'后报备','none','56','后报备',0),('100',0,'上报后报备业务','afterRatingBiz_uploadReport.action','0f536bfc241c4309b7578ae66da87846',NULL,1),('101',0,'浏览后报备业务','afterRatingBiz_brower.action','0f536bfc241c4309b7578ae66da87846','浏览后报备业务',1),('102',0,'待处理后报备业务','afterHandleBiz.action','0f536bfc241c4309b7578ae66da87846','待处理后报备业务',0),('103',0,'批准后报备业务','afterHandleBiz_afterApprove.action','0f536bfc241c4309b7578ae66da87846','批准后报备业务',1),('104',0,'显示后报备退回','afterHandleBiz_afterShowBack.action','0f536bfc241c4309b7578ae66da87846','显示后报备退回',1),('105',0,'已批准后报备业务','afterHandleBiz_afterApprovalList.action','0f536bfc241c4309b7578ae66da87846','已批准后报备业务',0),('106',0,'已退回后报备业务','afterHandleBiz_afterBackList.action','0f536bfc241c4309b7578ae66da87846','已退回后报备业务',0),('107',0,'退回后报备','afterHandleBiz_afterBack.action','0f536bfc241c4309b7578ae66da87846','退回后报备',1),('12',0,'企业列表','firm.action','18ca5055f31ed5ce9c1cbbdef37714eb','企业列表',0),('1607a9fdacdcca5a1eea815ade0e341b',5,'执行分配角色','userAllocateRole_addRole.action','e1fe09dd27e97fef10f3d54af1b097fe','执行分配角色给用户',1),('16b3dddc497f6e549750e84306deeb9f',5,'执行解禁组','groupManager_enableGroup.action','3e09bff67a99411066a473501d04b128','执行解禁组',1),('16f4d5b96e063fabe7864ebb1ba57d45',5,'执行删除组','groupManager_deleteGroup.action','3e09bff67a99411066a473501d04b128','执行删除组',1),('18ca5055f31ed5ce9c1cbbdef37714eb',0,'企业信息','none',NULL,'企业信息',0),('19aeda50232bdd2b20293f5bed26a621',2,'执行删除功能','funcManager_deleteFunc.action','459b4d2258848fcd170be668433ab972','执行删除功能',1),('1b02224cc91f48d1e86918e2e5ed4c4a',0,'添加角色功能','roleAllocateFunction_showAddFunction.action','a7a3acda25aa61b68df481b48f422acc','添加角色功能页面',1),('1be858223db9e8a700a55c6c9bbb0416',5,'执行修改角色','roleManager_editRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行修改角色',1),('1dd6f5e857658293d764553639fef2be',0,'用户角色列表','userAllocateRole_showUserRole.action','e1fe09dd27e97fef10f3d54af1b097fe','用户角色列表页面',1),('1f3ba0870279fa625eb947ef7580df4a',5,'执行修改信息','userInfo_editUser.action','30c0911079ba41404adfc112d126e910','执行修改个人信息',1),('2',5,'登录','logon_doLogon.action',NULL,'用户登录',1),('20778cec69087f7059526c1bcc8d714b',5,'执行修改机构','supervisorManager_editSupervisor.action','4b41d098d6bbd29d8a21305666968e35','执行修改机构',1),('20d072c2ef85c94a73dc958e0d6fd1ec',5,'执行修改功能','funcManager_editFunc.action','459b4d2258848fcd170be668433ab972','执行修改功能',1),('22c3df5cb702e1ee9ab8366a75169f03',0,'角色组列表','roleAllocateGroup_showRoleGroup.action','62a4765e55e5d53bbca5da637d943250','角色组列表页面',1),('22ce46b4755c43668ed32e26be2068a4',0,'评级业务查询','none','47c65f1ac4af4092bdd5c805bc211156','评级业务查询',0),('29a79d5ceda6a30f85399fcf92d4d189',0,'所属组','userInfo_showGroup.action','30c0911079ba41404adfc112d126e910','所属组页面',0),('2e704b5ade5372a93b26e2a8259112e3',0,'评级机构列表','agc.action','de36a47f82d4c43f52bd868538718986','评级机构',0),('3',0,'首页','main.action',NULL,'首页',1),('3074acb0e34fb16529dbe271a88e4d0b',3,'修改组','groupManager_showEdit.action','3e09bff67a99411066a473501d04b128','修改组页面',1),('30c0911079ba41404adfc112d126e910',0,'个人信息','none',NULL,'个人信息页面',0),('31101c9a6fd0b47bcd4c33777faab638',0,'显示企业高级查询','firm_showAdvanceSearch.action','18ca5055f31ed5ce9c1cbbdef37714eb','显示企业高级查询',1),('31b18197ebbb7f1f40b9a8b0f0656aae',1,'分配用户组','userAllocateGroup_showAddGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','组树页面',1),('33ec703fc43fc1303fbcc58c5ba66afa',0,'已添加后报备','beforeHandleBiz_submittedBeforeApprovalList.action','0f536bfc241c4309b7578ae66da87846','已添加后报备',1),('34',0,'查询市','dic_city.action',NULL,'查询市',1),('3439c4e598fe97a3a118ae600c6c1241',0,'已退回前报备业务','beforeHandleBiz_beforeBackList.action','9780ab30f05565c3d8650eeb4511df0c','已退回前报备业务',0),('34ed49b9f4cd01a14c22c11497084ffc',0,'所属角色','userInfo_showRole.action','30c0911079ba41404adfc112d126e910','所属角色页面',0),('35',0,'查询县区','dic_area.action',NULL,'查询县区',1),('36',0,'新增评级业务','ratingBiz_addRatingBiz.action','9780ab30f05565c3d8650eeb4511df0c','新增评级业务',1),('37',0,'显示新增评级业务','ratingBiz_showAdd.action','9780ab30f05565c3d8650eeb4511df0c','显示新增评级业务',1),('38',0,'显示修改评级业务','ratingBiz_showEdit.action','9780ab30f05565c3d8650eeb4511df0c','显示修改评级业务',1),('39',0,'删除评级业务','ratingBiz_deleteBizs.action','9780ab30f05565c3d8650eeb4511df0c','删除评级业务',1),('3a752a1e6546f7b9fd9db396f078ed7c',0,'机构组列表','orgAllocateGroup_showOrgGroup.action','d58652e4bb1486a77973a53fb745a495','机构组列表',1),('3b9ac48532ae1881c02b73d393e399c1',5,'执行删除用户','userManager_deleteUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行删除用户',1),('3e09bff67a99411066a473501d04b128',0,'组管理','groupManager.action','e4a1ca7b3b69713c13c6396c761f94ad','组管理页面',0),('40',0,'修改评级业务','ratingBiz_editRatingBiz.action','9780ab30f05565c3d8650eeb4511df0c','修改评级业务',1),('41',0,'上报评级业务','ratingBiz_updateStates.action','9780ab30f05565c3d8650eeb4511df0c','上报评级业务',1),('42',0,'浏览上报评级业务','ratingBiz_brower.action','9780ab30f05565c3d8650eeb4511df0c','浏览上报评级业务',1),('42f2e461ffa558102fa9d832a5da8e81',5,'执行分配组','orgAllocateGroup_addGroup.action','d58652e4bb1486a77973a53fb745a495','执行分配组',1),('43',0,'批准评级业务','beforeHandleBiz_beforeApprove.action','9780ab30f05565c3d8650eeb4511df0c','批准评级业务',1),('432bbf8c1484f93bd7b6b45dcc0c7a1f',0,'金融机构信息','none',NULL,'金融机构信息',0),('44',0,'显示回退页面','beforeHandleBiz_beforeShowBack.action','56','显示回退页面',1),('45',0,'业务退回','beforeHandleBiz_beforeBack.action','56','业务退回',1),('459b4d2258848fcd170be668433ab972',0,'功能管理','funcManager.action','e4a1ca7b3b69713c13c6396c761f94ad','功能管理页面',0),('45b26b37df8ed64c110a58324366bb2a',5,'执行添加功能','funcManager_addFunc.action','459b4d2258848fcd170be668433ab972','执行添加功能',1),('46',0,'已批准业务','handleBiz_approvalList.action','9780ab30f05565c3d8650eeb4511df0c','已批准业务',1),('466e77a7db14c22b6b81ce5f525a1c5a',0,'角色管理','roleManager.action','e4a1ca7b3b69713c13c6396c761f94ad','角色管理页面',0),('47c65f1ac4af4092bdd5c805bc211156',0,'信息查询','none',NULL,'评级业务、评级报告、企业信息查询',0),('48',0,'显示回退原因','beforeHandleBiz_beforeShowReason.action','56','显示回退原因',1),('49',0,'显示后报备业务','afterRatingBiz_showAddAfter.action','0f536bfc241c4309b7578ae66da87846','显示后报备业务',1),('4a97d695ccfec2d3a3a836bcd1a76dfb',1,'用户组列表','userAllocateGroup_showUserGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','用户组列表',1),('4b41d098d6bbd29d8a21305666968e35',0,'监管机构信息','none',NULL,'监管机构信息',0),('4f25729dd4e5fa78f7ea6ef62f9419c6',5,'执行禁用用户','userManager_disableUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行禁用用户',1),('50',0,'添加后报备业务','afterRatingBiz_addRatingReportBiz.action','0f536bfc241c4309b7578ae66da87846','添加后报备业务',1),('51',0,'显示修改后报备业务','afterRatingBiz_showEdit.action','0f536bfc241c4309b7578ae66da87846','显示修改后报备业务',1),('52',0,'修改后报备业务','afterRatingBiz_editRatingReportBiz.action','0f536bfc241c4309b7578ae66da87846','修改后报备业务',1),('53',0,'删除后报备业务','afterRatingBiz_deleteRatingReportBizs.action','0f536bfc241c4309b7578ae66da87846','删除后报备业务',1),('5319068ab297603b110b166eeafe88ca',3,'修改功能','funcManager_showEdit.action','459b4d2258848fcd170be668433ab972','修改功能页面',1),('55c07aa0aabec12504d58aef7527da9a',0,'机构管理','none','78c645ed248092d9631df492b94be03a','机构管理',0),('55ca344d943fb674d8e4fd4814e22da7',5,'执行修改机构','financialManager_editFinancial.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','执行修改机构',1),('56',0,'评级业务','',NULL,'评级业务',0),('561',0,'前报备业务','ratingBiz.action','9780ab30f05565c3d8650eeb4511df0c','前报备业务',0),('562',0,'已报备业务','ratingBiz_submittedRatingBiz.action','9780ab30f05565c3d8650eeb4511df0c','已报备业务',0),('563',0,'待处理业务','beforeHandleBiz.action','9780ab30f05565c3d8650eeb4511df0c','待处理业务',0),('564',0,'已批准前报备业务','pages/rate/before/beforeHandledlist.html','9780ab30f05565c3d8650eeb4511df0c','已批准前报备业务',0),('565',0,'后报备业务','afterRatingBiz.action','0f536bfc241c4309b7578ae66da87846','后报备业务',0),('566',0,'已提交后报备业务','afterRatingBiz_submittedRatingBiz.action','0f536bfc241c4309b7578ae66da87846','已提交后报备业务',0),('589a8def43967301e536726fddf67ceb',3,'显示修改企业','firm_showEdit.action','18ca5055f31ed5ce9c1cbbdef37714eb','显示修改企业',1),('5e4296d7f9e25e8396236e746f992d0b',1,'分配用户角色','userAllocateRole_showAddRole.action','e1fe09dd27e97fef10f3d54af1b097fe','角色树页面',1),('5e74d208182c489f8868b14d4c0f6caf',0,'被评级企业查询','none','47c65f1ac4af4092bdd5c805bc211156','被评级企业查询',0),('62a4765e55e5d53bbca5da637d943250',0,'分配组','roleAllocateGroup.action','466e77a7db14c22b6b81ce5f525a1c5a','给角色分配组',0),('692ccf211faf403691e780b31ff7b5df',0,'评级报告查询','none','47c65f1ac4af4092bdd5c805bc211156','评级报告查询',0),('6b2ef4820f8dae21a7e5f69aa25e880c',5,'执行删除角色','roleManager_deleteRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行删除角色',1),('6ceb97cf6b55aeff1370e0d1497b9490',0,'监管机构列表','supervisorManager.action','4b41d098d6bbd29d8a21305666968e35','监管机构列表',0),('6db9d77da526ceefffa7ccb7fe74e42a',0,'角色功能列表','roleAllocateFunction_showRoleFunction.action','a7a3acda25aa61b68df481b48f422acc','角色功能列表页面',1),('6f8766e906dbb8434adb57b203257bfc',5,'执行添加组','groupManager_addGroup.action','3e09bff67a99411066a473501d04b128','执行添加组',1),('712cbbaca146f05a444b91233b735782',5,'执行分配角色功能','roleAllocateFunction_addFunction.action','a7a3acda25aa61b68df481b48f422acc','执行分配角色功能',1),('713408074845e4df20a18f3a79960ee4',4,'查询用户','userManager_searchUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行查询用户',1),('72a66514b88dcb8667417dc3dd760704',5,'删除功能','roleAllocateFunction_disallocateFunction.action','a7a3acda25aa61b68df481b48f422acc','执行取消分配角色功能',1),('749939bff702a796497ea19dc5b232eb',3,'修改用户','userManager_showEdit.action','f9a67e8dcd4c90ed2c0916f3e86c424c','修改用户页面',1),('78c645ed248092d9631df492b94be03a',0,'组织机构','none',NULL,'组织机构',0),('819e4d5a1ad65be9cd9aad5e15ff2e8f',5,'执行解禁用户','userManager_enableUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行解禁用户',1),('84d0fad8007df4be99e8734feb7e34a0',4,'查询组','groupManager_searchGroup.action','3e09bff67a99411066a473501d04b128','查询组信息',1),('86539c1a44d4b85acfb604b1fc6fa747',3,'修改信息','userInfo.action','30c0911079ba41404adfc112d126e910','修改个人信息页面',0),('8aec30dde9780cbe6cf4bb0f87d9535a',5,'执行禁用角色','roleManager_disableRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行禁用角色',1),('8db454f2855d7f7ff60957a8c8272f88',5,'执行解禁角色','roleManager_enableRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行解禁角色',1),('907b393770b97111b9d0330cd52391e4',1,'添加机构','supervisorManager_showAdd.action','4b41d098d6bbd29d8a21305666968e35','显示添加机构页面',1),('9142f603acbfadcb1c9721be0fd86861',2,'执行删除机构','supervisorManager_deleteSupervisor.action','4b41d098d6bbd29d8a21305666968e35','执行删除机构',1),('91b760c3f95c442ad4c521e6335e2756',3,'修改机构','supervisorManager_showEdit.action','4b41d098d6bbd29d8a21305666968e35','显示修改机构页面',1),('9780ab30f05565c3d8650eeb4511df0c',0,'前报备','none','56','前报备',0),('999f0a740a6a7c6575265dc715a1e68f',1,'分配机构组','orgAllocateGroup_showAddGroup.action','d58652e4bb1486a77973a53fb745a495','分配机构组',1),('9b03dbb48f164dbe8565fa5511d77894',1,'添加用户','userManager_showAdd.action','f9a67e8dcd4c90ed2c0916f3e86c424c','添加用户页面',1),('a16b82a6a54401c4bd0fd9bbd435ef69',5,'执行添加机构','financialManager_addFinancial.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','执行添加机构',1),('a333bc13a00e3004ebe2e6eb7121efab',2,'执行删除机构','financialManager_deleteFinancial.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','执行删除机构',1),('a52548c9d999c2c18b5cb5b7fd9b37ba',3,'修改机构','financialManager_showEdit.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','显示修改机构页面',1),('a6f51d0754b91f4e51180b7a542927e2',0,'显示添加评级机构','agc_showAdd.action','de36a47f82d4c43f52bd868538718986','显示添加评级机构',1),('a7a3acda25aa61b68df481b48f422acc',0,'分配功能','roleAllocateFunction.action','466e77a7db14c22b6b81ce5f525a1c5a','给角色分配功能',0),('a925a0c6bfebade7234052e0c728d5fd',4,'查询机构','financialManager_searchFinancial.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','查询机构',1),('aa3daa58fff761dedbda4da0f56a704b',5,'执行删除组','userAllocateGroup_disallocateGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','执行取消分配用户组',1),('ade65031e5335a790af51af62b6df1ad',5,'执行修改组','groupManager_editGroup.action','3e09bff67a99411066a473501d04b128','执行修改组',1),('b54d4eba31a6619a34b5582a827ea73d',0,'未添加后报备','beforeHandleBiz_unSubmittedBeforeApprovalList.action','9780ab30f05565c3d8650eeb4511df0c','未添加后报备',1),('b696d49e56cf5cdd2fff82fa46ee7eee',1,'添加评级机构','agc_createAgc.action','de36a47f82d4c43f52bd868538718986','添加评级机构',1),('b6ff4812abc9f641a524a99ef96e22be',3,'修改角色','roleManager_showEdit.action','466e77a7db14c22b6b81ce5f525a1c5a','修改角色页面',1),('bdfdb29bc58ed6f13ee26a05d150d41e',2,'删除评级机构','agc_deleteAgcs.action','de36a47f82d4c43f52bd868538718986','删除评级机构',1),('be337341229f80b9c7a9213bc951de2d',0,'金融机构列表','financialManager.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','金融机构列表',0),('c1e3bd0662787213c5604b79605ad5a2',1,'添加角色组','roleAllocateGroup_showAddGroup.action','62a4765e55e5d53bbca5da637d943250','组树页面',1),('c28ea286790a88f4f8c3af10a7a5fac8',1,'显示添加企业','firm_showAdd.action','18ca5055f31ed5ce9c1cbbdef37714eb','显示添加企业',1),('c71c78c6ee0f8a247f97e255f930cdb0',0,'分配组','userAllocateGroup.action','f9a67e8dcd4c90ed2c0916f3e86c424c','给用户分配组',0),('c791feb154e82f25fccf836f46983176',5,'执行禁用组','groupManager_disableGroup.action','3e09bff67a99411066a473501d04b128','执行禁用组',1),('c8e22ed7ade5cc88c5ee2f106d599922',5,'执行添加用户','userManager_addUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行添加用户',1),('cf24f9ec7cd2cef1dd4ba1d167bd0c80',5,'执行分配用户组','userAllocateGroup_addGroup.action','c71c78c6ee0f8a247f97e255f930cdb0','执行分配用户组',1),('d3992f4c96266e02b0c7e71b30735122',0,'显示评级机构高级查询','agc_showAdvanceSearch.action','de36a47f82d4c43f52bd868538718986','显示评级机构高级查询',1),('d58652e4bb1486a77973a53fb745a495',0,'分配组','orgAllocateGroup.action','55c07aa0aabec12504d58aef7527da9a','分配组',0),('d9fac0e985d150e919db31941b2fc5f3',2,'删除企业','firm_deleteFirms.action','18ca5055f31ed5ce9c1cbbdef37714eb','删除企业',1),('daed5775b8b537c9bc82d5d347dd8e68',1,'添加角色','roleManager_showAdd.action','466e77a7db14c22b6b81ce5f525a1c5a','添加角色页面',1),('de36a47f82d4c43f52bd868538718986',0,'评级机构信息','none',NULL,'评级机构信息',0),('de8e54904fce5eb389fd751223f4443d',4,'查询角色','roleManager_searchRole.action','466e77a7db14c22b6b81ce5f525a1c5a','执行查询角色',1),('e1fe09dd27e97fef10f3d54af1b097fe',0,'分配角色','userAllocateRole.action','f9a67e8dcd4c90ed2c0916f3e86c424c','用户分配角色页面',0),('e4a1ca7b3b69713c13c6396c761f94ad',0,'功能权限','none',NULL,'权限管理页面',0),('eff8877e6e4da9cf3988f5f13004c140',1,'添加企业','firm_createFirm.action','18ca5055f31ed5ce9c1cbbdef37714eb','添加企业',1),('f3229139692be9968dc8e6fbbee79aff',1,'添加功能','funcManager_showAdd.action','459b4d2258848fcd170be668433ab972','添加功能页面',1),('f3bbe986451be316082393701c4999f4',5,'执行分配角色组','roleAllocateGroup_addGroup.action','62a4765e55e5d53bbca5da637d943250','执行分配角色组',1),('f5a1aa0e31e6cb342b4345adfac1720e',5,'执行删除角色','userAllocateRole_disallocateRole.action','e1fe09dd27e97fef10f3d54af1b097fe','执行取消分配用户角色',1),('f7b56dfd694d638a1de49dc8722151bc',3,'修改企业','firm_editFirm.action','18ca5055f31ed5ce9c1cbbdef37714eb','修改企业',1),('f9a67e8dcd4c90ed2c0916f3e86c424c',0,'用户管理','userManager.action','e4a1ca7b3b69713c13c6396c761f94ad','用户管理页面',0),('f9d6d78e41c2a59573294198f7b4eddd',4,'企业查找带回','firm_lookup.action','18ca5055f31ed5ce9c1cbbdef37714eb','企业查找带回',1),('fabcf9de800a305990d0ba4e48a3c611',3,'显示修改评级机构','agc_showEdit.action','de36a47f82d4c43f52bd868538718986','显示修改评级机构',1),('fcae7af2109674b7d3baf921d7e78a36',5,'执行修改用户','userManager_editUser.action','f9a67e8dcd4c90ed2c0916f3e86c424c','执行修改用户',1),('fcf033d9052fb0824d5e487a3381aed4',5,'执行添加机构','supervisorManager_addSupervisor.action','4b41d098d6bbd29d8a21305666968e35','执行添加机构',1),('fd63dd0d94add6c287090528f8f20171',1,'添加组','groupManager_showAdd.action','3e09bff67a99411066a473501d04b128','添加组页面',1),('ffa02c53a375e227d119e12075f0ce2c',1,'添加机构','financialManager_showAdd.action','432bbf8c1484f93bd7b6b45dcc0c7a1f','显示添加机构页面',1);

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

insert  into `rbac_sys_group`(`groupid`,`name`,`description`,`disable`) values ('2ed83a1881b12c5cd34ad5592be4b7ea','评级机构A','评级机构A',0);

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

insert  into `rbac_sys_role`(`roleid`,`name`,`description`,`disable`) values ('10','评级机构','评级机构',0),('12','监管机构','监管机构',0),('14','企业','企业',0),('1a146a728eaddeb3f82924d88457b862','金融机构','金融机构',0),('2','访客','未登录时的角色',0),('5','系统管理员','系统管理员',0),('7','默认角色','默认角色',0);

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

insert  into `rbac_sys_role_function`(`rfid`,`roleid`,`functionid`,`comp`) values ('0ff3696955a23c4d685dd2d7bf0cecc0','7','86539c1a44d4b85acfb604b1fc6fa747',1),('10','5','3e09bff67a99411066a473501d04b128',1),('11','5','e4a1ca7b3b69713c13c6396c761f94ad',1),('12','2','2',1),('13','5','3',0),('14','5','9b03dbb48f164dbe8565fa5511d77894',1),('15','5','c8e22ed7ade5cc88c5ee2f106d599922',1),('16','5','749939bff702a796497ea19dc5b232eb',1),('17','5','713408074845e4df20a18f3a79960ee4',1),('176ff89c7063c5c322d88104e59b1162','5','0cd7e6daea6496bd09c66c588b66cbb8',1),('18','5','fcae7af2109674b7d3baf921d7e78a36',1),('19','5','3b9ac48532ae1881c02b73d393e399c1',1),('20','5','4f25729dd4e5fa78f7ea6ef62f9419c6',1),('21','5','819e4d5a1ad65be9cd9aad5e15ff2e8f',1),('22','5','20d072c2ef85c94a73dc958e0d6fd1ec',1),('23','5','5319068ab297603b110b166eeafe88ca',1),('24','5','f3229139692be9968dc8e6fbbee79aff',1),('25','5','45b26b37df8ed64c110a58324366bb2a',1),('26','5','19aeda50232bdd2b20293f5bed26a621',1),('27','5','de8e54904fce5eb389fd751223f4443d',1),('28','5','daed5775b8b537c9bc82d5d347dd8e68',1),('29','5','0a747791e9daa8d57757b1250caafbc3',1),('30','5','b6ff4812abc9f641a524a99ef96e22be',1),('31','5','1be858223db9e8a700a55c6c9bbb0416',1),('32','5','6b2ef4820f8dae21a7e5f69aa25e880c',1),('33','5','8aec30dde9780cbe6cf4bb0f87d9535a',1),('34','5','8db454f2855d7f7ff60957a8c8272f88',1),('35','5','16b3dddc497f6e549750e84306deeb9f',1),('36','5','16f4d5b96e063fabe7864ebb1ba57d45',1),('37','5','3074acb0e34fb16529dbe271a88e4d0b',1),('38','5','6f8766e906dbb8434adb57b203257bfc',1),('39','5','84d0fad8007df4be99e8734feb7e34a0',1),('3df4bae381fb38b9122f98a4b9f4f73d','5','78c645ed248092d9631df492b94be03a',1),('40','5','ade65031e5335a790af51af62b6df1ad',1),('402881e43b09693c013b09e8addb242b','10','9780ab30f05565c3d8650eeb4511df0c',1),('402881e43b09693c013b09e8addb242c','10','b54d4eba31a6619a34b5582a827ea73d',1),('402881e43b09693c013b09e8addb242d','10','46',1),('402881e43b09693c013b09e8addb242e','10','36',1),('402881e43b09693c013b09e8addb242f','10','39',1),('402881e43b09693c013b09e8addb2430','10','37',1),('402881e43b09693c013b09e8addb2431','10','38',1),('402881e43b09693c013b09e8addb2432','10','43',1),('402881e43b09693c013b09e8addb2433','10','42',1),('402881e43b09693c013b09e8addb2434','10','41',1),('402881e43b09693c013b09e8addb2435','10','40',1),('402881e43b09693c013b09e8addb2436','10','3439c4e598fe97a3a118ae600c6c1241',1),('402881e43b09693c013b09e8addb2437','10','561',1),('402881e43b09693c013b09e8addb2438','10','562',1),('402881e43b09693c013b09e8addb2439','10','563',1),('402881e43b09693c013b09e8addb243a','10','564',1),('402881e43b09693c013b09e8addb243b','10','0f536bfc241c4309b7578ae66da87846',1),('402881e43b09693c013b09e8addb243c','10','49',1),('402881e43b09693c013b09e8addb243d','10','33ec703fc43fc1303fbcc58c5ba66afa',1),('402881e43b09693c013b09e8addb243e','10','107',1),('402881e43b09693c013b09e8addb243f','10','106',1),('402881e43b09693c013b09e8addb2440','10','565',1),('402881e43b09693c013b09e8addb2441','10','51',1),('402881e43b09693c013b09e8addb2442','10','52',1),('402881e43b09693c013b09e8addb2443','10','105',1),('402881e43b09693c013b09e8addb2444','10','566',1),('402881e43b09693c013b09e8addb2445','10','53',1),('402881e43b09693c013b09e8addb2446','10','104',1),('402881e43b09693c013b09e8addb2447','10','103',1),('402881e43b09693c013b09e8addb2448','10','102',1),('402881e43b09693c013b09e8addb2449','10','101',1),('402881e43b09693c013b09e8addb244a','10','100',1),('402881e43b09693c013b09e8addb244b','10','50',1),('402881e43b09693c013b09e8addb244c','10','56',1),('402881e43b09693c013b09e905fd2644','10','48',1),('402881e43b09693c013b09e905fd2645','10','45',1),('402881e43b09693c013b09e93c492849','10','44',1),('41','5','c791feb154e82f25fccf836f46983176',1),('42','5','fd63dd0d94add6c287090528f8f20171',1),('46','5','62a4765e55e5d53bbca5da637d943250',1),('47','5','a7a3acda25aa61b68df481b48f422acc',1),('48','5','c71c78c6ee0f8a247f97e255f930cdb0',1),('49','5','e1fe09dd27e97fef10f3d54af1b097fe',1),('4d2e393fc453c3935e2acd862eca34e8','7','34ed49b9f4cd01a14c22c11497084ffc',1),('50','5','1dd6f5e857658293d764553639fef2be',1),('51','5','5e4296d7f9e25e8396236e746f992d0b',1),('52','5','4a97d695ccfec2d3a3a836bcd1a76dfb',1),('53','5','31b18197ebbb7f1f40b9a8b0f0656aae',1),('54','5','22c3df5cb702e1ee9ab8366a75169f03',1),('55','5','c1e3bd0662787213c5604b79605ad5a2',1),('56','5','6db9d77da526ceefffa7ccb7fe74e42a',1),('57','5','1607a9fdacdcca5a1eea815ade0e341b',1),('58','5','cf24f9ec7cd2cef1dd4ba1d167bd0c80',1),('59','5','f3bbe986451be316082393701c4999f4',1),('60','5','f5a1aa0e31e6cb342b4345adfac1720e',1),('6072c42f29701db169389161caa88dd5','5','42f2e461ffa558102fa9d832a5da8e81',1),('61','5','aa3daa58fff761dedbda4da0f56a704b',1),('62','5','0b42952892aa6fc77e8df67ae8648d38',1),('63','5','1b02224cc91f48d1e86918e2e5ed4c4a',1),('64','5','712cbbaca146f05a444b91233b735782',1),('65','5','72a66514b88dcb8667417dc3dd760704',1),('6ad01d1929108fa349b57a74975fdd8d','5','3a752a1e6546f7b9fd9db396f078ed7c',1),('7','5','f9a67e8dcd4c90ed2c0916f3e86c424c',1),('7ef0d20cf4d58303e9a34d01047f5989','5','999f0a740a6a7c6575265dc715a1e68f',1),('8','5','466e77a7db14c22b6b81ce5f525a1c5a',1),('9','5','459b4d2258848fcd170be668433ab972',1),('930aa8afc6c0c6d15ab644de9a95356d','5','55c07aa0aabec12504d58aef7527da9a',1),('aeb0a029367990f434496d4708bfc95b','7','30c0911079ba41404adfc112d126e910',1),('edef07614dad9d2923b3a2feffa26007','7','29a79d5ceda6a30f85399fcf92d4d189',1),('f66adf9fe0f6db35821f65fcf3ce7d94','5','d58652e4bb1486a77973a53fb745a495',1),('f71192ecf62f5279a18e88c0c08e24d2','7','1f3ba0870279fa625eb947ef7580df4a',1),('fe1d8b11ef3d649e2f37f6dabd71500f','7','3',1);

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

insert  into `rbac_sys_role_group`(`rgid`,`roleid`,`groupid`) values ('2ddda4b063c958123dd0c740739cad6d','10','2ed83a1881b12c5cd34ad5592be4b7ea');

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

insert  into `rbac_sys_user`(`userid`,`username`,`password`,`description`,`disable`) values ('3','guest','084e0343a0486ff05530df6c705c8bb4','guest',0),('5','admin','21232f297a57a5a743894a0e4a801fc3','系统管理员',0);

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

insert  into `rbac_sys_user_role`(`urid`,`userid`,`roleid`) values ('090989','3','2'),('1','5','2'),('402881e43b09693c013b096afe5b0454','5','7'),('7','5','5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
