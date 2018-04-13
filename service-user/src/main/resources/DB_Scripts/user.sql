/*
  用户基本信息表
 */
CREATE TABLE user
(
  userid        VARCHAR(40)                         NOT NULL PRIMARY KEY,
  password      VARCHAR(50)                         NULL,
  username      VARCHAR(40)                         NULL,
  nickname      VARCHAR(40)                         NULL
  COMMENT '昵称',
  usertype      INT                                 NULL
  COMMENT '用户类型',
  recommender   INT                                 NULL
  COMMENT '邀请人推荐码',
  province      VARCHAR(40)                         NULL
  COMMENT '省份',
  city          VARCHAR(40)                         NULL
  COMMENT '城市',
  address       VARCHAR(400)                        NULL
  COMMENT '家庭地址',
  organize_id   VARCHAR(200)                        NULL
  COMMENT '单位名称',
  headimage_url VARCHAR(500)                        NULL,
  phone_num     VARCHAR(20)                         NOT NULL
  COMMENT '手机号',
  regist_date   DATETIME DEFAULT CURRENT_TIMESTAMP  NOT NULL
  COMMENT '注册时间',
  modify_time   DATETIME DEFAULT CURRENT_TIMESTAMP  NOT NULL
  COMMENT '修改时间',
  valid_tag     VARCHAR(4)                          NULL,
  CONSTRAINT user_phone_num_uindex
  UNIQUE (phone_num),
  CONSTRAINT user_username_uindex UNIQUE (username)
);

/*
用户账户表
 */
CREATE TABLE account
(
  userid        VARCHAR(40)                        NOT NULL
    PRIMARY KEY,
  balance       DECIMAL(10, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '账户余额',
  bank_num      VARCHAR(50)                        NULL
  COMMENT '银行卡号码',
  identify_card VARCHAR(50)                        NULL
  COMMENT '身份证号码',
  binding_phone VARCHAR(30)                        NULL
  COMMENT '银行绑定手机号码',
  bank_province VARCHAR(40)                        NULL
  COMMENT '开户银行身份',
  bank_city     VARCHAR(40)                        NULL
  COMMENT '开户银行城市',
  bank_name     VARCHAR(200)                       NULL
  COMMENT '开户银行名称',
  bank_deposit  VARCHAR(400)                       NULL
  COMMENT '开户银行详细信息',
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  modify_time   DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  valid_tag     CHAR DEFAULT 'Y'                   NULL
  COMMENT '有效标志',
  CONSTRAINT account_user_userid_fk
  FOREIGN KEY (userid) REFERENCES smartnew.user (userid)
);
