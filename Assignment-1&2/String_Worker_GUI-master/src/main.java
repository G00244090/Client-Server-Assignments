import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
//
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.FileInputStream;
//
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.SwingWorker;
class MyPanel extends JPanel
{
    private JButton jcomp1;
    private JButton jcomp2;
    private JButton jcomp3;
    private JButton jcomp4;
    private JTextArea jcomp5;
    private JTextArea jcomp6;
    private JTextArea jcomp7;
    private JTextArea jcomp8;
    int file_selected = 0;

    public MyPanel()
    {
        //construct components
        jcomp1 = new JButton ("Load");
        jcomp2 = new JButton ("Reverse");
        jcomp3 = new JButton ("Swap");
        jcomp4 = new JButton ("Count");
        jcomp5 = new JTextArea (5, 5);
        jcomp5.setLineWrap(true);
        jcomp5.setWrapStyleWord(true);
        jcomp6 = new JTextArea (5, 5);
        jcomp6.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jcomp6.setLineWrap(true);
        jcomp6.setWrapStyleWord(true);
        jcomp7 = new JTextArea (5, 5);
        jcomp7.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jcomp7.setLineWrap(true);
        jcomp7.setWrapStyleWord(true);
        jcomp8 = new JTextArea (5, 5);
        //adjust size and set layout
        setPreferredSize(new Dimension(667, 366));
        setLayout(null);

        //add components
        add(jcomp1);
        add(jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add(jcomp7);
        add(jcomp8);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds(0, 0, 160, 35);
        jcomp2.setBounds(170, 0, 160, 35);
        jcomp3.setBounds (340, 0, 160, 35);
        jcomp4.setBounds (505, 0, 160, 35);
        jcomp5.setBounds (0, 35, 160, 330);
        jcomp6.setBounds (170, 35, 160, 330);
        jcomp7.setBounds(340, 35, 160, 330);
        jcomp8.setBounds(505, 35, 160, 330);

        jcomp1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                JFileChooser jFileChooser1 = new JFileChooser(new File("."));
                jFileChooser1.showOpenDialog(jFileChooser1);
                File sel_file = jFileChooser1.getSelectedFile();
                file_selected++;
SwingUtilities.invokeLater(new Runnable() {

    @Override
    public void run() {
        SwingWorker<String, String> worker = new SwingWorker<String, String>() {

            @Override
            protected String doInBackground() throws Exception {
                BufferedReader inputStream = new BufferedReader(new FileReader(sel_file));
                String c = inputStream.readLine();
                publish(c);
                while ((c = inputStream.readLine()) != null) {
                    publish(c);
                }
                return c;
            }

            protected void done() {
//                        System.out.println("process to done");
//                        String status;
//                        try {
//                            status = get();
//                            jcomp5.append(status);
//                        } catch (InterruptedException e1) {
//                            e1.printStackTrace();
//                        } catch (ExecutionException e1) {
//                            e1.printStackTrace();
//                        }

            }

            protected void process(List<String> a) {
                int lines = a.size();
                int cnt = 0;
                String status = "";
                while (lines != 0) {
                    status = a.get(cnt);
                    jcomp5.append(status);
                    cnt++;
                    lines--;
                }
                System.out.println("Load Done");
            }
        };
        worker.execute();
    }
});


            }
        });

        jcomp2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (file_selected == 0)
                {

                }
                else
                {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            SwingWorker<String, String> worker2 = new SwingWorker<String,String>()
                            {
                                protected String doInBackground() throws Exception
                                {
                                    String c = jcomp5.getText();
                                    String d = "";
                                    StringTokenizer rev = new StringTokenizer(c, ".");
                                    String words = "";

                                    while(rev.hasMoreTokens())
                                    {
                                        words = rev.nextToken() + " " + words;

                                    }

                                    StringTokenizer rev2 = new StringTokenizer(words, " ");
                                    while(rev2.hasMoreTokens())
                                    {

                                        d = rev2.nextToken() + " " + d;
                                    }
                                    rev= new StringTokenizer(c);
                                    return d;
                                }
                                protected void done()
                                {
                                    System.out.println("Reverse done");
                                    String status;
                                    try {
                                        status = get();
                                        jcomp6.setText(status);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    } catch (ExecutionException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            };
                            worker2.execute();
                        }
                    });

                }
            }
        });
        jcomp3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(file_selected == 0)
                {

                }
                else
                {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            SwingWorker<String[], String> worker3 = new SwingWorker<String[],String>()
                            {
                                protected String[] doInBackground() throws Exception
                                {
                                    String c = jcomp6.getText();
                                    String d = "";
                                    String temp = "";
                                    StringTokenizer rev = new StringTokenizer(c);
                                    int cnt = 0;
                                    String[] words = c.split(" ");
                                    for (int i = 0; i < words.length; i++) {
                                        if (cnt == 0)
                                        {
                                            temp = words[i];
                                            cnt++;
                                        }
                                        else if (cnt == 1)
                                        {
                                            words[i - 1] = words[i];
                                            words[i] = temp;
                                            cnt = 0;
                                        }
                                    }
                                    return words;
                                }
                                protected void done()
                                {
                                    System.out.println("Swap done");
                                    String[] status;
                                    try {
                                        status = get();
                                        for (int i = 0; i < status.length; i++)
                                        {
                                            jcomp7.append(status[i] + " ");
                                        }
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    } catch (ExecutionException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            };
                            worker3.execute();
                        }
                    });

                }
            }
        });
        jcomp4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (file_selected == 0)
                {

                }
                else
                {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            SwingWorker< String, String> worker4 = new SwingWorker< String,String>()
                            {
                                protected  String doInBackground() throws Exception
                                {
                                    String c = jcomp5.getText();
                                    String d ="";
                                    String[] stringsplit = c.split("\\W");
                                    int cnt = 0;
                                    ArrayList<String> list = new ArrayList<>();
                                    for (String s : stringsplit)
                                    {
                                        if (!list.contains(s))
                                        {
                                            list.add(s);
                                        }
                                    }

                                    for (int i = 0; i < list.size(); i++)
                                    {
                                        for (int j = 0; j < stringsplit.length; j++)
                                        {
                                            if (list.get(i).equalsIgnoreCase(stringsplit[j]))
                                            {
                                                cnt++;
                                            }
                                        }
                                        d+=(list.get(i) + " " + cnt + "\n");
                                        cnt = 0;
                                    }
                                    return d;
                                }
                                protected void done()
                                {
                                    System.out.println("Count done");
                                    try {
                                        jcomp8.append(get());
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    } catch (ExecutionException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            };
                            worker4.execute();
                        }
                    });

                }
            }
        });
    }

    public static void main (String[] args)
    {
        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MyPanel());
        frame.pack();
        frame.setVisible (true);
    }
}
