package dev.zux13.command.menu;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class SubMenuItem implements MenuItem {

    private final String label;
    private final MenuManager menuManager;
    private final Supplier<MenuScreen> subMenuSupplier;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void execute() {
        menuManager.push(subMenuSupplier.get());
    }
}
