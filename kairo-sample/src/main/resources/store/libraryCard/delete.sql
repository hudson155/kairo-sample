update library.library_card
set deleted_at = now()
where id = :id
  and deleted_at is null
returning *
