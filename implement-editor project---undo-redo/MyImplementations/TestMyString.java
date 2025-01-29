// Test cases for MyString class
//
package MyImplementations;


public class TestMyString {
  public static void main(String[] args) {
    MyString s1 = new MyString("Welcome to Java");
    MyString s2 = new MyString("Programming is fun");
    MyString s3 = new MyString("Welcome to Java");

    System.out.println("s1 is " + s1);
    System.out.println("s2 is " + s2);
    System.out.println("s3 is " + s3);
    System.out.println("s1 length is " + s1.length());
    System.out.println("s2 length is " + s2.length());
    System.out.println("s1 charAt 0 is " + s1.charAt(0));
    System.out.println("s2 charAt 0 is " + s2.charAt(0));
    System.out.println("s1 substring(0, 5) is " + s1.substring(0, 5));
    System.out.println("s2 substring(0, 5) is " + s2.substring(0, 5));
    // test concat
    System.out.println("s1 concat s2 is " + s1.concat(s2));
    // test indexOf
    System.out.println("s1 indexOf 'o' is " + s1.indexOf('o'));
    System.out.println("s1 indexOf \"o J\" is " + s1.indexOf("o J"));
    // test replace
    System.out.println("s2 replace 'm' with '$' is " + s2.replace('m', '$'));
    System.out.println("s1 replace \"o Java\" with \"o C#\" is " + s1.replace("o Java", "o C#"));
    
    // test count
    System.out.println("s1 count 'o' is " + s1.count('o'));
    System.out.println("s1 count \"o J\" is " + s1.count("o J"));

    // test split
    System.out.println(s1 + "- split \" \" is");
    for (MyString s: s1.split(" ")) {
      System.out.println(s);
    }

    // test trim
    System.out.println("str \"  trimmed  \" is \"" + new MyString("  trimmed  ").trim() + "\"");


    System.out.println("s1 toLowerCase() is " + s1.toLowerCase());
    System.out.println("s2 toUpperCase() is " + s2.toUpperCase());

    // test equalsIgnoreCase
    System.out.println("s1 equalsIgnoreCase s3 is " + s1.equalsIgnoreCase(s3.toUpperCase()));
    System.out.println("s1 equalsIgnoreCase s2 is " + s1.equalsIgnoreCase(s2.toUpperCase()));

    // test compareTo
    System.out.println("s1 compareTo s2 is " + s1.compareTo(s2));
    System.out.println("s1 compareTo s3 is " + s1.compareTo(s3));
    System.out.println("s1 compareToIgnoreCase s3 is " + s1.compareToIgnoreCase(s3.toUpperCase()));


    System.out.println("s1 startsWith Welcome is " + s1.startsWith("Welcome"));
    System.out.println("s1 startsWith Java is " + s1.startsWith("Java"));
    System.out.println("s1 endsWith Welcome is " + s1.endsWith("Welcome"));
    System.out.println("s1 endsWith Java is " + s1.endsWith("Java"));

    System.out.println("s1 valueOf(34) is " + MyString.valueOf(34));
    System.out.println("s1 valueOf(34.5) is " + MyString.valueOf(34.5));

    System.out.println("s1 contains Java is " + s1.contains("Java"));
    System.out.println("s1 contains C++ is " + s1.contains("C++"));

    System.out.println("s1 equals s3 is " + s1.equals(s3));
    System.out.println("s1 equals s2 is " + s1.equals(s2));
  }
}