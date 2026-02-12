package io.realmit.interfass.menu.dto;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;

public class ColoredTextPart {
    ArrayList<String> names;
    ArrayList<NamedTextColor> namesColors;
    ArrayList<String> lores;
    ArrayList<NamedTextColor> loresColors;

    public ColoredTextPart(
        ArrayList<String> names,
        ArrayList<NamedTextColor> namesColors,
        ArrayList<String> lores,
        ArrayList<NamedTextColor> loresColors
    ) {
        this.names = names;
        this.namesColors = namesColors;
        this.lores = lores;
        this.loresColors = loresColors;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public ArrayList<NamedTextColor> getNamesColors() {
        return namesColors;
    }

    public void setNamesColors(ArrayList<NamedTextColor> namesColors) {
        this.namesColors = namesColors;
    }

    public ArrayList<String> getLores() {
        return lores;
    }

    public void setLores(ArrayList<String> lores) {
        this.lores = lores;
    }

    public ArrayList<NamedTextColor> getLoresColors() {
        return loresColors;
    }

    public void setLoresColors(ArrayList<NamedTextColor> loresColors) {
        this.loresColors = loresColors;
    }
}