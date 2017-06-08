package org.davy.commons;

import java.io.File;
import java.io.IOException;

/**
 * Indicates that a file already exists.
 */
public class FileExistsException extends IOException {

    private static final long serialVersionUID = 1L;

    public FileExistsException() {
        super();
    }

    public FileExistsException(final String message) {
        super(message);
    }

    public FileExistsException(final File file) {
        super("File " + file + " exists");
    }

}
