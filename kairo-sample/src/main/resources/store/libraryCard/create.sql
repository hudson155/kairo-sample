insert
into library.library_card (id, library_member_id)
values (:id, :libraryMemberId)
returning *
