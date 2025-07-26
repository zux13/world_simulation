package dev.zux13.command.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class MenuScreen {

    private final String title;
    private final String icon;
    private final List<MenuItem> items;
    private final MenuItem exitItem;
    private final String infoText;

    public MenuScreen(String title, String icon, List<MenuItem> items, MenuItem exitItem) {
        this(title, icon, items, exitItem, null);
    }

    public MenuScreen(String title, String icon, MenuItem exitItem, String infoText) {
        this(title, icon, new ArrayList<>(), exitItem, infoText);
    }

}