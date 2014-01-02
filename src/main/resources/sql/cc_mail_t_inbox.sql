/*
Navicat MySQL Data Transfer

Source Server         : BW_MYSQL
Source Server Version : 50154
Source Host           : localhost:3306
Source Database       : mail

Target Server Type    : MYSQL
Target Server Version : 50154
File Encoding         : 65001

Date: 2014-01-02 10:20:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cc_mail_t_inbox
-- ----------------------------
DROP TABLE IF EXISTS `cc_mail_t_inbox`;
CREATE TABLE `cc_mail_t_inbox` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UID` varchar(256) NOT NULL,
  `UnitID` varchar(32) NOT NULL,
  `Subject` varchar(100) NOT NULL,
  `Receiver` varchar(64) NOT NULL,
  `Sender` varchar(64) NOT NULL,
  `SentDate` datetime DEFAULT NULL,
  `Size` int(11) NOT NULL,
  `Content` text,
  `AttachmtDir` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8;
