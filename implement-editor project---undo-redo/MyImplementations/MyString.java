package MyImplementations;

// Implementing the String class
public class MyString {
  private char[] chars;

  // Constructor from a char array
  public MyString(char[] chars) {
    this.chars = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      this.chars[i] = chars[i];
    }
  }

  // constructor from a String
  public MyString(String s) {
    this(s.toCharArray());
  }

  // constructor from a MyString
  public MyString(MyString s) {
    this(s.toCharArray());
  }

  // toCharArray
  public char[] toCharArray() {
    char[] result = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      result[i] = chars[i];
    }
    return result;
  }

  // toString
  public String toString() {
    return new String(chars);
  }

  // Returns the length of the string
  public int length() {
    return chars.length;
  }

  // Returns the character at the specified index
  public char charAt(int index) {
    return chars[index];
  }

  // Returns a new MyString that is a substring of this MyString
  public MyString substring(int begin, int end) {
    int len = end - begin;
    char[] newChars = new char[len];
    for (int i = 0; i < len; i++) {
      newChars[i] = chars[begin + i];
    }
    return new MyString(newChars);
  }

  // Returns the MyString that is the concatenation of this MyString and the
  // specified MyString
  public MyString concat(MyString s) {
    char[] newChars = new char[this.length() + s.length()];
    System.arraycopy(this.chars, 0, newChars, 0, this.length());
    System.arraycopy(s.chars, 0, newChars, this.length(), s.length());
    return new MyString(newChars);
  }

  // Returns the MyString that is the concatenation of this MyString and the String
  public MyString concat(String s) {
    return concat(new MyString(s));
  }

  // indexOf
  public int indexOf(MyString s) {
    return indexOf(s, 0);
  }

  // indexOf
  public int indexOf(MyString s, int fromIndex) {
    int max = this.length() - s.length();
    for (int i = fromIndex; i <= max; i++) {
      int j;
      for (j = 0; j < s.length(); j++) {
        if (this.chars[i + j] != s.chars[j]) {
          break;
        }
      }
      if (j == s.length()) {
        return i;
      }
    }
    return -1;
  }

  // replace oldString with newString
  public MyString replace(MyString oldString, MyString newString) {
    int index = indexOf(oldString);
    if (index == -1) {
      return this;
    } else {
      MyString prefix = substring(0, index);
      MyString suffix = substring(index + oldString.length(), length());
      return prefix.concat(newString).concat(suffix);
    }
  }

  public MyString replace(String oldString, String newString) {
    return replace(new MyString(oldString), new MyString(newString));
  }

  public MyString replace(char oldChar, char newChar) {
    char[] newChars = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      newChars[i] = (chars[i] == oldChar) ? newChar : chars[i];
    }
    return new MyString(newChars);
  }

  // count
  public int count(MyString s) {
    int count = 0;
    int index = 0;
    while ((index = indexOf(s, index)) != -1) {
      count++;
      index += s.length();
    }
    return count;
  }

  // count
  public int count(String s) {
    return count(new MyString(s));
  }

  // count
  public int count(char c) {
    int count = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == c) {
        count++;
      }
    }
    return count;
  }

  // split
  public MyString[] split(MyString regex) {
    MyArrayList<MyString> list = new MyArrayList<>();
    int start = 0;
    int index;
    while ((index = indexOf(regex, start)) != -1) {
      list.add(substring(start, index));
      start = index + regex.length();
    }
    list.add(substring(start, length()));
    MyString[] result = new MyString[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  // split with String
  public MyString[] split(String regex) {
    return split(new MyString(regex));
  }

  // trim
  public MyString trim() {
    int start = 0;
    int end = length() - 1;
    while (start <= end && Character.isWhitespace(chars[start])) {
      start++;
    }
    while (end >= start && Character.isWhitespace(chars[end])) {
      end--;
    }
    return substring(start, end + 1);
  }

  // toLowerCase
  public MyString toLowerCase() {
    char[] newChars = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      newChars[i] = Character.toLowerCase(chars[i]);
    }
    return new MyString(newChars);
  }

  // toUpperCase
  public MyString toUpperCase() {
    char[] newChars = new char[chars.length];
    for (int i = 0; i < chars.length; i++) {
      newChars[i] = Character.toUpperCase(chars[i]);
    }
    return new MyString(newChars);
  }

  // equalsIgnoreCase
  public boolean equalsIgnoreCase(MyString s) {
    if (this.length() != s.length()) {
      return false;
    }
    for (int i = 0; i < chars.length; i++) {
      if (Character.toLowerCase(this.chars[i]) != Character.toLowerCase(s.chars[i])) {
        return false;
      }
    }
    return true;
  }

  // compareTo
  public int compareTo(MyString s) {
    int len1 = this.length();
    int len2 = s.length();
    int lim = Math.min(len1, len2);
    for (int i = 0; i < lim; i++) {
      char c1 = this.chars[i];
      char c2 = s.chars[i];
      if (c1 != c2) {
        return c1 - c2;
      }
    }
    return len1 - len2;
  }

  // compareToIgnoreCase
  public int compareToIgnoreCase(MyString s) {
    int len1 = this.length();
    int len2 = s.length();
    int lim = Math.min(len1, len2);
    for (int i = 0; i < lim; i++) {
      char c1 = Character.toLowerCase(this.chars[i]);
      char c2 = Character.toLowerCase(s.chars[i]);
      if (c1 != c2) {
        return c1 - c2;
      }
    }
    return len1 - len2;
  }

  // startsWith
  public boolean startsWith(MyString s) {
    if (s.length() > this.length()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      if (this.chars[i] != s.chars[i]) {
        return false;
      }
    }
    return true;
  }

  // endsWith
  public boolean endsWith(MyString s) {
    int offset = this.length() - s.length();
    if (offset < 0) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      if (this.chars[offset + i] != s.chars[i]) {
        return false;
      }
    }
    return true;
  }

  // contains
  public boolean contains(MyString s) {
    return indexOf(s) != -1;
  }

  // valueOf
  public static MyString valueOf(Number i) {
    return new MyString(i.toString());
  }

  // equals
  public boolean equals(Object o) {
    if (o instanceof MyString) {
      return this.equals((MyString) o);
    } else if (o instanceof String) {
      return this.equals((String) o);
    } else {
      return false;
    }
  }

  // equals for String
  public boolean equals(String s) {
    if (s.length() != this.length()) {
      return false;
    }
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] != s.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  // equals MyString
  public boolean equals(MyString s) {
    if (s.length() != this.length()) {
      return false;
    }
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] != s.chars[i]) {
        return false;
      }
    }
    return true;
  }

  // hashCode
  public int hashCode() {
    int h = 0;
    for (int i = 0; i < length(); i++) {
      h = 31 * h + charAt(i);
    }
    return h;
  }

  // startsWith String
  public boolean startsWith(String s) {
    return startsWith(new MyString(s));
  }

  // endsWith String
  public boolean endsWith(String s) {
    return endsWith(new MyString(s));
  }

  // contains String
  public boolean contains(String s) {
    return contains(new MyString(s));
  }

  // indexOf String
  public int indexOf(String s) {
    return indexOf(new MyString(s));
  }

  // indexOf String from index
  public int indexOf(String s, int fromIndex) {
    return indexOf(new MyString(s), fromIndex);
  }

  // indexOf char
  public int indexOf(char ch) {
    return indexOf(ch, 0);
  }

  // indexOf char from index
  public int indexOf(char ch, int fromIndex) {
    for (int i = fromIndex; i < chars.length; i++) {
      if (chars[i] == ch) {
        return i;
      }
    }
    return -1;
  }
}
