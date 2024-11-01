package kairoSample.exception

import kairo.exception.BadRequestException

public class InvalidQueryCombination(
  message: String,
) : BadRequestException(
  message = "Invalid query combination: $message",
)
