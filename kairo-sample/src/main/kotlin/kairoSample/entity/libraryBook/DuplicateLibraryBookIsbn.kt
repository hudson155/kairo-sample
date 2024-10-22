package kairoSample.entity.libraryBook

import kairo.exception.ConflictException

public class DuplicateLibraryBookIsbn : ConflictException("ISBN already exists.")
