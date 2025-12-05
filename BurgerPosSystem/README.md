<<<<<<< HEAD
# Hamburger-OOP
Dự án máy bán Hamburger sử dụng Java Swing GUI

## 🎯 Các tính chất OOP được thể hiện

### 1. **Đóng gói (Encapsulation)**
- Tất cả các thuộc tính trong class `Item` được khai báo `private` (name, type, price, size)
- Truy cập thông qua các phương thức getter/setter public:
  ```java
  public String getName() { return name; }
  public void setSize(String size) { this.size = size; }
  ```
- Class `Burger` đóng gói danh sách toppings và maxToppings, chỉ cho phép thêm topping qua method `addTopping()`

### 2. **Kế thừa (Inheritance)**
- **Cấu trúc kế thừa:**
  - `Item` (abstract class - lớp cha)
    - `Burger` extends `Item`
      - `DeluxeBurger` extends `Burger`
    - `Drink` extends `Item`
    - `SideItem` extends `Item`

- Các class con kế thừa thuộc tính và phương thức từ class cha `Item`
- `DeluxeBurger` kế thừa từ `Burger` và mở rộng `maxToppings = 5`

### 3. **Đa hình (Polymorphism)**
- **Method Overriding:**
  - `getAdjustedPrice()` được override ở các class:
    - `Item`: Tính giá theo size (SMALL, MEDIUM, LARGE)
    - `Burger`: Tính giá burger + toppings
    - `DeluxeBurger`: Giá cố định, không tính thêm topping (đã bao gồm)
  
  ```java
  // Item.java
  public double getAdjustedPrice() {
      return switch (size.toUpperCase()) {
          case "SMALL" -> price - 0.5;
          case "LARGE" -> price + 1.0;
          default -> price;
      };
  }
  
  // DeluxeBurger.java
  @Override
  public double getAdjustedPrice() {
      return getBasePrice(); // Không tính phí topping
  }
  ```

- **Runtime Polymorphism:** Đối tượng `Burger` có thể là `Burger` hoặc `DeluxeBurger`, method `getAdjustedPrice()` được gọi dựa trên loại thực tế

### 4. **Trừu tượng (Abstraction)**
- Class `Item` được khai báo `abstract`:
  ```java
  public abstract class Item { ... }
  ```
- Không thể tạo trực tiếp object `Item`, chỉ có thể tạo các subclass cụ thể (Burger, Drink, SideItem)
- Cung cấp template chung cho tất cả các item trong cửa hàng

### 🎁 Tính năng bổ sung
- **Exception Handling:** `ToppingLimitException` - Xử lý trường hợp thêm quá số lượng topping cho phép
- **File I/O:** Lưu hóa đơn vào file `receipt.txt` thông qua method `printReceipt(true)`

---

## 🚀 Cách chạy code

### Yêu cầu hệ thống
- **Java Development Kit (JDK):** phiên bản 14 trở lên (do sử dụng switch expression)
- **IDE:** IntelliJ IDEA, Eclipse, hoặc VS Code với Java Extension

### Cách 1: Chạy bằng IDE (Khuyến nghị)

1. **Clone repository:**
   ```bash
   git clone https://github.com/fishsauce-05/Hamburger-OOP.git
   cd Hamburger-OOP
   ```

2. **Mở project trong IDE:**
   - IntelliJ IDEA: `File → Open → Chọn thư mục project`
   - Eclipse: `File → Import → Existing Projects into Workspace`

3. **Chạy chương trình:**
   - Tìm file `BurgerAppGUI.java` trong `src/com/burgerstore/ui/`
   - Click chuột phải → `Run 'BurgerAppGUI.main()'`

### Cách 2: Chạy bằng Command Line

1. **Compile code:**
   ```bash
   # Từ thư mục gốc của project
   javac -d bin src/com/burgerstore/**/*.java
   ```

2. **Chạy chương trình:**
   ```bash
   java -cp bin com.burgerstore.ui.BurgerAppGUI
   ```

