mailingPrototype
================
运行UnitTest进行测试。

如果要配合数据库：
  1. 请先运行../src/main/resources/sql/inbox.sql创建表格
  2. 配置../src/main/resources/properties/database.properties属性,
  3. 修改../src/main/resources/hibernate/Mail.hbm.xml文件中
	      <class name="com.hesong.mail.model.Mail" table="cc_mail_t_inbox" catalog="mail">
	   catalog参数，catalog="数据库名"


* MySQL中"SSL"和"Interval"属于保留字段，insert时会引起错误，字段更名为"IS_SSL"和"TimeInterval". （坑爹）
