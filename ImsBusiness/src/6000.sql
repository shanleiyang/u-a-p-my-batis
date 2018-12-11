???prompt PL/SQL Developer import file
prompt Created on 2018年11月28日 by 18003829186
set feedback off
set define off
prompt Creating BI_IMS_SERVICE_APP_INDI...
create table BI_IMS_SERVICE_APP_INDI
(
  sgb_id     VARCHAR2(30 CHAR),
  stat_year  NUMBER(8),
  stat_month NUMBER(8),
  indi_type  VARCHAR2(8 CHAR),
  indi_name  VARCHAR2(64 CHAR),
  indi_value VARCHAR2(32 CHAR),
  sync_time  DATE not null
)
tablespace MOCSBD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating BI_IMS_SERVICE_RUN_INDI...
create table BI_IMS_SERVICE_RUN_INDI
(
  sgb_id     VARCHAR2(30 CHAR),
  stat_year  NUMBER(8),
  stat_month NUMBER(8),
  indi_type  VARCHAR2(8 CHAR),
  indi_name  VARCHAR2(64 CHAR),
  indi_value VARCHAR2(32 CHAR),
  sync_time  DATE not null
)
tablespace MOCSBD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating I_IMS_MONITOR...
create table I_IMS_MONITOR
(
  monitor_date DATE,
  status_code  VARCHAR2(16)
)
tablespace MOCSBD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating SYS_USER_LOGIN...
create table SYS_USER_LOGIN
(
  id             VARCHAR2(32) not null,
  login_name     VARCHAR2(32),
  ip             VARCHAR2(32),
  online_type    VARCHAR2(32),
  login_time     TIMESTAMP(6),
  login_out_time TIMESTAMP(6),
  user_id        VARCHAR2(32)
)
tablespace BD_PROJECT_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_LOGIN
  is '用户在线状态';
comment on column SYS_USER_LOGIN.login_name
  is '用户登录账号';
comment on column SYS_USER_LOGIN.ip
  is '登录ip';
comment on column SYS_USER_LOGIN.online_type
  is '在线状态：0,不在线;1,在线';
comment on column SYS_USER_LOGIN.login_time
  is '登录时间';
comment on column SYS_USER_LOGIN.login_out_time
  is '注销时间或会话到期';
comment on column SYS_USER_LOGIN.user_id
  is '用户id';
alter table SYS_USER_LOGIN
  add constraint PK_SYS_USER_LOGIN primary key (ID)
  using index 
  tablespace MOCSBD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating SYS_USER_SESSION...
create table SYS_USER_SESSION
(
  id          VARCHAR2(32) not null,
  login_name  VARCHAR2(32),
  session_num NUMBER(6) default 0,
  user_id     VARCHAR2(32)
)
tablespace BD_PROJECT_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_SESSION
  is '用户打开的页签数量';
comment on column SYS_USER_SESSION.login_name
  is '用户登录账号';
comment on column SYS_USER_SESSION.session_num
  is '页签数量';
comment on column SYS_USER_SESSION.user_id
  is '用户id';
alter table SYS_USER_SESSION
  add constraint PK_SYS_USER_SESSION primary key (ID)
  using index 
  tablespace MOCSBD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for BI_IMS_SERVICE_APP_INDI...
