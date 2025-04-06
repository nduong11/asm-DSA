package ASM2;

public class SinhVien {

    int id;
    String ten;
    double diem;
    int rank;

    public SinhVien(int id, String ten, double diem) {
        this.id = id;
        this.ten = ten;
        this.diem = diem;
    }

    public String getTrangThai() {
        if (diem < 5) {
            return "Fail"; 
        }else if (diem < 6.5) {
            return "Medium"; 
        }else if (diem < 7.5) {
            return "Good"; 
        }else if (diem < 9) {
            return "Very Good"; 
        }else {
            return "Excellent";
        }
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Rank: " + rank
                + ", ID: " + id
                + ", Name: " + ten
                + ", Score: " + diem
                + ", Status: " + getTrangThai();
    }
}
