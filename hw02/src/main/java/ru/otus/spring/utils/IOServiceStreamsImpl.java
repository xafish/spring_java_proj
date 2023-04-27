package ru.otus.spring.utils;

import lombok.Getter;

import java.io.InputStream;
import java.io.PrintStream;

public record IOServiceStreamsImpl(PrintStream out, InputStream in) implements IOServiceStreams {
}
