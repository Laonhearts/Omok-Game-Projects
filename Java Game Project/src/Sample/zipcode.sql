CREATE TABLE zipcode (
                         zipcode        VARCHAR(5) NULL,
                         sido           VARCHAR(25) NULL,
                         sido_en        VARCHAR(20) NULL,
                         sigungu        VARCHAR(30) NULL,
                         sigungu_en     VARCHAR(30) NULL,
                         eupmyun        VARCHAR(20) NULL,
                         eupmyun_en     VARCHAR(25) NULL,
                         doro_code      VARCHAR(12) NULL,
                         doro           VARCHAR(40) NULL,
                         doro_en        VARCHAR(50) NULL,
                         under_yn       VARCHAR(1) NULL,
                         buildno1       VARCHAR(5) NULL,
                         buildno2       VARCHAR(4) NULL,
                         buildnum       VARCHAR(25) NULL,
                         multiple       VARCHAR(1) NULL,
                         buildname      VARCHAR(70) NULL,
                         dong_code      VARCHAR(10) NULL,
                         dong           VARCHAR(20) NULL,
                         ri             VARCHAR(20) NULL,
                         dong_hj        VARCHAR(30) NULL,
                         mount_yn       VARCHAR(1) NULL,
                         jibun1         VARCHAR(4) NULL,
                         eupmyundong_no VARCHAR(2) NULL,
                         jibun2         VARCHAR(4) NULL,
                         zipcode_old    VARCHAR(7) NULL,
                         zipcode_seq    VARCHAR(3) NULL,
                         idx            INT(10)    UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;