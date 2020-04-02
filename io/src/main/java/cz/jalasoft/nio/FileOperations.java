package cz.jalasoft.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Jan Lastovicka
 * @since 2019-02-21
 */
public class FileOperations {

    public static void main(String[] args) throws IOException {
        Path src = FileSystems.getDefault().getPath(System.getProperty("user.home"), "linky");
        Path tgt = Paths.get(System.getProperty("user.home"), "linky2");

        //Files.deleteIfExists(tgt);
        //Files.createFile(tgt);

        copyFile(src, tgt);
    }

    private static void copyFile(Path src, Path target) throws IOException {

        try (FileChannel srcChannel = FileChannel.open(src);
             FileChannel tgtChannel = FileChannel.open(target, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            long size = srcChannel.size();
            srcChannel.transferTo(0, size, tgtChannel);
        }
    }

    private static void printFile() throws IOException {
        File file = FileSystems.getDefault().getPath(System.getProperty("user.home"), "linky").toFile();
        try(FileChannel channel = new FileInputStream(file).getChannel()) {

            ByteBuffer buf = ByteBuffer.allocate(20);
            int length;
            while((length = channel.read(buf)) > 0) {
                buf.flip();

                byte[] bytes = new byte[length];
                buf.get(bytes, 0, length);

                System.out.println(new String(bytes));
                buf.clear();
            }
        }
    }
}
