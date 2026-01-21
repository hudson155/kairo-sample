create table if not exists identity.user_account
(
  id text
    primary key,
  created_at timestamp default current_timestamp not null,
  version bigint default 0 not null,
  updated_at timestamp default current_timestamp not null,
  first_name text null,
  last_name text null,
  email_address text not null
);

create trigger on_update__user_account
  before update
  on identity.user_account
  for each row
execute procedure updated();
