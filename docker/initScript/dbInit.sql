--create master schema
create schema if not exists master;
grant all privileges on all tables in schema master to shopi;

create extension if not exists "uuid-ossp";