package changcheng;

import javax.swing.JFrame;

/**
 * Created by changcheng on 2017/4/16.
 */
public class JNotePad extends JFrame {
    public JNotePad() {
        initComponent();
        initEventListeners();
    }

    public void initComponent() {
        setTitle("新增纯文本文档");
        setSize(400, 300);
    }

    private void initEventListeners() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
