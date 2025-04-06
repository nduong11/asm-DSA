package ASM2;

import java.util.ArrayList;
import java.util.List;

class AVLTree {

    Node root;

    int getHeight(Node node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(Node node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    Node rightRotate(Node node) {
        Node temp = node.left;
        Node tempRight = temp.right;

        temp.right = node;
        node.left = tempRight;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;

        return temp;
    }

    Node leftRotate(Node node) {
        Node temp = node.right;
        Node tempLeft = temp.left;

        temp.left = node;
        node.right = tempLeft;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;

        return temp;
    }

    Node insertNode(Node node, SinhVien sinhVien) {
        if (node == null) {
            return new Node(sinhVien);
        }

        if (sinhVien.id < node.sinhVien.id) {
            node.left = insertNode(node.left, sinhVien);
        } else {
            node.right = insertNode(node.right, sinhVien);
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        // Left-Left Case
        if (balance > 1 && sinhVien.id < node.left.sinhVien.id) {
            return rightRotate(node);
        }
        // Right-Right Case
        if (balance < -1 && sinhVien.id >= node.right.sinhVien.id) {
            return leftRotate(node);
        }
        // Left-Right Case
        if (balance > 1 && sinhVien.id >= node.left.sinhVien.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right-Left Case
        if (balance < -1 && sinhVien.id < node.right.sinhVien.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void insert(SinhVien sinhVien) {
        root = insertNode(root, sinhVien);
    }

    Node deleteNode(Node node, int id) {
        if (node == null) {
            return null;
        }

        if (id < node.sinhVien.id) {
            node.left = deleteNode(node.left, id);
        } else if (id > node.sinhVien.id) {
            node.right = deleteNode(node.right, id);
        } else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    return null; 
                }else {
                    return temp;
                }
            } else {
                Node successor = findMinValueNode(node.right);
                node.sinhVien = successor.sinhVien;
                node.right = deleteNode(node.right, successor.sinhVien.id);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void delete(int id) {
        root = deleteNode(root, id);
    }

    Node findMinValueNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Tìm kiếm theo ID sử dụng Binary Search Tree
    public SinhVien searchById(int id) {
        return searchByIdNode(root, id);
    }

    private SinhVien searchByIdNode(Node node, int id) {
        if (node == null) {
            return null;
        }

        if (id == node.sinhVien.id) {
            return node.sinhVien;
        } else if (id < node.sinhVien.id) {
            return searchByIdNode(node.left, id);
        } else {
            return searchByIdNode(node.right, id);
        }
    }

    // Tìm kiếm theo khoảng ID sử dụng Binary Search Tree
    public List<SinhVien> searchByIdRange(int minId, int maxId) {
        List<SinhVien> result = new ArrayList<>();
        searchByIdRangeNode(root, minId, maxId, result);
        return result;
    }

    private void searchByIdRangeNode(Node node, int minId, int maxId, List<SinhVien> result) {
        if (node == null) {
            return;
        }

        if (node.sinhVien.id < minId) {
            searchByIdRangeNode(node.right, minId, maxId, result);
        } else if (node.sinhVien.id > maxId) {
            searchByIdRangeNode(node.left, minId, maxId, result);
        } else {
            result.add(node.sinhVien);
            searchByIdRangeNode(node.left, minId, maxId, result);
            searchByIdRangeNode(node.right, minId, maxId, result);
        }
    }

    // Cập nhật rank
    void updateRanks() {
        List<SinhVien> students = new ArrayList<>();
        inOrderForRank(root, students);

        // Sắp xếp theo điểm giảm dần (điểm cao = rank thấp)
        students.sort((s1, s2) -> {
            if (s2.diem != s1.diem) {
                return Double.compare(s2.diem, s1.diem); 
            }else {
                return Integer.compare(s1.id, s2.id);
            }
        });

        // Gán rank
        for (int i = 0; i < students.size(); i++) {
            students.get(i).setRank(i + 1);
        }
    }

    private void inOrderForRank(Node node, List<SinhVien> students) {
        if (node != null) {
            inOrderForRank(node.left, students);
            students.add(node.sinhVien);
            inOrderForRank(node.right, students);
        }
    }

    // Hiển thị danh sách
    void inOrder(Node node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.left, sb);
            sb.append(node.sinhVien.toString()).append("\n");
            inOrder(node.right, sb);
        }
    }

    String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    // Hiển thị danh sách theo rank tăng dần
    String inOrderTang() {
        List<SinhVien> students = new ArrayList<>();
        inOrderForRank(root, students);

        // Sắp xếp theo rank tăng dần
        students.sort((s1, s2) -> Integer.compare(s1.rank, s2.rank));

        StringBuilder sb = new StringBuilder();
        for (SinhVien sv : students) {
            sb.append(sv.toString()).append("\n");
        }
        return sb.toString();
    }

    // Hiển thị danh sách theo rank giảm dần
    String inOrderGiam() {
        List<SinhVien> students = new ArrayList<>();
        inOrderForRank(root, students);

        // Sắp xếp theo rank giảm dần
        students.sort((s1, s2) -> Integer.compare(s2.rank, s1.rank));

        StringBuilder sb = new StringBuilder();
        for (SinhVien sv : students) {
            sb.append(sv.toString()).append("\n");
        }
        return sb.toString();
    }
}
