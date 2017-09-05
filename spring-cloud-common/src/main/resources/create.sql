CREATE TABLE
  user
(
  userId   INT NOT NULL AUTO_INCREMENT,
  userName VARCHAR(255),
  password VARCHAR(255),
  salt     VARCHAR(255),
  role     VARCHAR(255),
  PRIMARY KEY (userId)
)
  ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

CREATE TABLE
  simple_book
(
  bookName  VARCHAR(255),
  publisher VARCHAR(255)
            COLLATE utf8_general_ci,
  bookId    INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (bookId)
)
  ENGINE = MyISAM
  DEFAULT CHARSET = utf8;

