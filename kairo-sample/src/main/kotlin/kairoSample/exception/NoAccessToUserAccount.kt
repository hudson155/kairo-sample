package kairoSample.exception

import kairo.exception.ForbiddenException

public class NoAccessToUserAccount : ForbiddenException(
  message = "You do not have access to this user account.",
)