alter table BI_IMS_SERVICE_APP_INDI disable all triggers;
prompt Disabling triggers for BI_IMS_SERVICE_RUN_INDI...
alter table BI_IMS_SERVICE_RUN_INDI disable all triggers;
prompt Disabling triggers for I_IMS_MONITOR...
alter table I_IMS_MONITOR disable all triggers;
prompt Disabling triggers for SYS_USER_LOGIN...
alter table SYS_USER_LOGIN disable all triggers;
prompt Disabling triggers for SYS_USER_SESSION...
alter table SYS_USER_SESSION disable all triggers;
prompt Deleting SYS_USER_SESSION...
delete from SYS_USER_SESSION;
commit;
prompt Deleting SYS_USER_LOGIN...
delete from SYS_USER_LOGIN;
commit;
prompt Deleting I_IMS_MONITOR...
delete from I_IMS_MONITOR;
commit;
prompt Deleting BI_IMS_SERVICE_RUN_INDI...
delete from BI_IMS_SERVICE_RUN_INDI;
commit;
prompt Deleting BI_IMS_SERVICE_APP_INDI...
delete from BI_IMS_SERVICE_APP_INDI;
commit;
prompt Loading BI_IMS_SERVICE_APP_INDI...
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191597', 2015, 12, '02', '三相电能表配送检定率', '100', to_date('01-12-2015 00:00:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191599', 2015, 12, '04', '低压电流互感器到货批次数', '0', to_date('01-12-2015 00:00:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191600', 2015, 12, '05', '采集终端到货批次数', '0', to_date('01-12-2015 00:00:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191601', 2015, 12, '06', '低压电流互感器检定任务单数', '54', to_date('01-12-2015 00:00:06', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191602', 2015, 12, '07', '采集终端检定任务单数', '6', to_date('01-12-2015 00:00:09', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191603', 2015, 12, '08', '电能表配送任务单数', '0', to_date('01-12-2015 00:00:13', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191604', 2015, 12, '09', '低压电流互感器配送任务单数', '0', to_date('01-12-2015 00:00:14', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191605', 2015, 12, '10', '采集终端配送任务单数', '0', to_date('01-12-2015 00:00:14', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191596', 2015, 12, '01', '单相电能表配送检定率', '100', to_date('01-12-2015 00:00:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_APP_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('191598', 2015, 12, '03', '低压电流互感器配送检定率', '100', to_date('01-12-2015 00:00:02', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 10 records loaded
prompt Loading BI_IMS_SERVICE_RUN_INDI...
insert into BI_IMS_SERVICE_RUN_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('216322', 2015, 12, '08', '业务应用系统占用表空间大小', '2411.86', to_date('29-12-2015 14:34:06', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_RUN_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('216321', 2015, 12, '05', '页面会话连接数', '234', to_date('29-12-2015 14:34:04', 'dd-mm-yyyy hh24:mi:ss'));
insert into BI_IMS_SERVICE_RUN_INDI (sgb_id, stat_year, stat_month, indi_type, indi_name, indi_value, sync_time)
values ('216323', 2015, 12, '09', '系统平均响应时长', '359.64', to_date('29-12-2015 14:34:06', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 3 records loaded
prompt Loading I_IMS_MONITOR...
insert into I_IMS_MONITOR (monitor_date, status_code)
values (to_date('03-02-2018 17:51:19', 'dd-mm-yyyy hh24:mi:ss'), '01');
commit;
prompt 1 records loaded
prompt Loading SYS_USER_LOGIN...
insert into SYS_USER_LOGIN (id, login_name, ip, online_type, login_time, login_out_time, user_id)
values ('1279eb64eb6e9301675855e177004c', 'P39220006', '10.153.0.215', '1', to_timestamp('28-11-2018 11:21:22.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, '17DAB32008EF8F98E050930A22FC2A94');
commit;
prompt 1 records loaded
prompt Loading SYS_USER_SESSION...
insert into SYS_USER_SESSION (id, login_name, session_num, user_id)
values ('1279eb64eb6e9301675855e23a004d', 'P39220006', 4, '17DAB32008EF8F98E050930A22FC2A94');
commit;
prompt 1 records loaded
prompt Enabling triggers for BI_IMS_SERVICE_APP_INDI...
alter table BI_IMS_SERVICE_APP_INDI enable all triggers;
prompt Enabling triggers for BI_IMS_SERVICE_RUN_INDI...
alter table BI_IMS_SERVICE_RUN_INDI enable all triggers;
prompt Enabling triggers for I_IMS_MONITOR...
alter table I_IMS_MONITOR enable all triggers;
prompt Enabling triggers for SYS_USER_LOGIN...
alter table SYS_USER_LOGIN enable all triggers;
prompt Enabling triggers for SYS_USER_SESSION...
alter table SYS_USER_SESSION enable all triggers;
set feedback on
set define on
prompt Done.
