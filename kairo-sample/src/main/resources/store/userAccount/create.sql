insert
into users.user_account (id, email_address, first_name, last_name)
values (:id, lower(:emailAddress), :firstName, :lastName)
returning *
