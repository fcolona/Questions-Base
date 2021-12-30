CREATE TABLE question (
	question_id int not null AUTO_INCREMENT,
  	exam varchar(255) not null,
  	year int not null,
  	subject varchar(255) not null,
  	slug varchar(255) not null UNIQUE,
  	stem text not null,

  	PRIMARY KEY(question_id)
);

CREATE TABLE alternative (
	alternative_id int not null AUTO_INCREMENT,
  	question_id int not null,
  	content text not null,
	is_correct boolean not null,
  
  	PRIMARY KEY(alternative_id),
  	FOREIGN KEY (question_id) REFERENCES question(question_id)
);