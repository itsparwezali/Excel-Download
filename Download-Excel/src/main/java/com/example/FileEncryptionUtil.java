package com.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

public class FileEncryptionUtil {
    public static void writeFileSystem(HttpServletResponse httpServletResponse, File file, String password) throws IOException, InvalidFormatException {
        try (OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE)) {
            POIFSFileSystem fileSystem = new POIFSFileSystem();
            opc.save(getEncryptingOutputStream(fileSystem, password));
            fileSystem.writeFilesystem(httpServletResponse.getOutputStream());
            fileSystem.close();
        }
    }

    private static OutputStream getEncryptingOutputStream(POIFSFileSystem fileSystem, String password) throws IOException {
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(password);
        try {
            return encryptor.getDataStream(fileSystem);
        } catch (GeneralSecurityException e) {
            // TODO handle this better
            throw new RuntimeException(e);
        }
    }


}
