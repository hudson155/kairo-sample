create table if not exists chat.conversation
(
  id text
    primary key,
  created_at timestamp default current_timestamp not null,
  version bigint default 0 not null,
  updated_at timestamp default current_timestamp not null,
  user_id text not null,
  agent_name text not null,
  processing boolean not null
);

create trigger on_update__conversation
  before update
  on chat.conversation
  for each row
execute procedure updated();

create index ix__conversation__user_id
  on chat.conversation (user_id);
