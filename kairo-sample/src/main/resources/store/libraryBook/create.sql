insert
into library.library_book (id, title, author, isbn)
values (:id, :title, :author, :isbn)
returning *
