CREATE TABLE password_reset_token (
	token_id INT NOT NULL AUTO_INCREMENT,
  	token VARCHAR(36) NOT NULL,
  	user_id INT NOT NULL UNIQUE,
  	expire_date DATE NOT NULL,
  	
  	PRIMARY KEY (token_id),
  	FOREIGN KEY (user_id) REFERENCES user(user_id)
);