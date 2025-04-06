package ASM2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SinhVienGUI extends JFrame {

    private AVLTree tree = new AVLTree();
    private JTextArea dataArea;
    private TreePanel treeDisplay;

    public SinhVienGUI() {
        setTitle("Student Management - Binary Tree (AVL)  -  BH01888  -  Nông Ngọc Dương");
        setLayout(new BorderLayout());

        // Student list panel
        JPanel dataPanel = new JPanel(new BorderLayout());
        dataArea = new JTextArea(15, 30);
        dataArea.setEditable(false);

        // Sort buttons panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton btnTang = new JButton("Sort Ascending");
        JButton btnGiam = new JButton("Sort Descending");
        btnTang.addActionListener(new ActionHandler());
        btnGiam.addActionListener(new ActionHandler());
        sortPanel.add(btnTang);
        sortPanel.add(btnGiam);

        // Add sort panel to data panel
        JPanel dataContainer = new JPanel(new BorderLayout());
        dataContainer.add(sortPanel, BorderLayout.NORTH);
        dataContainer.add(new JScrollPane(dataArea), BorderLayout.CENTER);
        dataPanel.add(dataContainer, BorderLayout.CENTER);
        dataPanel.setBorder(BorderFactory.createTitledBorder("Student List"));

        // AVL Tree panel
        JPanel treePanel = new JPanel(new BorderLayout());
        treeDisplay = new TreePanel(tree);
        treePanel.add(new JScrollPane(treeDisplay), BorderLayout.CENTER);
        treePanel.setBorder(BorderFactory.createTitledBorder("Binary Tree (AVL)"));

        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        String[] buttonLabels = {"Add", "Edit", "Delete", "Search"};
        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.addActionListener(new ActionHandler());
            controlPanel.add(btn);
        }

        add(dataPanel, BorderLayout.WEST);
        add(treePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(1200, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();
            switch (command) {
                case "Add":
                    themSinhVien();
                    break;
                case "Edit":
                    suaSinhVien();
                    break;
                case "Delete":
                    xoaSinhVien();
                    break;
                case "Search":
                    timKiemSinhVien();
                    break;
                case "Sort Ascending":
                    sapXepTang();
                    break;
                case "Sort Descending":
                    sapXepGiam();
                    break;
            }
        }
    }

    private void themSinhVien() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField idField = new JTextField();
        idField.setEditable(false);
        JTextField tenField = new JTextField();
        JTextField diemField = new JTextField();

        // Generate automatic ID
        int newId = 1;
        while (tree.searchById(newId) != null) {
            newId++;
        }
        idField.setText(String.valueOf(newId));

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(tenField);
        inputPanel.add(new JLabel("Score (0-10):"));
        inputPanel.add(diemField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String ten = tenField.getText().trim();
                double diem = Double.parseDouble(diemField.getText());

                if (ten.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty!");
                }
                if (diem < 0 || diem > 10) {
                    throw new IllegalArgumentException("Score must be between 0 and 10!");
                }

                tree.insert(new SinhVien(id, ten, diem));
                updateUI();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid data format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void suaSinhVien() {
        String input = JOptionPane.showInputDialog(this, "Enter student ID to edit:");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(input);
            SinhVien sv = tree.searchById(id);
            if (sv == null) {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel editPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField tenField = new JTextField(sv.ten);
            JTextField diemField = new JTextField(String.valueOf(sv.diem));

            editPanel.add(new JLabel("New Name:"));
            editPanel.add(tenField);
            editPanel.add(new JLabel("New Score:"));
            editPanel.add(diemField);

            int result = JOptionPane.showConfirmDialog(this, editPanel, "Edit Information", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String ten = tenField.getText().trim();
                double diem = Double.parseDouble(diemField.getText());

                if (ten.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty!");
                }
                if (diem < 0 || diem > 10) {
                    throw new IllegalArgumentException("Score must be between 0 and 10!");
                }

                tree.delete(id);
                tree.insert(new SinhVien(id, ten, diem));
                updateUI();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid data format!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSinhVien() {
        String input = JOptionPane.showInputDialog(this, "Enter student ID to delete:");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(input);
            SinhVien sv = tree.searchById(id);
            if (sv != null) {
                tree.delete(id);
                updateUI();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be an integer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemSinhVien() {
        String input = JOptionPane.showInputDialog(this, "Enter student ID to search:");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(input);
            SinhVien sv = tree.searchById(id);
            if (sv != null) {
                JOptionPane.showMessageDialog(this, sv.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Notice", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be an integer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sapXepTang() {
        tree.updateRanks();
        dataArea.setText(tree.inOrderTang());
        treeDisplay.repaint();
    }

    private void sapXepGiam() {
        tree.updateRanks();
        dataArea.setText(tree.inOrderGiam());
        treeDisplay.repaint();
    }

    private void updateUI() {
        tree.updateRanks();
        dataArea.setText(tree.inOrder());
        treeDisplay.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SinhVienGUI());
    }
}
