package ru.sergeykz.libmuffet.files;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import ru.sergeykz.ash.utils.F;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
public class TFile extends File  {
    File subroot;
    String subpath;
    String mimetype;
    String extension;

    public TFile (File file) {
        this(null, file);
    }
    public TFile(File subroot, File file) {
        super(file.getPath());
        this.subroot = subroot;
        try {
            this.mimetype = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.extension = FilenameUtils.getExtension(this.getName());
        this.subpath = F.clearSubpath(file, subroot);
    }
}
