update library.library_book
set deleted_at = now()
where id = :id
  and deleted_at is null
returning *
