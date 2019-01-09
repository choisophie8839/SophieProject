
/* Drop Triggers */

DROP TRIGGER TRI_EH_BOARD_num;



/* Drop Tables */

DROP TABLE EH_FILE CASCADE CONSTRAINTS;
DROP TABLE EH_BOARD CASCADE CONSTRAINTS;
DROP TABLE EH_FILM CASCADE CONSTRAINTS;
DROP TABLE EH_MEMBER CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE SEQ_EH_BOARD_num;




/* Create Sequences */

CREATE SEQUENCE SEQ_EH_BOARD_num INCREMENT BY 1 START WITH 1;



/* Create Tables */

CREATE TABLE EH_BOARD
(
	num number NOT NULL,
	id varchar2(20) NOT NULL,
	subject varchar2(50),
	re_f number NOT NULL,
	re_step number NOT NULL,
	re_level number NOT NULL,
	re_type number NOT NULL,
	content varchar2(4000) NOT NULL,
	score number,
	readcount number,
	reg_date timestamp NOT NULL,
	movieNm varchar2(50) NOT NULL,
	PRIMARY KEY (num)
);


CREATE TABLE EH_FILE
(
	num number NOT NULL,
	org_fileNm varchar2(300) NOT NULL,
	std_fileNm varchar2(300) NOT NULL,
	filereg_date timestamp NOT NULL
);


CREATE TABLE EH_FILM
(
	movieCd varchar2(50),
	movieNm varchar2(50) NOT NULL UNIQUE,
	movieNmEn varchar2(50),
	prdtYear varchar2(30),
	openDt varchar2(30),
	repNationNm varchar2(30),
	repGenreNm varchar2(50),
	PRIMARY KEY (movieNm)
);


CREATE TABLE EH_MEMBER
(
	id varchar2(20) NOT NULL,
	passwd varchar2(20) NOT NULL,
	email varchar2(40) NOT NULL,
	tel varchar2(13),
	birth date NOT NULL,
	gender char NOT NULL,
	address1 varchar2(150) NOT NULL,
	address2 varchar2(90),
	reg_date timestamp NOT NULL,
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE EH_FILE
	ADD FOREIGN KEY (num)
	REFERENCES EH_BOARD (num)
;


ALTER TABLE EH_BOARD
	ADD FOREIGN KEY (movieNm)
	REFERENCES EH_FILM (movieNm)
;


ALTER TABLE EH_BOARD
	ADD FOREIGN KEY (id)
	REFERENCES EH_MEMBER (id)
;




