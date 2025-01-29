package Editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
importS java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import MyImplementations.MyArrayList;
import MyImplementations.MyStack;
import MyImplementations.MyString;

public class Editor {
  /** cursor row position */
  int row;
  /** cursor column position */
  int col;
  /** the text of the editor */
  MyArrayList<MyString> text;
  /** the undo stack */
  MyStack<EditorAction> undoStack;
  /** the redo stack */
  MyStack<EditorAction> redoStack;

  // Current file name
  MyString fileName;

  class EditorAction {
    /** the row of the action */
    int row;
    /** the column of the action */
    int col;
    /** the text of the action */
    MyString text;
    /** the length of the action */
    int length;
    /** the action type */
    ActionType type;
  }

  enum ActionType {
    /** insert action */
    INSERT,
    /** delete action */
    DELETE,
    /** replace action */
    REPLACE
  }

  /**
   * Constructor
   */
  public Editor(String myFilename) {
    this(new MyString(myFilename));
  }

  public Editor(MyString fileName) {
    this.fileName = fileName;
    undoStack = new MyStack<EditorAction>();
    redoStack = new MyStack<EditorAction>();
    open(fileName);
  }

  /**
   * Open the file with the given name and read the contents into the editor.
   */
  void open(String fileName) {
    open(new MyString(fileName));
  }

