/*
Navicat MySQL Data Transfer

Source Server         : BW_MYSQL
Source Server Version : 50154
Source Host           : localhost:3306
Source Database       : mail

Target Server Type    : MYSQL
Target Server Version : 50154
File Encoding         : 65001

Date: 2014-01-02 10:20:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cc_mgr_t_email
-- ----------------------------
DROP TABLE IF EXISTS `cc_mgr_t_email`;
CREATE TABLE `cc_mgr_t_email` (
  `ID` varchar(32) NOT NULL,
  `SendServer` varchar(64) NOT NULL,
  `ReceiveServer` varchar(64) NOT NULL,
  `Account` varchar(32) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `IS_SSL` char(1) NOT NULL DEFAULT '0',
  `TimeInterval` int(11) DEFAULT '600',
  `Remark` varchar(256) DEFAULT NULL,
  `UnitID` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cc_mgr_t_email
-- ----------------------------
INSERT INTO `cc_mgr_t_email` VALUES ('asdasd', 'asdasd', 'pop3.163.com', 'bowen_test11@163.com', 'waiwai33', '0', '100', 'sfdf', 'asdkadj');
INSERT INTO `cc_mgr_t_email` VALUES ('asdw', 'ssssss', '125.93.53.89', 'test@koyoo.cn', 'test123456', '0', '150', null, 'aaaa');
