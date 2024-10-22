update library.library_member
set email_address = lower(:emailAddress),
    first_name = :firstName,
    last_name = :lastName
where id = :id
  and deleted_at is null
returning *
