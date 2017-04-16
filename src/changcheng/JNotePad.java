package changcheng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by changcheng on 2017/4/16.
 */
public class JNotePad extends JFrame {
    private JMenuBar menuBar;

    private TextDAO textDAO;

    private JFileChooser fileChooser;

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

    private JTextArea textArea;
    private JLabel stateBar;

    private JPopupMenu popupMenu;

    public JNotePad(TextDAO textDAO) {
        this();
        this.textDAO = textDAO;
    }

    protected JNotePad() {
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

        textArea = new JTextArea();
        textArea.setFont(new Font("明细体", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        JScrollPane panel = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Container containerPane = getContentPane();
        containerPane.add(panel, BorderLayout.CENTER);

        stateBar = new JLabel("未修改");
        stateBar.setHorizontalAlignment(SwingConstants.LEFT);
        stateBar.setBorder(BorderFactory.createEtchedBorder());
        containerPane.add(stateBar, BorderLayout.SOUTH);

        popupMenu = editMenu.getPopupMenu();

        fileChooser = new JFileChooser();
    }

    private void openFile() {
        if (stateBar.getText().equals("未修改")) {
            showFileDialog();
        } else {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "文档已修改，是否存储？",
                    "存储文档?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null
            );

            switch (option) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    showFileDialog();
                    break;
            }
        }
    }

    private void showFileDialog() {
        int option = fileChooser.showDialog(null, null);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                setTitle(
                        fileChooser.getSelectedFile().toString()
                );
                textArea.setText("");
                stateBar.setText("未修改");
                String text = textDAO.read(
                        fileChooser.getSelectedFile().toString()
                );
                textArea.setText(text);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, e.toString(),
                        "打开文档失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        Path path = Paths.get(getTitle());
        if (Files.notExists(path)) {
            saveFileAs();
        } else {
            try {
                textDAO.save(path.toString(), textArea.getText());
                stateBar.setText("未修改");
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null,
                        e.toString(),
                        "写入文档失败",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void saveFileAs() {
        int option = fileChooser.showDialog(null, null);
        if (option == JFileChooser.APPROVE_OPTION) {
            setTitle(fileChooser.getSelectedFile().toString());
            textDAO.create(
                    fileChooser.getSelectedFile().toString()
            );
            saveFile();
        }
    }
    private void closeFile() {
        if (stateBar.getText().equals("未修改")) {
            dispose();
        } else {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "文档以修改，是否存储？",
                    "存储文档?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null
            );
            switch (option) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    dispose();
            }
        }
    }
    private void cut() { }
    private void copy() { }
    private void paste() { }
    private void jtextAreaActionPerformed() { }

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

        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        closeFile();
                    }
                }
        );

        menuOpen.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openFile();
                    }
                }
        );

        menuSave.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                }
        );

        menuSaveAs.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveFileAs();
                    }
                }
        );

        menuClose.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        closeFile();
                    }
                }
        );

        menuCut.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cut();
                    }
                }
        );

        menuCopy.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        copy();
                    }
                }
        );

        menuPaste.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        paste();
                    }
                }
        );

        menuAbout.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showOptionDialog(null, "\tJNotePad 1.0\n comes from changcheng",
                                "About JNotePad",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null, null, null);
                    }
                }
        );

        textArea.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        jtextAreaActionPerformed();
                    }
                }
        );

        textArea.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1)
                            popupMenu.setVisible(false);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3)
                            popupMenu.show(editMenu, e.getX(), e.getY());
                    }
                }
        );
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JNotePad(new FileTextDAO()).setVisible(true);
            }
        });
    }

}
