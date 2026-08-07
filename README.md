BLOX FRUIT FAKE - WEBVIEW
============================

Nội dung file zip:
- index.html          -> Game chính (đã gắn icon, luôn full màn hình + luôn ngang)
- manifest.json       -> Cấu hình PWA (tên: Blox Fruit Fake, display: fullscreen, orientation: landscape)
- icon.png / icons/   -> Icon ứng dụng (từ hình vẽ tay), nhiều kích cỡ (36 - 512px)
- config.xml          -> Cấu hình Cordova (nếu dùng công cụ build APK kiểu Cordova/PhoneGap)
- www/                -> Bản sao index.html + manifest + icons (một số công cụ đòi hỏi thư mục www/)
- res/icon/android/   -> Icon Android riêng (một số công cụ Cordova đòi hỏi đường dẫn này)

CÁCH DÙNG:
1) Nếu dùng app "đóng gói web thành APK" (Website to APK, WebView Builder...):
   - Chọn index.html (ở thư mục gốc) làm trang chủ
   - Chọn icon.png làm icon ứng dụng
   - Đặt tên ứng dụng: Blox Fruit Fake
   - Bật tùy chọn Fullscreen = true, Orientation = Landscape (đã cấu hình sẵn trong index.html
     và manifest.json để tự khóa ngang + full màn hình ngay cả khi công cụ không hỗ trợ tùy chọn này)

2) Nếu dùng Cordova/PhoneGap CLI:
   - Copy toàn bộ nội dung zip vào project, dùng file config.xml có sẵn
   - Chạy: cordova platform add android && cordova build android

Ghi chú: Do đây là gói mã nguồn/webview, để có file .apk cài trực tiếp trên điện thoại,
cần đưa gói này qua một công cụ build APK (Cordova CLI, Android Studio WebView project,
hoặc app "web to apk" trên máy tính/điện thoại).