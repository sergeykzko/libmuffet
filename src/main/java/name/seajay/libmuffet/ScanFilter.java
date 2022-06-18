package name.seajay.libmuffet;

import name.seajay.libmuffet.files.TFile;

public abstract class ScanFilter {
    public abstract boolean isTarget(TFile file);
}
