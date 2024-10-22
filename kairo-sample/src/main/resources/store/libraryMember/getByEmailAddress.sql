select *
from library.library_member
where email_address = lower(:emailAddress)
  and deleted_at is null
