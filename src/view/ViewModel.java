/*package view;

import javafx.scene.canvas.Canvas;

public class ViewModel extends Canvas{
	private static final long serialVersionUID = 1L;

    public ViewModel(final PipeGameBoard board) {
        JButton load = new JButton("Load");
        this.add(load);
        final TextField ip = new TextField("127.0.0.1");
        this.add(ip);
        final TextField port = new TextField("6400");
        this.add(port);
        JButton solve = new JButton("Solve");
        this.add(solve);
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                c.setFileFilter(new FileFilter() {
                    public String getDescription() {
                        return "Text Files in Pipe Game Format";
                    }

                    public boolean accept(File f) {
                        return f.getName().endsWith(".txt");
                    }
                });
                int rVal = c.showOpenDialog(GamePanel.this);
                if (rVal == 0) {
                    List lines = null;

                    try {
                        lines = (List)Files.lines(Paths.get(c.getSelectedFile().getAbsolutePath())).collect(Collectors.toList());
                    } catch (IOException var9) {
                        var9.printStackTrace();
                    }

                    char[][] data = new char[lines.size()][];
                    int i = 0;

                    for(Iterator var8 = lines.iterator(); var8.hasNext(); ++i) {
                        String s = (String)var8.next();
                        data[i] = s.toCharArray();
                    }

                    board.setData(data);
                }

            }
        });
        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                (new Thread(() -> {
                    SolverClient.solve(ip.getText(), Integer.parseInt(port.getText()), board);
                })).start();
            }
        });
    }

}*/
