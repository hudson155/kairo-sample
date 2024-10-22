select *
from library.library_card
where library_member_id = :libraryMemberId
  and deleted_at is null
