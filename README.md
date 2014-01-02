mailingPrototype
================

  1. 请先运行../src/main/resources/sql/中的sql文件创建表格
  2. 配置../src/main/resources/properties/database.properties属性,
  3. 修改../src/main/resources/hibernate/*.hbm.xml文件中的catalog参数值
	    例：  <class name="com.hesong.mail.model.Mail" table="cc_mail_t_inbox" catalog="mail">
	   catalog参数，catalog="数据库名"

Jar运行：
	1.创建Runnable Jar
	2.命令行： java -jar runnable.jar ftphostName username passwor

Eclipse运行：
	在run configuration中添加arguments值：ftphostName username passwor
	
所有附件都会按照命名规则上传到FTP服务器根目录下Attachment_for_Client文件夹中


* MySQL中"SSL"和"Interval"属于保留字段，insert时会引起错误，字段更名为"IS_SSL"和"TimeInterval". （坑爹）
