select *
from library.library_book
where (:title is null or (strpos(lower(title), lower(:title)) > 0))
  and (:author is null or (strpos(lower(author), lower(:author)) > 0))
  and deleted_at is null
