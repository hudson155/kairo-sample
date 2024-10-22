update library.library_book
set title = :title,
    author = :author
where id = :id
  and deleted_at is null
returning *
