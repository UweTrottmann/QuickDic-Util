import java.util.Random;


public class Password {

  /**
   * @param args
   */
  public static void main(String[] args) {
    final Random random = new Random();
    final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (int i = 0; i < 9; ++i) {
     System.out.print(chars.charAt(random.nextInt(chars.length())));
    }
  }

}
