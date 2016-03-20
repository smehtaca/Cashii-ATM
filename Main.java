import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // assign the OS native look and feel
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        TellerGUI gui = new TellerGUI();
        gui.init();
    }
}
