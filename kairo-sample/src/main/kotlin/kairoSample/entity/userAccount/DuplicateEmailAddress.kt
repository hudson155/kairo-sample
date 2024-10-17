package kairoSample.entity.userAccount

import kairo.exception.ConflictException

public class DuplicateEmailAddress : ConflictException("Email address already taken.")
