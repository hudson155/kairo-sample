create table library.library_member
(
  id text
    constraint pk__library_member
      primary key,
  version bigint default 0,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  deleted_at timestamptz default null
);

create trigger bu__library_member
  before update
  on library.library_member
  for each row
execute procedure updated();

alter table library.library_member
  add column email_address text not null,
  add constraint ck__library_member__email_address
    check (lower(email_address) = email_address),
  add constraint uq__library_member__email_address
    unique (email_address);

alter table library.library_member
  add column first_name text;

alter table library.library_member
  add column last_name text;