  void open(MyString fileName) {
    // Read the file into the text
    text = new MyArrayList<MyString>();

    // Read the file line by line
    Path path = Paths.get(fileName.toString());
    Charset charset = Charset.forName("UTF-8");
    try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        // Add the line to text
        text.add(new MyString(line));
      }
    } catch (IOException e) {
      System.err.println(e);
    }

    // Set cursor to beginning
    row = 0;
    col = 0;

    // Set the file name
    this.fileName = fileName;

    // Clear undo and redo stacks
    undoStack.clear();
    redoStack.clear();
  }

  /**
   * Save the current text to currently open file.
   */
  void save() {
    saveAs(fileName);
  }

  /**
   * Save the current text to the given file.
   */
  void saveAs(String fileName) {
    saveAs(new MyString(fileName));
  }

  void saveAs(MyString fileName) {
    // Write the text to the file
    Path path = Paths.get(fileName.toString());
    Charset charset = Charset.forName("UTF-8");
    try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
      for (int i = 0; i < text.size(); i++) {
        writer.write(text.get(i).toString());
        writer.newLine();
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
   * Undo the last action.
   */
  void undo() {
    if (!undoStack.isEmpty()) {
      EditorAction action = undoStack.pop();
      redoStack.push(action);

      // Move cursor to the action's position
      moveCursor(action.row, action.col);

      switch (action.type) {
        case INSERT:
          // Undo insert: delete the inserted text
          delete(action.length, false);
          break;
        case DELETE:
          // Undo delete: insert the deleted text
          insert(action.text, false);
          break;
        case REPLACE:
          // Undo replace: replace the text with the original text
          replace(action.length, action.text, false);
          break;
      }
    }
  }

  /**
   * Redo the last undone action.
   */
  void redo() {
    if (!redoStack.isEmpty()) {
      EditorAction action = redoStack.pop();
      undoStack.push(action);

      // Move cursor to the action's position
      moveCursor(action.row, action.col);

      switch (action.type) {
        case INSERT:
          // Redo insert: insert the text
          insert(action.text, false);
          break;
        case DELETE:
          // Redo delete: delete the text
          delete(action.length, false);
          break;
        case REPLACE:
          // Redo replace: replace the text with the new text
          replace(action.length, action.text, false);
          break;
      }
    }
  }

  /**
   * Save the state of the EditorAction and push it onto the undo stack. used by insert and delete
   * @param type
   * @param s
   */
  void saveState(ActionType type, MyString s) {
    EditorAction action = new EditorAction();
    action.type = type;
    action.row = this.row;
    action.col = this.col;
    action.text = s;
    action.length = s.length();
    undoStack.push(action);

    // Clear the redo stack when new action is performed
    redoStack.clear();
  }

  /**
   * Save the state of the EditorAction and push it onto the undo stack. used by replace
   * @param type
   * @param s
   * @param length
   */
  void saveState(ActionType type, MyString s, int length) {
    EditorAction action = new EditorAction();
    action.type = type;
    action.row = this.row;
    action.col = this.col;
    action.text = s;
    action.length = length;
    undoStack.push(action);

    // Clear the redo stack when new action is performed
    redoStack.clear();
  }

  /**
   * Insert the given string at the current cursor position.
   * The cursor position is updated to point to the end of the inserted string.
   */
  void insert(String s) {
    insert(new MyString(s));
  }

  void insert(MyString s) {
    insert(s, true);
  }

  void insert(MyString s, boolean saveState) {
    if (saveState) {
      saveState(ActionType.INSERT, s);
    }

    // Insert s at the current cursor position
    MyString line = text.get(row);
    // Insert s into line at col
    MyString newLine = line.substring(0, col).concat(s).concat(line.substring(col, line.length()));
    // Update the line in text
    text.set(row, newLine);
    // Update the cursor position
    col += s.length();
  }

  void delete(int n) {
    delete(n, true);
  }

  /**
   * Delete n characters at the current cursor position.
   * The cursor position is updated to point to the end of the deleted string.
   */
  void delete(int n, boolean saveState) {
    // Delete n characters at the current cursor position
    // Save the deleted text
    MyString line = text.get(row);
    int endCol = Math.min(col + n, line.length());
    MyString deletedText = line.substring(col, endCol);

    if (saveState) {
      saveState(ActionType.DELETE, deletedText);
    }

    // Remove the text from line
    MyString newLine = line.substring(0, col).concat(line.substring(endCol, line.length()));
    // Update the line in text
    text.set(row, newLine);
    // Cursor position remains the same

    // Clear the redo stack when new action is performed
    if (saveState) {
      redoStack.clear();
    }
  }

  /**
   * Replace the character at the current cursor position with the given
   * character.
   * The cursor position is updated to point to the end of the deleted string.
   */
  void replace(int n, String s) {
    replace(n, new MyString(s));
  }

  void replace(int n, MyString s) {
    replace(n, s, true);
  }

  /**
   * Replace the n characters at the current cursor position with the given string.
   * The cursor position is updated to point to the end of the replaced string.
   */
  void replace(int n, MyString s, boolean saveState) {
    // Replace n characters at the current cursor position with s
    // Save the original text being replaced
    MyString line = text.get(row);
    int endCol = Math.min(col + n, line.length());
    MyString originalText = line.substring(col, endCol);

    if (saveState) {
      saveState(ActionType.REPLACE, originalText, s.length());
    }

    // Build the new line
    MyString newLine = line.substring(0, col).concat(s).concat(line.substring(endCol, line.length()));
    // Update the line in text
    text.set(row, newLine);
    // Update the cursor position
    col += s.length();

    // Clear the redo stack when new action is performed
    if (saveState) {
      redoStack.clear();
    }
  }

  /**
   * Find the first instance of given string in the editor and set the cursor to
   * that position.
   */
  int[] find(String s) {
    return find(new MyString(s));
  }

  int[] find(MyString s) {
    for (int i = 0; i < text.size(); i++) {
      MyString line = text.get(i);
      int index = line.indexOf(s);
      if (index != -1) {
        // Found
        row = i;
        col = index;
        return new int[] { row, col };
      }
    }
    // Not found
    return null;
  }

  /**
   * Move the cursor to the given position.
   */
  void moveCursor(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Return the current cursor position.
   */
  int[] getCursor() {
    return new int[] { row, col };
  }

  /**
   * Move the cursor to the given position.
   */
  void moveCursor(int[] rowcol) {
    this.row = rowcol[0];
    this.col = rowcol[1];
  }

  /** return the entire line in row */
  MyString getText(int row) {
    return text.get(row);
  }

  /** return the line in row from col to the end */
  MyString getText(int row, int col) {
    MyString line = text.get(row);
    return line.substring(col, line.length());
  }

  /** return the line from col1 to n character length */
  MyString getText(int row, int col, int n) {
    MyString line = text.get(row);
    int endCol = Math.min(col + n, line.length());
    return line.substring(col, endCol);
  }

} 
