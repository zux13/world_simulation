package dev.zux13.logger;

import java.util.List;

public interface LogProvider {
    List<String> getLogSnapshot();
}