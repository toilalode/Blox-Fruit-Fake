>BLOX FRUIT FAKE - WEBVIEW
============================
Game hành động 2D chơi trực tiếp trên trình duyệt (HTML5 Canvas), lấy cảm hứng từ Blox Fruits — ăn Trái Ác Quỷ, luyện Mastery, biến hình, cân quái vật và Boss trên đảo.

🔗 **Chơi ngay:** https://toilalode.github.io/Blox-Fruit-Fake/

---

## ✨ Tính năng chính

- ⚔️ Hệ thống Trái Ác Quỷ: **Dragon** (có dạng Hybrid + Full), **Kitsune**, **Empyrean** (chỉ có dạng Full)
- 👊 Hệ thống Võ: Võ Vừa & Huyết Quỷ Thuật — mỗi Võ luyện Mastery **riêng biệt**, đổi qua lại không mất tiến trình
- 📈 Mastery tách riêng theo từng loại Trái (ăn trái nào tính riêng trái đó)
- 🎯 Bộ đếm sát thương cộng dồn, tự ẩn sau 5 giây không đánh trúng
- ⚙️ Bảng Cài Đặt đầy đủ: Zoom camera, FPS/Ping, vị trí Joystick, độ trong suốt nút, rung haptic, auto-save, xuất/nhập Save
- 📴 Tự động chuyển **Offline** khi mất mạng — chơi tiếp bình thường, không mất dữ liệu
- 📱 Hỗ trợ cài như PWA (thêm vào màn hình chính) và đóng gói thành app Android

---

## 🛠️ Công nghệ

- HTML5 Canvas 2D + JavaScript thuần (không dùng framework/engine ngoài)
- Lưu tiến trình qua `localStorage` (offline) + `window.storage` (đồng bộ, nếu môi trường hỗ trợ)
- Service Worker (`sw.js`) cache toàn bộ game để chơi được khi mất mạng
- PWA Manifest (`manifest.json`) — cài được như app trên điện thoại

---

## 📂 Cấu trúc thư mục

```
Blox-Fruit-Fake/
├── index.html        # Toàn bộ game (1 file HTML duy nhất, tự nhận diện môi trường online/app đóng gói)
├── sw.js              # Service Worker — cache offline cho bản web
├── manifest.json      # Khai báo PWA
├── config.xml         # Cấu hình build app native (Cordova)
├── icon.png           # Icon gốc cho app native
└── icons/             # Icon nhiều kích thước cho PWA (36 → 512px)
```

---

## ▶️ Chạy thử ở máy local

Không cần cài đặt gì thêm — chỉ cần 1 web server tĩnh bất kỳ (Service Worker yêu cầu `http(s)://`, không chạy được khi mở trực tiếp file `index.html` bằng `file://`):

```bash
# Ví dụ dùng Python có sẵn
python3 -m http.server 8080
```

Sau đó mở `http://localhost:8080` trên trình duyệt.

---

## 📦 Đóng gói thành app Android

File `index.html` này tự nhận diện khi chạy trong app đóng gói (`file://`, Cordova/Capacitor) và **tự bỏ qua** Service Worker — chỉ cần copy `index.html` vào thư mục `www/` của project build (dùng `config.xml` đính kèm), không cần chỉnh sửa gì thêm.

---

## 🗒️ Ghi chú

Game đang trong giai đoạn phát triển/beta, các hệ thống gameplay (skill, boss, mastery...) sẽ còn tiếp tục được cập nhật.

Khi cập nhật `index.html`/`manifest.json`/icon, nhớ tăng số phiên bản `CACHE_NAME` trong `sw.js` (ví dụ `v2` → `v3`) để trình duyệt người chơi tự tải lại bản mới thay vì dùng cache cũ.
