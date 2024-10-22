create table library.library_card
(
  id text
    constraint pk__library_card
      primary key,
  version bigint default 0,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  deleted_at timestamptz default null
);

create trigger bu__library_card
  before update
  on library.library_card
  for each row
execute procedure updated();

alter table library.library_card
  add column library_member_id text not null;

create index ix__library_card__library_member_id
  on library.library_card (library_member_id);
