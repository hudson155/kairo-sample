package kairoSample.entity.libraryMember

import kairo.exception.ConflictException

public class DuplicateLibraryMemberEmailAddress : ConflictException("Email address already taken.")
