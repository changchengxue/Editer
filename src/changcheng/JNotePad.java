package changcheng;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by changcheng on 2017/4/16.
 */
public class JNotePad extends JFrame {
    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;
    private JMenuItem menuClose;

    private JMenu editMenu;
    private JMenuItem menuCut;
    private JMenuItem menuCopy;
    private JMenuItem menuPaste;

    private JMenu aboutMenu;
    private JMenuItem menuAbout;


    public JNotePad() {
        initComponent();
        initEventListeners();
    }

    public void initComponent() {
        setTitle("新增纯文本文档");
        setSize(400, 300);

        fileMenu = new JMenu("文档");
        menuOpen = new JMenuItem("打开文档");
        menuSave = new JMenuItem("存储文档");
        menuSaveAs = new JMenuItem("另存为");
        menuClose = new JMenuItem("关闭");

        fileMenu.add(menuOpen);
        fileMenu.add(menuSave);
        fileMenu.add(menuSaveAs);
        fileMenu.add(menuClose);

        editMenu = new JMenu("编辑");
        menuCut = new JMenuItem("剪切");
        menuCopy = new JMenuItem("复制");
        menuPaste = new JMenuItem("粘贴");

        editMenu.add(menuCut);
        editMenu.add(menuCopy);
        editMenu.add(menuPaste);

        aboutMenu = new JMenu("关于");
        menuAbout = new JMenuItem("关于JNotePad");

        aboutMenu.add(menuAbout);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);
    }

    private void initEventListeners() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_MASK));
        menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_MASK));
        menuClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_MASK));
        menuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                InputEvent.CTRL_MASK));
        menuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_MASK));
        menuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                InputEvent.CTRL_MASK));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JNotePad().setVisible(true);
            }
        });
    }

}
