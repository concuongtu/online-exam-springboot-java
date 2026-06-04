USE online_exam;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(100),
    role VARCHAR(20) NOT NULL
);

CREATE TABLE subjects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL
);

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer VARCHAR(1) NOT NULL,
    CONSTRAINT fk_questions_subjects
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);

CREATE TABLE results (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    username VARCHAR(50),
    subject_id INT,
    score DOUBLE,
    total_questions INT,
    correct_count INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_results_users
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL,
    CONSTRAINT fk_results_subjects
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE SET NULL
);
INSERT INTO users(username, password, fullname, role)
VALUES
('admin', '123456', 'Quản trị viên', 'admin'),
('user', '123456', 'Người dùng', 'user');

INSERT INTO subjects (subject_name) VALUES
('Lập trình Java'),
('Cơ sở dữ liệu'),
('Mạng máy tính'),
('Hệ điều hành'),
('Cấu trúc dữ liệu'),
('Spring Boot');

INSERT INTO questions
(subject_id, question_text, option_a, option_b, option_c, option_d, correct_answer)
VALUES

-- Môn 1: Lập trình Java
(1,'Java là ngôn ngữ lập trình gì?','Hướng đối tượng','Thủ tục','Assembly','Truy vấn dữ liệu','A'),
(1,'Từ khóa dùng để kế thừa trong Java là gì?','implements','extends','inherit','super','B'),
(1,'Phương thức chính trong Java là gì?','start()','run()','main()','init()','C'),
(1,'Kiểu dữ liệu nào dùng để lưu số nguyên?','int','float','double','char','A'),
(1,'Lớp gốc của mọi lớp trong Java là gì?','String','Object','System','Class','B'),
(1,'Từ khóa tạo đối tượng trong Java là gì?','create','new','make','object','B'),
(1,'Java chạy trên nền tảng nào?','JVM','JDK','JRE','IDE','A'),
(1,'Từ khóa khai báo hằng số là gì?','static','final','const','var','B'),
(1,'Gói chứa lớp Scanner là gì?','java.io','java.util','java.net','java.sql','B'),
(1,'Toán tử so sánh bằng trong Java là gì?','=','==','===','!=','B'),
(1,'Từ khóa dùng để xử lý ngoại lệ là gì?','try-catch','if-else','switch','for','A'),
(1,'Interface trong Java dùng để làm gì?','Khai báo lớp','Khai báo phương thức trừu tượng','Tạo biến','Kết nối CSDL','B'),
(1,'ArrayList thuộc gói nào?','java.util','java.io','java.lang','java.sql','A'),
(1,'String trong Java dùng để lưu gì?','Số nguyên','Chuỗi ký tự','Số thực','Ký tự đơn','B'),
(1,'Từ khóa dùng để ghi đè phương thức là gì?','@Override','@Entity','@Controller','@Autowired','A'),

-- Môn 2: Cơ sở dữ liệu
(2,'SQL là viết tắt của gì?','Structured Query Language','Simple Query Language','System Query Language','Standard Query Language','A'),
(2,'Lệnh dùng để truy vấn dữ liệu là gì?','INSERT','UPDATE','SELECT','DELETE','C'),
(2,'Khóa chính trong CSDL là gì?','Primary Key','Foreign Key','Unique Key','Index','A'),
(2,'Lệnh dùng để thêm dữ liệu là gì?','ADD','INSERT','CREATE','PUT','B'),
(2,'Lệnh dùng để xóa dữ liệu là gì?','REMOVE','DELETE','CLEAR','DROP','B'),
(2,'Lệnh dùng để cập nhật dữ liệu là gì?','CHANGE','UPDATE','EDIT','ALTER','B'),
(2,'Khóa ngoại dùng để làm gì?','Liên kết bảng','Xóa bảng','Tạo database','Sao lưu dữ liệu','A'),
(2,'Lệnh tạo bảng là gì?','CREATE TABLE','MAKE TABLE','NEW TABLE','ADD TABLE','A'),
(2,'Lệnh xóa bảng là gì?','DELETE TABLE','DROP TABLE','REMOVE TABLE','CLEAR TABLE','B'),
(2,'Mệnh đề WHERE dùng để làm gì?','Sắp xếp','Lọc dữ liệu','Nhóm dữ liệu','Tạo bảng','B'),
(2,'Mệnh đề ORDER BY dùng để làm gì?','Lọc dữ liệu','Sắp xếp dữ liệu','Xóa dữ liệu','Thêm dữ liệu','B'),
(2,'Mệnh đề GROUP BY dùng để làm gì?','Nhóm dữ liệu','Sắp xếp','Xóa bảng','Tạo khóa','A'),
(2,'Hàm COUNT dùng để làm gì?','Tính tổng','Đếm số dòng','Tính trung bình','Tìm lớn nhất','B'),
(2,'JOIN dùng để làm gì?','Gộp bảng dữ liệu','Xóa bảng','Tạo database','Đổi tên bảng','A'),
(2,'Kiểu VARCHAR dùng để lưu gì?','Số nguyên','Chuỗi ký tự','Ngày tháng','Số thực','B'),

