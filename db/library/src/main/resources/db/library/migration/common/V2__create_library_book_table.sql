create table library.library_book
(
  id text
    constraint pk__library_book
      primary key,
  version bigint default 0,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),
  deleted_at timestamptz default null
);

create trigger bu__library_book
  before update
  on library.library_book
  for each row
execute procedure updated();

alter table library.library_book
  add column title text not null;

create index ix__library_book__title
  on library.library_book (title);

alter table library.library_book
  add column author text;

create index ix__library_book__author
  on library.library_book (author);

alter table library.library_book
  add column isbn text not null,
  add constraint uq__library_book__isbn
    unique (isbn);
