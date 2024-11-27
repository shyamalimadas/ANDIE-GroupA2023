package cosc202.andie;

import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 *
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 *
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class EditableImage {

  /** The original image. This should never be altered by ANDIE. */
  private BufferedImage original;
  /**
   * The current image, the result of applying {@link ops} to {@link original}.
   */
  private BufferedImage current;

  /** The sequence of operations currently applied to the image. */
  private Stack<ImageOperation> ops;
  /** A memory of 'undone' operations to support 'redo'. */
  private Stack<ImageOperation> redoOps;
  /** The file where the original image is stored/ */
  private String imageFilename;
  /** The file where the operation sequence is stored. */
  private String opsFilename;
  /** The sequence of operations currently applied to the image.
   * Specfically for the macro operations
   */
  public static Stack<ImageOperation> recordedActions;
  /** boolean value to dictate when the macro is recording and when it
   * is not recording for the start and stop settings
   */
  public static boolean isRecording = false;

  /**
   * <p>
   * Create a new EditableImage.
   * </p>
   *
   * <p>
   * A new EditableImage has no image (it is a null reference), and an empty stack
   * of operations.
   * </p>
   */
  public EditableImage() {
    original = null;
    current = null;
    ops = new Stack<ImageOperation>();
    redoOps = new Stack<ImageOperation>();
    recordedActions = new Stack<ImageOperation>();
    imageFilename = null;
    opsFilename = null;
  }

  /**
   * <p>
   * Check if there is an image loaded.
   * </p>
   *
   * @return True if there is an image, false otherwise.
   */
  public boolean hasImage() {
    return current != null;
  }

  /**
   * <p>
   * Make a 'deep' copy of a BufferedImage.
   * </p>
   *
   * <p>
   * Object instances in Java are accessed via references, which means that
   * assignment does
   * not copy an object, it merely makes another reference to the original.
   * In order to make an independent copy, the {@code clone()} method is generally
   * used.
   * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
   * the
   * {@code clone()} method is not accessible.
   * </p>
   *
   * <p>
   * This method makes a cloned copy of a BufferedImage.
   * This requires knoweldge of some details about the internals of the
   * BufferedImage,
   * but essentially comes down to making a new BufferedImage made up of copies of
   * the internal parts of the input.
   * </p>
   *
   * <p>
   * This code is taken from StackOverflow:
   * <a href=
   * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
   * in response to
   * <a href=
   * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
   * Code by Klark used under the CC BY-SA 2.5 license.
   * </p>
   *
   * <p>
   * This method (only) is released under
   * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
   * </p>
   *
   * @param bi The BufferedImage to copy.
   * @return A deep copy of the input.
   */
  private static BufferedImage deepCopy(BufferedImage bi) {
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }

  /**
   * <p>
   * Open an image from a file.
   * </p>
   *
   * <p>
   * Opens an image from the specified file.
   * Also tries to open a set of operations from the file with <code>.ops</code>
   * added.
   * So if you open <code>some/path/to/image.png</code>, this method will also try
   * to
   * read the operations from <code>some/path/to/image.png.ops</code>.
   * </p>
   *
   * @param filePath The file to open the image from.
   * @throws Exception If something goes wrong.
   */
  public void open(String filePath) throws Exception {
    imageFilename = filePath;
    opsFilename = imageFilename + ".ops";
    File imageFile = new File(imageFilename);
    original = ImageIO.read(imageFile);
    current = deepCopy(original);

    try {
      ops.clear();
      FileInputStream fileIn = new FileInputStream(this.opsFilename);
      ObjectInputStream objIn = new ObjectInputStream(fileIn);

      // Silence the Java compiler warning about type casting.
      // Understanding the cause of the warning is way beyond
      // the scope of COSC202, but if you're interested, it has
      // to do with "type erasure" in Java: the compiler cannot
      // produce code that fails at this point in all cases in
      // which there is actually a type mismatch for one of the
      // elements within the Stack, i.e., a non-ImageOperation.
      @SuppressWarnings("unchecked")
      Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
      ops = opsFromFile;
      redoOps.clear();
      objIn.close();
      fileIn.close();
    } catch (Exception ex) {
      // Could be no file or something else. Carry on for now.
    }
    this.refresh();
  }

  public void saveMacro(String filename) {
    try {
      imageFilename = filename;
      opsFilename = imageFilename + ".ops";

      FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
      ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
      objOut.writeObject(this.recordedActions);
      objOut.close();
      fileOut.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void loadMacro(String filePath) throws Exception {
    imageFilename = filePath;
    opsFilename = imageFilename;

    FileInputStream fileIn = new FileInputStream(this.opsFilename);
    ObjectInputStream objIn = new ObjectInputStream(fileIn);

    @SuppressWarnings("unchecked")
    Stack<ImageOperation> macro = (Stack<ImageOperation>) objIn.readObject();

    // for (ImageOperation op : macro) {
    // op.apply(current);
    // }

    while (!macro.isEmpty()) {
      apply(macro.pop());
    }

    objIn.close();
    fileIn.close();

    this.refresh();
  }

  /**
   * <p>
   * Save an image to file.
   * </p>
   *
   * <p>
   * Saves an image to the file it was opened from, or the most recent file saved
   * as.
   * Also saves a set of operations from the file with <code>.ops</code> added.
   * So if you save to <code>some/path/to/image.png</code>, this method will also
   * save
   * the current operations to <code>some/path/to/image.png.ops</code>.
   * </p>
   *
   * @throws Exception If something goes wrong.
   */
  public void save() throws Exception {
    if (this.opsFilename == null) {
      this.opsFilename = this.imageFilename + ".ops";
    }
    // Write image file based on file extension
    String extension = imageFilename
      .substring(1 + imageFilename.lastIndexOf("."))
      .toLowerCase();
    ImageIO.write(current, extension, new File(imageFilename)); // Changed original to current
    // Write operations file
    FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
    objOut.writeObject(this.ops);
    objOut.close();
    fileOut.close();
  }

  /**
   * Save an image to a speficied file.
   *
   * @param imageFilename The file location to save the image to.
   * @throws Exception If something goes wrong.
   */
  public void saveAs(String imageFilename) throws Exception {
    this.imageFilename = imageFilename;
    this.opsFilename = imageFilename + ".ops";
    save();
  }

  /**
   * <p>
   * Apply an {@link ImageOperation} to this image.
   * </p>
   *
   * @param op The operation to apply.
   */
  public void apply(ImageOperation op) {
    current = op.apply(current);
    ops.add(op);
    // System.out.println(recordedActions.toString());
    if (isRecording) {
      // recordedActions.push(op);
      recordedActions.add(op);
      System.out.println(recordedActions.toString());
    }
  }

  /**
   * <p>
   * Undo the last {@link ImageOperation} applied to the image.
   * </p>
   */
  public void undo() {
    if (!ops.isEmpty()) {
      redoOps.push(ops.pop());
      refresh();
    } else {
      try {
        Andie.createErrorMessage(Andie.bundle.getString("undo_error"));
      } catch (Exception ex) {
        ex.printStackTrace();
        System.exit(1);
      }
    }
  }

  // /**
  // * <p>
  // * Reapply the most recently {@link undo}ne {@link ImageOperation} to the
  // image.
  // * </p>
  // */
  // public void redo() {
  // apply(redoOps.pop());
  // }
  public void redo() {
    if (!redoOps.isEmpty() || !ops.isEmpty()) {
      if (!redoOps.isEmpty()) {
        apply(redoOps.pop());
      } else {
        try {
          Andie.createErrorMessage(Andie.bundle.getString("redo_error"));
        } catch (Exception ex) {
          ex.printStackTrace();
          System.exit(1);
        }
      }
    } else {
      try {
        Andie.createErrorMessage(Andie.bundle.getString("redo_error"));
      } catch (Exception ex) {
        ex.printStackTrace();
        System.exit(1);
      }
    }
  }

  /**
   * <p>
   * Get the current image after the operations have been applied.
   * </p>
   *
   * @return The result of applying all of the current operations to the
   *         {@link original} image.
   */
  public BufferedImage getCurrentImage() {
    return current;
  }

  /**
   * <p>
   * Reapply the current list of operations to the original.
   * </p>
   *
   * <p>
   * While the latest version of the image is stored in {@link current}, this
   * method makes a fresh copy of the original and applies the operations to it in
   * sequence.
   * This is useful when undoing changes to the image, or in any other case where
   * {@link current}
   * cannot be easily incrementally updated.
   * </p>
   */
  private void refresh() {
    current = deepCopy(original);
    for (ImageOperation op : ops) {
      current = op.apply(current);
    }
  }

  /**
   * Exports the image to the specified filepath with the specified format.
   * If no file format is specified by the user, the image is saved as a PNG file
   * by default.
   * If the user specifies another format like JPG or BMP, the image is saved in
   * that format.
   *
   * @param filepath The filepath where the image should be exported.
   * @throws IOException If an error occurs while exporting the image.
   */
  public void export(String filepath, String extension) throws IOException {
    if (filepath == null || "".equals(filepath)) {
      throw new IllegalArgumentException(
        Andie.bundle.getString("filepath_error")
      );
    }

    // Check if the extension is valid
    if (!ImageIO.getImageWritersBySuffix(extension).hasNext()) {
      throw new IllegalArgumentException(
        Andie.bundle.getString("format_error")
      );
    }

    // Write the image to the specified filepath with the specified format
    File outputFile = new File(filepath);
    ImageIO.write(current, extension, outputFile);
  }

  public void setImage(BufferedImage newImage) {
    original = deepCopy(newImage);
    current = deepCopy(newImage);
    ops.clear();
    redoOps.clear();
  }
}
