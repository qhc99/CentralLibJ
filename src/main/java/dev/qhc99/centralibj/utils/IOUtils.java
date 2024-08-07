package dev.qhc99.centralibj.utils;

import dev.qhc99.centralibj.utils.misc.Ref;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static dev.qhc99.centralibj.utils.ExceptionUtils.CEStripper.raise;
import static dev.qhc99.centralibj.utils.LambdaUtils.stripCE;

public class IOUtils {

  public static  String pwd() {
    return new File(".").getAbsolutePath();
  }

  public static  String getPathDialog( PathDialogOption option) {
    return getPathDialog("打开", option);
  }

  public enum PathDialogOption {
    FILES,
    DIRECTORIES
  }

  /**
   * @param title title
   * @return absolute path
   */
  public static  String getPathDialog( String title,  PathDialogOption option) {
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    if (option == PathDialogOption.FILES) {
      jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
    else if (option == PathDialogOption.DIRECTORIES) {
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    else {
      throw new RuntimeException("unknown option");
    }
    jfc.setDialogTitle(title);
    jfc.setPreferredSize(new Dimension(640, 480));
    int ret = jfc.showOpenDialog(null);
    if (ret == JFileChooser.APPROVE_OPTION) {
      return jfc.getSelectedFile().toString();
    }
    else {
      throw new RuntimeException("not select a target");
    }
  }

  /**
   * @param path path
   * @return absolute path
   */
  public static  Stream<Path> listDir( Path path) {
    var f = path.toFile();
    if (f.exists()) {
      return stripCE(() -> Files.list(f.toPath()));
    }
    throw new RuntimeException(new NoSuchFileException(f.getAbsolutePath()));
  }

  public static void forFileLines( Path path,  Consumer<String> action) {
    try (var r = stripCE(() -> new BufferedReader(new FileReader(path.toFile())))) {
      var lineRef = new Ref<String>();
      var boolRef = Ref.of(true);
      while (boolRef.deRef) {
        stripCE(() -> {
          lineRef.deRef = r.readLine();
        });
        if (lineRef.deRef != null) {
          action.accept(lineRef.deRef);
        }
        else {
          boolRef.deRef = false;
        }
      }
    } catch (IOException e) {
      raise(e);
    }
  }

  public static void writeFile( String path,  Consumer<BufferedWriter> action) {
    writeFile(path, action, false);
  }

  public static void writeFile( String path,  Consumer<BufferedWriter> action, boolean append) {
    try (var w = stripCE(() -> new BufferedWriter(new FileWriter(path, StandardCharsets.UTF_8, append)))) {
      action.accept(w);
    } catch (IOException e) {
      raise(e);
    }

  }

  public static  File createOrOpenFile( Path path) {
    var f = path.toFile();
    if (!f.exists()) {
      var ret = stripCE(f::createNewFile);
      if (!ret) {
        throw new RuntimeException("cannot create file");
      }
    }
    return f;

  }

  public static void print(Object... objects) {
    print(true, objects);
  }

  public static void print(boolean end, Object... objects) {
    for (var s : objects) {
      System.out.print(s);
      System.out.print(" ");
    }
    if (end) {
      System.out.println();
    }
  }

  public static void renameFile( Path file_path,  Path new_path) {
    if (Files.exists(file_path)) {
      if (!Files.exists(new_path)) {
        stripCE(() -> Files.move(file_path, new_path));
      }
      else {
        throw new RuntimeException("new file name already exist in the same place");
      }
    }
    else {
      raise(new NoSuchFileException(file_path.toAbsolutePath().toString()));
    }
  }

  public static void coverMoveFile( Path origin_path,  Path new_path) {
    if (Files.exists(origin_path)) {
      if (Files.exists(new_path)) {
        removeFile(new_path);
      }
      stripCE(() -> Files.move(origin_path, new_path));
    }
    else {
      raise(new NoSuchFileException(origin_path.toAbsolutePath().toString()));
    }
  }

  public static void removeFile( Path path) {
    if (Files.exists(path)) {
      stripCE(() -> Files.delete(path));
    }
    else {
      raise(new NoSuchFileException(path.toAbsolutePath().toString()));
    }
  }
}
