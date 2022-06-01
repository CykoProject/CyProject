-- TODO 쇼핑 DB 수정해야함

CREATE OR replace TABLE home_profile(
	iprofile INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
	ctnt TEXT,
	img VARCHAR(100),
	rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

-- *** 2022-05-26 수정 ( font 추가 ) ***
CREATE OR replace TABLE home_diary(
	idiary INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
	ctnt TEXT NOT NULL,
    ifont INT UNSIGNED DEFAULT 0,
	rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

CREATE OR replace TABLE home_jukebox(
	ijukebox INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	imusic INT UNSIGNED NOT NULL,
	ihost INT UNSIGNED NOT NULL,
	repre BOOLEAN DEFAULT FALSE
);

-- 2022-05-29 수정 --
CREATE OR replace TABLE home_photos(
	iphoto INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
# 	ifile INT UNSIGNED NOT NULL,
	title TEXT NOT NULL,
	ctnt TEXT NOT NULL,
	rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
	scrap INT UNSIGNED DEFAULT 0
);

-- *** 2022-04-21 수정 ( iminime 추가 ) ***
-- *** 2022-05-26 수정 ( font 추가 ) ***
CREATE OR replace TABLE home_visit(
	ivisit INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
	ctnt TEXT NOT NULL,
	iuser INT UNSIGNED NOT NULL,
	iminime INT UNSIGNED DEFAULT 0,
    ifont INT UNSIGNED DEFAULT 0,
	secret BOOLEAN DEFAULT FALSE,
	rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

-- 2022-05-30 my_room -> myroom 이름 수정 --
CREATE OR replace TABLE home_mini_room(
	iroom INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
	myroom INT UNSIGNED NOT NULL,
	repre BOOLEAN DEFAULT FALSE
);

-- *** 2022-04-21 mini_room, skin, music, font, mini_me 수정 ( cnt 뺌 )
CREATE OR replace TABLE mini_room(
	iroom INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm TEXT NOT NULL,
	img TEXT NOT NULL,
	price INT UNSIGNED NOT null
);

CREATE OR replace TABLE skin(
	iskin INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm TEXT NOT NULL,
	img TEXT NOT NULL,
	price INT UNSIGNED NOT NULL
);

CREATE OR replace TABLE mini_me(
	imini_me INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm TEXT NOT NULL,
	img TEXT NOT NULL,
	price INT UNSIGNED NOT NULL
);

CREATE OR replace TABLE font(
	ifont INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm TEXT NOT NULL,
	img TEXT NOT NULL,
	price INT UNSIGNED NOT NULL
);
-- *** 2022-04-21 mini_room, skin, music, font, mini_me 수정 ( cnt 뺌 )

-- 2022-05-29 home_nm 추가 --
CREATE OR REPLACE TABLE home( # 미니홈피
   ihome INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   diary BOOLEAN DEFAULT FALSE,
   photo BOOLEAN DEFAULT FALSE,
   visit BOOLEAN DEFAULT FALSE,
   jukebox BOOLEAN DEFAULT FALSE,
   mini_room BOOLEAN DEFAULT FALSE,
   scrap_cnt INT UNSIGNED DEFAULT 0,
   daily_visit INT UNSIGNED default 0,
   total_visit INT UNSIGNED DEFAULT 0,
    home_nm     VARCHAR(20),
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
   # 0 전체공개
   # 1 일촌공개
   # 2 비공개
   scope INT UNSIGNED DEFAULT 1 CHECK (scope IN(0, 1, 2))
);

######## join( user ) ########
# ihost => 미니홈피 주인     #
# writer => 일촌의 글        #
#############################

CREATE OR REPLACE TABLE `comment` (
   icmt INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   ihost INT UNSIGNED NOT NULL,
   category INT UNSIGNED NOT NULL,
   iboard INT UNSIGNED NOT NULL,
   ctnt TEXT NOT NULL,
   writer INT UNSIGNED NOT NULL,
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

-- 2022-05-29 수정 --
CREATE OR REPLACE TABLE photos (
   iphoto BIGINT UNSIGNED,
#    ihost INT UNSIGNED NOT NULL,
   img    VARCHAR(50)     NOT NULL,
    PRIMARY KEY (iphoto, img)
);

CREATE OR replace TABLE music(
	imusic INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm TEXT NOT NULL,
	artist TEXT NOT NULL,
	url TEXT NOT NULL,
	img TEXT,
	cnt INT UNSIGNED DEFAULT 0,
	price INT UNSIGNED NOT null
);

-- 2022-05-29 profile_img, profile_ctnt 추가 --
CREATE OR REPLACE TABLE `user`(
   iuser INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
   email VARCHAR(50) NOT NULL,
   nm VARCHAR(5) NOT NULL,
   upw VARCHAR(200),
   provider VARCHAR(10) NOT NULL DEFAULT 'local',
   img VARCHAR(50), # 미니미,
    profile_img  VARCHAR(100),
    profile_ctnt TEXT,
   `point` INT UNSIGNED DEFAULT 0,
   rdt DATE DEFAULT CURRENT_TIMESTAMP()
);

-- *** 2022-04-29 msg_savebox 테이블 추가, message (remove) 컬럼 추가
CREATE OR REPLACE TABLE msg_savebox (
	ibox INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	iuser INT UNSIGNED NOT NULL,
	imsg INT UNSIGNED NOT NULL
);


CREATE OR REPLACE TABLE message( # 쪽지
   imsg INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   receiver INT UNSIGNED NOT NULL,
   recv_read BOOLEAN NOT NULL DEFAULT FALSE,
   ctnt TEXT NOT NULL,
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
   remove_iuser BOOLEAN NOT NULL DEFAULT FALSE,
   remove_receiver BOOLEAN NOT NULL DEFAULT FALSE
);
-- 2022-04-29 msg_savebox 추가, message 컬럼 추가 --

CREATE OR REPLACE TABLE friends( # 일촌 ( 신청, 목록 )
   ifriend INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   fuser INT UNSIGNED NOT NULL,
   `status` BOOLEAN DEFAULT FALSE,
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
   nickname VARCHAR(10) NOT NULL
);

CREATE OR REPLACE TABLE my_item(
   imyitem INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   category INT UNSIGNED NOT NULL,
   item_id INT UNSIGNED NOT NULL,
   rdt DATE DEFAULT CURRENT_TIMESTAMP()
);

CREATE OR REPLACE TABLE item_category(
   icategory INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   nm VARCHAR(20) NOT null
);

#CREATE OR REPLACE TABLE shop(
#   item_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
#   icategory INT UNSIGNED NOT NULL
#);

CREATE OR REPLACE TABLE purchase_history(
   ipurchase INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   ishop INT UNSIGNED NOT NULL,
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

CREATE OR REPLACE TABLE point_history(
   ipoint INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   iuser INT UNSIGNED NOT NULL,
   changed_point INT NOT NULL,
   reason VARCHAR(10) NOT NULL,
   rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

CREATE OR replace TABLE report(
	ireport INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	icategory INT UNSIGNED NOT NULL,
	iboard INT UNSIGNED NOT NULL,
	reporter INT UNSIGNED NOT NULL, # 신고자
	reason TEXT NOT NULL,
	iuser INT UNSIGNED NOT NULL,
	rdt DATETIME DEFAULT CURRENT_TIMESTAMP()
);

-- 2022-05-29 rdt 추가 --
CREATE OR REPLACE TABLE home_message( # 일촌평
	imsg INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	ihost INT UNSIGNED NOT NULL,
	ctnt TEXT NOT NULL,
	writer INT UNSIGNED NOT NULL,
    rdt    DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()
);

# -2022-05-13-
-- 2022-05-30 CHECK(itemcategory <= 5) 수정 --
-- ++ item_category 테이블에 5번 미니룸 데이터 추가 --
CREATE OR REPLACE TABLE item (
                                 item_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                                 nm VARCHAR(50) NOT NULL,
                                 artist VARCHAR(50), # null 가능
                                 price INT UNSIGNED NOT NULL,
                                 icategory INT UNSIGNED,
                                 `file` VARCHAR(200),
                                 rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
                                 CHECK(icategory <= 5)
);

CREATE OR REPLACE TABLE item_like (
                                      ilike INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                                      iuser INT UNSIGNED NOT null,
                                      item_id INT UNSIGNED NOT null,
                                      FOREIGN KEY (iuser) REFERENCES user(iuser),
                                      FOREIGN KEY (item_id) REFERENCES item(item_id)
);

CREATE OR REPLACE TABLE sell_history (
                                         ihistory INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                                         iuser INT UNSIGNED NOT null,
                                         item_id INT UNSIGNED NOT null,
                                         rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
                                         FOREIGN KEY (iuser) REFERENCES user(iuser),
                                         FOREIGN KEY (item_id) REFERENCES item(item_id)
CREATE OR REPLACE TABLE purchase_history (
    ihistory INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    iuser INT UNSIGNED NOT null,
    item_id INT UNSIGNED NOT null,
    rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
    FOREIGN KEY (iuser) REFERENCES user(iuser),
    FOREIGN KEY (item_id) REFERENCES item(item_id)
);

CREATE OR REPLACE table cart (
    icart int unsigned primary key auto_increment,
    iuser int unsigned not null,
    item_id int unsigned not null,
    cnt int unsigned default 1,
    rdt datetime default current_timestamp()
);

CREATE OR REPLACE table order_info(
                                      order_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                                      iuser INT UNSIGNED NOT NULL,
                                      item_nm VARCHAR(50) NOT null,
                                      quantity INT UNSIGNED,
                                      rdt DATETIME DEFAULT CURRENT_TIMESTAMP(),
                                      FOREIGN KEY (iuser) REFERENCES user(iuser)
);

-- 2022-05-31 테이블 추가 --
CREATE OR REPLACE table `cyworld`.`board_list`
(
  iuser INT UNSIGNED NOT NULL,
  iphoto BIGINT UNSIGNED NOT NULL,
  scrap BOOLEAN NOT NULL DEFAULT FALSE
);


/*

    2022-05-31 수정 내용

    - home_mini_room 테이블에 컬럼 수정
    my_room -> myroom 이름 수정

    - Item 테이블에 컬럼 수정
    CHECK(itemcategory <= 5) 로 수정

    - board_list 테이블 생성

    - item_category 테이블에 데이터 추가
    (5, ‘미니룸’) 데이터 추가


 */


/* item 테이블에 미니룸 상품 테스트 데이터로 쓰면 됩니다 */
INSERT INTO `cyworld`.`item` (`nm`, `artist`, `price`, `icategory`, `file`)
VALUES ('orange', 'test', 5, 5, '/img/miniroom/miniroom_orange.jpg'),
  ('yellow', 'test', 5, 5, '/img/miniroom/miniroom_yellow.jpg'),
  ('green', 'test', 5, 5, '/img/miniroom/miniroom_green.jpg'),
  ('blue', 'test', 5, 5, '/img/miniroom/miniroom_blue.jpg');