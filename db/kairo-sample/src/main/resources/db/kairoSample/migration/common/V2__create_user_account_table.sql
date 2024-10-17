create table users.user_account
(
  id text
    constraint pk__user_account
      primary key,
  version bigint default 0,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  deleted_at timestamptz default null
);

create trigger bu__user_account
  before update
  on users.user_account
  for each row
execute procedure updated();

alter table users.user_account
  add column email_address text not null,
  add constraint ck__user_account__email_address
    check (lower(email_address) = email_address),
  add constraint uq__user_account__email_address
    unique (email_address);

alter table users.user_account
  add column first_name text;

alter table users.user_account
  add column last_name text;
