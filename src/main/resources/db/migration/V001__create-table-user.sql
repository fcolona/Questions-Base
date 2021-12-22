CREATE TABLE `user` (
	`user_id` INT(11) NOT NULL AUTO_INCREMENT,
	`email` varchar(255) NOT NULL,
	`password` varchar(60),
	`provider` varchar(15) NOT NULL,
	PRIMARY KEY (`user_id`)
);