-- Môn 3: Mạng máy tính
(3,'TCP thuộc tầng nào trong mô hình OSI?','Network','Transport','Session','Application','B'),
(3,'Địa chỉ IPv4 gồm bao nhiêu bit?','16','32','64','128','B'),
(3,'Thiết bị định tuyến mạng là gì?','Hub','Switch','Router','Repeater','C'),
(3,'Giao thức HTTP dùng cho dịch vụ nào?','Web','Email','File Transfer','Remote Login','A'),
(3,'Cổng mặc định của HTTP là gì?','21','25','80','443','C'),
(3,'Cổng mặc định của HTTPS là gì?','80','110','443','25','C'),
(3,'DNS dùng để làm gì?','Cấp phát IP','Phân giải tên miền','Truyền file','Mã hóa dữ liệu','B'),
(3,'DHCP dùng để làm gì?','Cấp phát IP tự động','Gửi email','Truy cập web','Quản lý file','A'),
(3,'IP dùng để làm gì?','Định danh thiết bị mạng','Lưu trữ dữ liệu','Thiết kế giao diện','Biên dịch chương trình','A'),
(3,'LAN là mạng gì?','Mạng cục bộ','Mạng diện rộng','Mạng toàn cầu','Mạng không dây','A'),
(3,'WAN là mạng gì?','Mạng cục bộ','Mạng diện rộng','Mạng cá nhân','Mạng nội bộ','B'),
(3,'Switch hoạt động chủ yếu ở tầng nào?','Physical','Data Link','Network','Application','B'),
(3,'Ping dùng để làm gì?','Kiểm tra kết nối mạng','Xóa IP','Tạo website','Mã hóa dữ liệu','A'),
(3,'FTP dùng để làm gì?','Truyền tệp','Gửi mail','Phân giải tên miền','Cấp phát IP','A'),
(3,'SMTP dùng để làm gì?','Gửi email','Truy cập web','Truyền file','Quản lý IP','A'),

-- Môn 4: Hệ điều hành
(4,'Hệ điều hành có chức năng gì?','Quản lý tài nguyên máy tính','Thiết kế web','Soạn thảo văn bản','Lập trình CSDL','A'),
(4,'Process là gì?','Chương trình đang thực thi','Tệp văn bản','Thiết bị mạng','Câu lệnh SQL','A'),
(4,'RAM là bộ nhớ gì?','Bộ nhớ trong','Bộ nhớ ngoài','Thiết bị nhập','Thiết bị xuất','A'),
(4,'CPU có chức năng gì?','Xử lý lệnh','Lưu trữ lâu dài','Hiển thị hình ảnh','Kết nối Internet','A'),
(4,'Deadlock là gì?','Tắc nghẽn tiến trình','Tăng tốc hệ thống','Xóa bộ nhớ','Tạo tiến trình','A'),
(4,'File System dùng để làm gì?','Quản lý tệp tin','Quản lý giao diện','Quản lý trình duyệt','Quản lý email','A'),
(4,'Windows là gì?','Hệ điều hành','Trình duyệt','Ngôn ngữ lập trình','CSDL','A'),
(4,'Linux là gì?','Hệ điều hành','Phần mềm vẽ','Công cụ SQL','Trình biên dịch Java','A'),
(4,'Thread là gì?','Luồng xử lý','Ổ cứng','Màn hình','Bàn phím','A'),
(4,'Scheduler dùng để làm gì?','Lập lịch tiến trình','Xóa file','Cấp phát IP','Tạo CSDL','A'),
(4,'Bộ nhớ ảo là gì?','Virtual Memory','Cache','ROM','Register','A'),
(4,'ROM là bộ nhớ gì?','Bộ nhớ chỉ đọc','Bộ nhớ truy cập ngẫu nhiên','Bộ nhớ tạm','Ổ đĩa','A'),
(4,'I/O là viết tắt của gì?','Input/Output','Internet Online','Internal Object','Index Order','A'),
(4,'Kernel là gì?','Nhân hệ điều hành','Trình duyệt','CSDL','Tệp tin','A'),
(4,'Booting là quá trình gì?','Khởi động hệ thống','Tắt máy','Xóa bộ nhớ','Cài đặt mạng','A'),

