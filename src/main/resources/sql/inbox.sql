CREATE TABLE `cc_mail_t_inbox` (
	`UID` VARCHAR(100) NOT NULL,
	`UnitID` VARCHAR(32) NOT NULL,
	`Subject` VARCHAR(50) NOT NULL,
	`Receiver` VARCHAR(30) NOT NULL,
	`Sender` VARCHAR(30) NOT NULL,
	`SentDate` TIMESTAMP NULL DEFAULT NULL,
	`Size` INT(11) NOT NULL,
	`Content` VARCHAR(100) NOT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;