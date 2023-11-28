# Dictionary Java - Project OOP

## Contributors
- Nguyễn Quang Cường(22026540) - K67J - CNTT - UET 
- Nguyễn Xuân Bách(22026542) - K67J - CNTT - UET
- Đoàn Trung Hiếu(22026506) - K67J - CNTT - UET

## Introduction
- Đây là bài tập lớn của môn Lập trình hướng đối tượng (INT2204 2).
- Dự án có 2 bản là commandLine và đồ họa được viết bằng ngôn ngữ java và đồ họa dùng thư viện javafx

## Preview graphic
![image](https://github.com/HieuDoan11102004/oopProject/assets/124696095/1411df60-496f-4bf7-a796-d541c520141f)

## Preview commandLine
![image](https://github.com/HieuDoan11102004/oopProject/assets/124696095/ee84013e-59b1-4b0c-8490-b0ea3af445fb)



## Features
### Dự án có những tính năng chính như sau:
- CRUD: thêm sửa xóa từ vựng
- Tìm kiếm từ
- Tra từ
- Phát âm từ
- Dịch từ
- Game luyện từ vựng

## Technologies
### Những công nghệ chính sử dụng trong dự án:
- Trình soạn thảo code: IntelliJ IDEA
- Javafx - thư viện dùng để làm đồ họa
- Công cụ hỗ trợ thiết kế giao diện: Scene Builder
- CSS dùng để trang trí cho giao diện
- FreeTTS - thư viện dùng để phát âm từ
- Google translate API - dùng để dịch từ

## Guide
### Hướng dẫn chạy dự án
- Cài openJDK, FreeTTS, Scene Builder...
- File -> Project Structure -> Add các đường dẫn vào SDKs, Libraries
- Tiếp theo config VM để chạy: Modify options -> Add VM options: --module-path "\path\to\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.media
