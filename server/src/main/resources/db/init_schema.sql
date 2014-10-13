create table if not exists devices (
  id integer primary key,
  url text,
  device_name text,
  tv_name text,
  last_heartbeat timestamp
);