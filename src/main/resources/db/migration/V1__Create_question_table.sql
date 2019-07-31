create table QUESTION
(
	ID INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_A69FE8C0_88C1_4B9F_967E_0E233D2CC462) auto_increment,
	TITLE VARCHAR(50),
	DESCRIPTION CLOB,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	CREATOR INTEGER,
	COMMENT_COUNT INTEGER default 0,
	VIEW_COUNT INTEGER default 0,
	LIKE_COUNT INTEGER default 0,
	TAG VARCHAR(256),
	NEW_COLUMN INTEGER,
	constraint QUESTION_PK
		primary key (ID)
);

