package ru.sergeykz.libmuffet;

import ru.sergeykz.libmuffet.files.TFile;

public abstract class ScanFilter {
    public abstract boolean isTarget(TFile file);
}
