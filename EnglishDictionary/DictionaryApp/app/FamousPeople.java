package DictionaryApp.app;

import java.util.Random;

public class FamousPeople {
    private String[] famousPs = {"Messi", "Ronaldo", "Sơn Tùng MTP", "Lại Văn Sâm", "Em trai anh Khá", "Đời Buồn", "Cường Chơi Đá", "Hiếu Nửa Viên", "NXBer"};

    public String getFamousPs() {
        Random random = new Random();
        int index = random.nextInt(famousPs.length);
        return famousPs[index];
    }
}