### Hướng dẫn sử dụng
1. Chọn loại Burger (Classic Beef, Spicy Chicken, hoặc Deluxe Combo)
2. Chọn các topping (tối đa 3 cho burger thường, 5 cho Deluxe)
3. Chọn đồ uống và kích thước (SMALL/MEDIUM/LARGE)
4. Chọn món phụ (Fries, Nuggets, v.v.)
5. Nhấn "Place Order" để xem hóa đơn
6. Hóa đơn sẽ được lưu vào file `receipt.txt`

---

## 📁 Cấu trúc thư mục
```
Hamburger-OOP/
├── src/
│   └── com/
│       └── burgerstore/
│           ├── model/           # Các class mô hình (Item, Burger, Drink...)
│           ├── service/         # Logic xử lý đơn hàng (MealOrder)
│           ├── ui/              # Giao diện GUI (BurgerAppGUI)
│           └── exception/       # Custom exceptions
├── bin/                         # Compiled .class files
└── receipt.txt                  # Hóa đơn xuất ra
```

---

## 📝 Tác giả
- **GitHub:** [@fishsauce-05](https://github.com/fishsauce-05)
- **Project:** Hamburger OOP - Máy bán Hamburger tự động
=======
Burger Store Management System - Premium Edition

1. Giới thiệu

Đây là dự án phần mềm quản lý bán hàng cho cửa hàng Burger, được xây dựng bằng ngôn ngữ Java với giao diện đồ họa Swing. Dự án được thiết kế để giải quyết bài toán "Bill's Burger Challenge", tập trung vào việc áp dụng triệt để các nguyên lý Lập trình hướng đối tượng (OOP) để xử lý các logic phức tạp về giá cả, phân loại món ăn và quản lý đơn hàng.

2. Phân tích OOP trong dự án

Dự án này là một ví dụ điển hình về cách áp dụng 4 tính chất cốt lõi của OOP để xây dựng hệ thống mềm dẻo, dễ bảo trì và mở rộng.

1. Tính Đóng gói (Encapsulation)

Thể hiện ở đâu:

Các thuộc tính của món ăn như name, price, toppings trong lớp Item và Burger đều được khai báo là private hoặc protected.

Việc thay đổi dữ liệu (ví dụ: thêm topping) không được thực hiện trực tiếp vào List mà phải thông qua phương thức addTopping().

Ý nghĩa:

Bảo vệ dữ liệu: Ngăn chặn việc truy cập hoặc sửa đổi trái phép từ bên ngoài. Ví dụ: Không ai có thể tự ý set giá của Deluxe Burger về 0đ từ bên ngoài class.

Kiểm soát logic: Phương thức addTopping() đóng vai trò "người gác cổng", kiểm tra xem Burger đã đạt giới hạn topping hay chưa (3 món cho thường, 5 món cho Deluxe) trước khi cho phép thêm vào.

2. Tính Kế thừa (Inheritance)

Thể hiện ở đâu:

Lớp cha Item (Món ăn) chứa các đặc điểm chung: Tên, Giá cơ bản, Kích cỡ.

Các lớp con Burger, Drink, SideItem đều kế thừa từ Item (quan hệ IS-A).

Đặc biệt: DeluxeBurger kế thừa trực tiếp từ Burger.

Ý nghĩa:

Tái sử dụng mã nguồn: DeluxeBurger sở hữu ngay lập tức khả năng quản lý danh sách topping của Burger mà không cần viết lại code.

Tổ chức phân cấp: Giúp hệ thống có cấu trúc rõ ràng, dễ hiểu và logic.

3. Tính Đa hình (Polymorphism) - Điểm sáng nhất của dự án

Thể hiện ở đâu:

Phương thức getAdjustedPrice() (Tính giá thực tế) được định nghĩa ở lớp cha Item nhưng hoạt động khác nhau ở từng lớp con:

Tại Drink: Giá thay đổi theo Size (Lớn cộng thêm tiền, Nhỏ trừ bớt tiền).

Tại Burger: Giá = Giá gốc + Tổng tiền các Topping.

Tại DeluxeBurger: Giá = Giá trọn gói (Override để luôn trả về giá gốc, Topping trở thành miễn phí).

Ý nghĩa:

Linh hoạt: Class MealOrder khi tính tổng tiền chỉ cần gọi item.getAdjustedPrice() mà không cần quan tâm đó là món gì. Burger tự biết tính giá kiểu Burger, Nước tự biết tính giá kiểu Nước.

4. Tính Trừu tượng (Abstraction)

Thể hiện ở đâu:

Class Item được khai báo là abstract class.

Ý nghĩa:

Bạn không thể tạo ra một đối tượng "Món ăn" chung chung (new Item() sẽ lỗi). Bạn bắt buộc phải tạo ra một món cụ thể như new Burger() hoặc new Drink().

Nó định nghĩa một khuôn mẫu chung mà tất cả các món ăn trong nhà hàng bắt buộc phải tuân theo (phải có tên, phải có giá, phải tính được giá điều chỉnh).

3. Cấu trúc dự án

BurgerStoreSystem/
├── src/
│   └── com/
│       └── burgerstore/
│           ├── exception/  # Chứa Custom Exception (ToppingLimitException)
│           ├── model/      # Chứa các thực thể (Burger, Drink, Item...)
│           ├── service/    # Chứa logic xử lý hóa đơn (MealOrder)
│           └── ui/         # Chứa giao diện đồ họa (BurgerAppGUI)
├── bin/                    # Chứa file .class sau khi biên dịch (tự sinh ra)
├── receipt.txt             # File hóa đơn được xuất ra sau khi order
└── run.bat                 # Script chạy chương trình nhanh trên Windows


4. Hướng dẫn cài đặt và chạy chương trình

Yêu cầu hệ thống

Máy tính đã cài đặt Java Development Kit (JDK) (Phiên bản 8 trở lên).

Hệ điều hành: Windows, macOS, hoặc Linux.

Cách 1: Chạy bằng file Script (Khuyên dùng cho Windows)

Trong thư mục gốc của dự án, tôi đã chuẩn bị sẵn file run.bat.

Tìm file run.bat.

Nhấn đúp chuột (Double click) vào file này.

Chương trình sẽ tự động biên dịch và mở giao diện bán hàng lên.

Cách 2: Chạy thủ công bằng dòng lệnh (CMD/Terminal)

Nếu bạn không dùng Windows hoặc muốn chạy thủ công, hãy làm theo các bước sau:

Mở CMD hoặc Terminal tại thư mục gốc của dự án (BurgerStoreSystem).

Biên dịch code:

javac -d bin -sourcepath src src/com/burgerstore/ui/BurgerAppGUI.java



Chạy chương trình:

java -cp bin com.burgerstore.ui.BurgerAppGUI



5. Hướng dẫn sử dụng phần mềm

Chọn món chính: Chọn loại Burger từ danh sách (Bò, Gà, hoặc các loại Deluxe Combo).

Thêm Topping: Chọn các món thêm (Phô mai, Thịt xông khói...).

Lưu ý: Burger thường tối đa 3 món, Deluxe tối đa 5 món.

Chọn đồ uống & món kèm: Chọn loại nước, kích cỡ và món ăn phụ.

Đặt hàng:

Nhấn PLACE CUSTOM ORDER để đặt theo lựa chọn của bạn.

Nhấn QUICK DEFAULT MEAL để hệ thống tự chọn combo tiêu chuẩn (nhanh).

Xem hóa đơn: Hóa đơn chi tiết sẽ hiện ở khung bên phải và tự động lưu vào file receipt.txt.

Dự án thực hiện bởi [Tên của bạn]
>>>>>>> 81d1431 (Java)
