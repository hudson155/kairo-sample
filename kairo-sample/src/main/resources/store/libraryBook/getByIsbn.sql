select *
from library.library_book
where isbn = :isbn
  and deleted_at is null
