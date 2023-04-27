package ru.otus.spring.utils;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOServiceStreams {
    PrintStream out();
    InputStream in();
}
