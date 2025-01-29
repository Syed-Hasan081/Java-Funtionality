package Editor;

public class TestEditor {
  public static void main(String[] args) {
    String[] AntiHeros = {
      "Samuel L. Jackson and John Travolta in Pulp Fiction (1994)",
      "Brad Pitt in Fight Club (1999)",
      "Robert De Niro in Taxi Driver (1976)"
    };

    String[] Jokers = {
      "Heath Ledger in The Dark Knight (2008)",
      "Joaquin Phoenix in Joker (2019)",
      "Jack Nicholson in Batman (1989)"
    };

    // Open a file AntiHeros.txt
    Editor editor = new Editor("AntiHeros.txt");
    // Save as Jokers.txt.  current file should be Jokers.txt
    editor.saveAs("Jokers.txt");
    // insert row 0 col 0
    editor.insert("Joker");
    // assert that row 0 col 0 is Joker
    assert(editor.getText(0).equals("JokerAnti Hero Actors ranked"));
    editor.delete(9);
    assert (editor.getText(0).equals("Joker Actors ranked"));

    // find "Samuel"
    editor.find("Samuel");
    int[] rowcol = editor.getCursor();
    assert(rowcol[0] == 1 && rowcol[1] == 3);
    // assert that row 1 col 3 is Samuel
    assert(editor.getText(rowcol[0], rowcol[1], 6).equals("Samuel"));

    // replace AntiHeros[0] with Jokers[0]
    editor.moveCursor(1, 3);
    editor.replace(AntiHeros[0].length(), Jokers[0]);
    assert (editor.getText(1, 3).equals(Jokers[0]));

    editor.moveCursor(2, 3);
    editor.replace(AntiHeros[1].length(), Jokers[1]);
    assert (editor.getText(2, 3).equals(Jokers[1]));

    editor.moveCursor(3, 3);
    editor.replace(AntiHeros[2].length(), Jokers[2]);
    assert (editor.getText(3, 3).equals(Jokers[2]));
    
    editor.save();

    // Saveas AntiHerosViaUndo.txt
    editor.saveAs("AntiHerosViaUndo.txt");
    // undo
    editor.undo();
    assert(editor.getText(3, 3).equals(AntiHeros[2]));
    editor.undo();
    assert(editor.getText(2, 3).equals(AntiHeros[1]));
    editor.undo();
    assert(editor.getText(1, 3).equals(AntiHeros[0]));

    editor.undo();  // undo the delete(9)
    assert (editor.getText(0).equals("JokerAnti Hero Actors ranked"));
    editor.undo();  // undo the insert
    assert (editor.getText(0).equals("Anti Hero Actors ranked"));
    editor.save();

    // Saveas JokersViaUndo.txt
    editor.saveAs("JokersViaRedo.txt");

    // redo insert "Joker"
    editor.redo();
    assert (editor.getText(0).equals("JokerAnti Hero Actors ranked"));
    // redo delete(9)
    editor.redo();
    assert (editor.getText(0).equals("Joker Actors ranked"));
    // redo replace AntiHeros[0] with Jokers[0]
    editor.redo();
    assert (editor.getText(1, 3).equals(Jokers[0]));
    // redo replace AntiHeros[1] with Jokers[1]
    editor.redo();
    assert (editor.getText(2, 3).equals(Jokers[1]));
    // redo replace AntiHeros[2] with Jokers[2]
    editor.redo();
    assert (editor.getText(3, 3).equals(Jokers[2]));

    editor.save();
    // // TODO
    // // Manually Check to see if the files AntiHeros.txt and AntiHerosViaUndo.txt are the same
    // // Manually Check to see if the files Jokers.txt and JokersViaRedo.txt are the same
  }
}
