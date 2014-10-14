CREATE TABLE IF NOT EXISTS devices (
  id INTEGER PRIMARY KEY,
  url TEXT,
  device_name TEXT,
  tv_name TEXT,
  last_heartbeat timestamp
);