package kairoSample.entity.userAccount

import kairo.exception.NotFoundException

public class UserAccountNotFound : NotFoundException("The user account does not exist.")
