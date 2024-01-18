# book_ha
## final 
Từ khóa final trong Java có ý nghĩa là biến hoặc phương thức được khai báo với từ khóa này không thể thay đổi giá trị hoặc nội dung sau khi đã được gán một lần. Điều này mang lại một số lợi ích quan trọng trong việc thiết kế và duy trì mã nguồn:

Không thay đổi giá trị:
Khi bạn khai báo một biến hoặc tham số với final, bạn đảm bảo rằng giá trị của nó sẽ không thay đổi sau khi đã được gán. Điều này giúp người đọc mã hiểu rằng giá trị của biến này không thể bị thay đổi bất kỳ khi nào.

An toàn đa luồng:
Biến final là an toàn trong môi trường đa luồng vì giá trị của nó không thay đổi. Nếu một luồng đã gán giá trị cho biến final, các luồng khác sẽ thấy giá trị đó mà không cần phải lo lắng về sự thay đổi đồng thời.

Tối ưu hóa trình biên dịch:
Trình biên dịch có thể thực hiện các tối ưu hóa dựa trên việc biết rằng một biến là final. Điều này có thể giúp tăng hiệu suất của chương trình.

Thiết kế không thay đổi (Immutable Design):
Khi sử dụng final trong việc thiết kế lớp hoặc đối tượng, bạn có thể tạo ra các đối tượng không thay đổi (immutable), nghĩa là giá trị của chúng không thể bị thay đổi sau khi được tạo ra. Điều này có nhiều ưu điểm, bao gồm tính nhất quán và an toàn đa luồng.

Phong cách lập trình chặt chẽ hơn:
Việc sử dụng final là một phong cách lập trình chặt chẽ hơn, giúp giảm rủi ro sai sót và làm cho mã nguồn dễ hiểu hơn.

Trong trường hợp của bạn, private final CustomerRepository repository; có nghĩa là repository là một trường của lớp hoặc biến của phương thức và chỉ có thể được gán giá trị một lần. Điều này giúp đảm bảo rằng một đối tượng CustomerRepository được gán cho repository không thể được thay đổi sau khi đã được khởi tạo.