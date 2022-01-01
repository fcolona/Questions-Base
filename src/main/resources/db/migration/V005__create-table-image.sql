CREATE TABLE image (
	image_id INT NOT NULL AUTO_INCREMENT,
  	question_id INT NOT NULL,
  	content LONGBLOB NOT NULL,
  	
  PRIMARY KEY (image_id),
  FOREIGN KEY (question_id) REFERENCES question(question_id)
);