insert
into library.library_member (id, email_address, first_name, last_name)
values (:id, lower(:emailAddress), :firstName, :lastName)
returning *
