package com.github.wicketconsole;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.wicket.util.io.IObjectStreamFactory;

public final class ConsoleObjectStreamFactory implements IObjectStreamFactory {
    @Override
    public ObjectInputStream newObjectInputStream(final InputStream in)
            throws IOException {
        return new ObjectInputStream(in);
    }

    @Override
    public ObjectOutputStream newObjectOutputStream(final OutputStream out)
            throws IOException {
        return new ObjectOutputStream(out);
    }
}