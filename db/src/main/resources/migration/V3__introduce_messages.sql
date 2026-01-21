create table if not exists chat.message
(
  id text
    primary key,
  created_at timestamp default current_timestamp not null,
  version bigint default 0 not null,
  updated_at timestamp default current_timestamp not null,
  user_id text not null,
  conversation_id text not null,
  author jsonb not null,
  raw jsonb not null,
  elements jsonb not null,
  constraint fk__message__conversation_id
    foreign key (conversation_id)
      references chat.conversation (id)
      on delete cascade
      on update restrict
);

create trigger on_update__message
  before update
  on chat.message
  for each row
execute procedure updated();

create index ix__message__user_id
  on chat.message (user_id);

create index ix__message__conversation_id
  on chat.message (conversation_id);