-- Môn 5: Cấu trúc dữ liệu
(5,'Stack hoạt động theo nguyên tắc nào?','FIFO','LIFO','Random','Priority','B'),
(5,'Queue hoạt động theo nguyên tắc nào?','FIFO','LIFO','Random','Tree','A'),
(5,'Linked List là gì?','Danh sách liên kết','Mảng tĩnh','Cây nhị phân','Hàng đợi','A'),
(5,'Array là gì?','Mảng','Ngăn xếp','Hàng đợi','Cây','A'),
(5,'Tree là cấu trúc gì?','Cây','Mảng','Danh sách','Bảng băm','A'),
(5,'Binary Tree là gì?','Cây nhị phân','Mảng hai chiều','Danh sách đơn','Hàng đợi','A'),
(5,'Graph là gì?','Đồ thị','Cây','Mảng','Ngăn xếp','A'),
(5,'Hash Table dùng để làm gì?','Lưu trữ và tìm kiếm nhanh','Sắp xếp ảnh','Gửi email','Thiết kế web','A'),
(5,'Push là thao tác của cấu trúc nào?','Stack','Queue','Tree','Graph','A'),
(5,'Pop là thao tác của cấu trúc nào?','Stack','Array','Tree','Graph','A'),
(5,'Enqueue là thao tác của cấu trúc nào?','Queue','Stack','Tree','Array','A'),
(5,'Dequeue là thao tác của cấu trúc nào?','Queue','Stack','Graph','Tree','A'),
(5,'Duyệt cây theo thứ tự trước gọi là gì?','Preorder','Inorder','Postorder','Levelorder','A'),
(5,'Tìm kiếm nhị phân yêu cầu dữ liệu như thế nào?','Đã sắp xếp','Chưa sắp xếp','Rỗng','Ngẫu nhiên','A'),
(5,'Bubble Sort là thuật toán gì?','Sắp xếp nổi bọt','Tìm kiếm','Duyệt cây','Băm dữ liệu','A'),

-- Môn 6: Spring Boot
(6,'Spring Boot là framework của ngôn ngữ nào?','Java','PHP','Python','C#','A'),
(6,'File cấu hình chính của Spring Boot là gì?','application.properties','pom.xml','index.html','web.xml','A'),
(6,'Annotation đánh dấu Controller là gì?','@Controller','@Entity','@Repository','@Table','A'),
(6,'Annotation đánh dấu Entity là gì?','@Entity','@Controller','@Service','@Autowired','A'),
(6,'Spring Data JPA dùng để làm gì?','Truy xuất cơ sở dữ liệu','Thiết kế giao diện','Gửi email','Tạo ảnh','A'),
(6,'Thymeleaf dùng để làm gì?','Tạo giao diện động','Kết nối mạng','Quản lý bộ nhớ','Biên dịch Java','A'),
(6,'Maven dùng để làm gì?','Quản lý thư viện','Thiết kế CSDL','Chạy MySQL','Vẽ biểu đồ','A'),
(6,'Repository trong Spring Boot dùng để làm gì?','Thao tác dữ liệu','Hiển thị HTML','Tạo Session','Định dạng CSS','A'),
(6,'Controller dùng để làm gì?','Xử lý request','Lưu dữ liệu','Tạo bảng','Thiết kế giao diện','A'),
(6,'Model dùng để làm gì trong MVC?','Chứa dữ liệu gửi sang View','Xóa database','Tạo server','Tạo CSS','A'),
(6,'View trong Spring Boot thường là gì?','HTML Thymeleaf','Database','Repository','Entity','A'),
(6,'Annotation @GetMapping dùng cho phương thức nào?','GET','POST','PUT','DELETE','A'),
(6,'Annotation @PostMapping dùng cho phương thức nào?','POST','GET','PUT','DELETE','A'),
(6,'HttpSession dùng để làm gì?','Lưu thông tin phiên đăng nhập','Tạo database','Gửi email','Tạo câu hỏi','A'),
(6,'Lệnh chạy ứng dụng Spring Boot thường chạy file nào?','OnlineExamApplication.java','application.properties','pom.xml','index.html','A');

SELECT * FROM subjects;
