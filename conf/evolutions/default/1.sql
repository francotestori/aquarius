# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint not null,
  comment                   varchar(255),
  date                      timestamp,
  user_id                   bigint,
  project_id                bigint,
  constraint pk_comment primary key (id))
;

create table country (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_country primary key (id))
;

create table fund (
  id                        bigint not null,
  amount                    integer,
  user_id                   bigint,
  project_id                bigint,
  constraint pk_fund primary key (id))
;

create table image (
  id                        bigint not null,
  project_id                bigint not null,
  path                      varchar(255),
  constraint pk_image primary key (id))
;

create table message (
  id                        bigint not null,
  subject                   varchar(255),
  message                   varchar(255),
  date                      timestamp,
  sender_id                 bigint,
  read                      boolean,
  constraint pk_message primary key (id))
;

create table notification (
  id                        bigint not null,
  message                   varchar(255),
  date                      timestamp,
  user_id                   bigint,
  read                      boolean,
  constraint pk_notification primary key (id))
;

create table project (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  faq                       varchar(255),
  start                     timestamp,
  end                       timestamp,
  objective                 integer,
  html                      varchar(255),
  country_id                bigint,
  type_id                   bigint,
  user_id                   bigint,
  constraint pk_project primary key (id))
;

create table tag (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_tag primary key (id))
;

create table type (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_type primary key (id))
;

create table update (
  id                        bigint not null,
  project_id                bigint,
  date                      timestamp,
  message                   varchar(255),
  constraint pk_update primary key (id))
;

create table user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  birthday                  timestamp,
  email                     varchar(255),
  password                  varchar(255),
  reputation                bigint,
  profile_picture_id        bigint,
  confirmed_email           boolean,
  country_id                bigint,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;


create table MESSAGE_USER (
  message_id                     bigint not null,
  RECIPIENT_ID                   bigint not null,
  constraint pk_MESSAGE_USER primary key (message_id, RECIPIENT_ID))
;

create table project_tag (
  project_id                     bigint not null,
  tag_id                         bigint not null,
  constraint pk_project_tag primary key (project_id, tag_id))
;

create table project_user (
  project_id                     bigint not null,
  user_id                        bigint not null,
  constraint pk_project_user primary key (project_id, user_id))
;

create table USER_FOLLOWERS (
  user_id                        bigint not null,
  FOLLOWER_ID                    bigint not null,
  constraint pk_USER_FOLLOWERS primary key (user_id, FOLLOWER_ID))
;

create table user_message (
  user_id                        bigint not null,
  message_id                     bigint not null,
  constraint pk_user_message primary key (user_id, message_id))
;
create sequence comment_seq;

create sequence country_seq;

create sequence fund_seq;

create sequence image_seq;

create sequence message_seq;

create sequence notification_seq;

create sequence project_seq;

create sequence tag_seq;

create sequence type_seq;

create sequence update_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_1 on comment (user_id);
alter table comment add constraint fk_comment_project_2 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_comment_project_2 on comment (project_id);
alter table fund add constraint fk_fund_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_fund_user_3 on fund (user_id);
alter table fund add constraint fk_fund_project_4 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_fund_project_4 on fund (project_id);
alter table image add constraint fk_image_project_5 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_image_project_5 on image (project_id);
alter table message add constraint fk_message_sender_6 foreign key (sender_id) references user (id) on delete restrict on update restrict;
create index ix_message_sender_6 on message (sender_id);
alter table notification add constraint fk_notification_user_7 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_notification_user_7 on notification (user_id);
alter table project add constraint fk_project_country_8 foreign key (country_id) references country (id) on delete restrict on update restrict;
create index ix_project_country_8 on project (country_id);
alter table project add constraint fk_project_type_9 foreign key (type_id) references type (id) on delete restrict on update restrict;
create index ix_project_type_9 on project (type_id);
alter table project add constraint fk_project_user_10 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_project_user_10 on project (user_id);
alter table update add constraint fk_update_project_11 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_update_project_11 on update (project_id);
alter table user add constraint fk_user_profilePicture_12 foreign key (profile_picture_id) references image (id) on delete restrict on update restrict;
create index ix_user_profilePicture_12 on user (profile_picture_id);
alter table user add constraint fk_user_country_13 foreign key (country_id) references country (id) on delete restrict on update restrict;
create index ix_user_country_13 on user (country_id);



alter table MESSAGE_USER add constraint fk_MESSAGE_USER_message_01 foreign key (message_id) references message (id) on delete restrict on update restrict;

alter table MESSAGE_USER add constraint fk_MESSAGE_USER_user_02 foreign key (RECIPIENT_ID) references user (id) on delete restrict on update restrict;

alter table project_tag add constraint fk_project_tag_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_tag add constraint fk_project_tag_tag_02 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table USER_FOLLOWERS add constraint fk_USER_FOLLOWERS_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table USER_FOLLOWERS add constraint fk_USER_FOLLOWERS_user_02 foreign key (FOLLOWER_ID) references user (id) on delete restrict on update restrict;

alter table user_message add constraint fk_user_message_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_message add constraint fk_user_message_message_02 foreign key (message_id) references message (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists country;

drop table if exists fund;

drop table if exists image;

drop table if exists message;

drop table if exists MESSAGE_USER;

drop table if exists notification;

drop table if exists project;

drop table if exists project_tag;

drop table if exists project_user;

drop table if exists tag;

drop table if exists type;

drop table if exists update;

drop table if exists user;

drop table if exists USER_FOLLOWERS;

drop table if exists user_message;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists country_seq;

drop sequence if exists fund_seq;

drop sequence if exists image_seq;

drop sequence if exists message_seq;

drop sequence if exists notification_seq;

drop sequence if exists project_seq;

drop sequence if exists tag_seq;

drop sequence if exists type_seq;

drop sequence if exists update_seq;

drop sequence if exists user_seq;

