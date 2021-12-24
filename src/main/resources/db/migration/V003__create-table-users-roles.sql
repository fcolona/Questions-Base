CREATE TABLE users_roles (
	id INT(11) NOT NULL AUTO_INCREMENT,
  	user_id INT(11) NOT NULL,
	role_id INT(11) NOT NULL,
  	FOREIGN KEY (user_id) REFERENCES user(user_id),
  	FOREIGN KEY (role_id) REFERENCES role(role_id),
  	PRIMARY KEY (id)
);