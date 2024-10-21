package kairoSample.exception

import kairo.exception.ForbiddenException

public class NotSuperuser : ForbiddenException(
  message = "You are not a superuser.",
)
