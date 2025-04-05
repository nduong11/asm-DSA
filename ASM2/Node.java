package asm;

class Node {
    SinhVien sinhVien;
    Node left;
    Node right;
    int height;

    public Node(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        this.height = 1;
    }
}


