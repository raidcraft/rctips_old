-- apply changes
create table rc_tips_player_tips (
  id                            integer auto_increment not null,
  player_id                     integer,
  template                      varchar(255),
  displayed                     datetime(6),
  constraint pk_rc_tips_player_tips primary key (id)
);

create table rc_tips_players (
  id                            integer auto_increment not null,
  uuid                          varchar(40),
  player                        varchar(255),
  enabled                       tinyint(1) default 0 not null,
  constraint pk_rc_tips_players primary key (id)
);

create index ix_rc_tips_player_tips_player_id on rc_tips_player_tips (player_id);
alter table rc_tips_player_tips add constraint fk_rc_tips_player_tips_player_id foreign key (player_id) references rc_tips_players (id) on delete restrict on update restrict;

