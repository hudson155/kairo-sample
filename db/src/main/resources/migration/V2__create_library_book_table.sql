create table if not exists library.library_book
(
  id text
    primary key,
  created_at timestamp default current_timestamp not null,
  version bigint default 0 not null,
  updated_at timestamp default current_timestamp not null,
  title text null,
  authors text[] not null,
  isbn text not null
);

alter table library.library_book
  add constraint uq__library_book__isbn
    unique (isbn);

create trigger on_update__library_book
  before update
  on library.library_book
  for each row
execute procedure updated();
