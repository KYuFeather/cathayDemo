drop table if exists currency;
create table currency (curr_chn varchar(255) not null, curr_eng varchar(3) not null, primary key (curr_eng));
alter table currency add constraint UK_5t93havl08w54jac70c5vw8xl unique (curr_chn);
