BLOX FRUIT FAKE — WEBVIEW
============================
Game hành động 2D chơi trực tiếp trên trình duyệt (HTML5 Canvas), lấy cảm hứng từ Blox Fruits — ăn Trái Ác Quỷ, luyện Mastery, biến hình, cân quái vật/Boss, quay Gacha, giao dịch, PvP Arena, Guild và chơi cùng người khác trong phòng chung.

🔗 **Chơi ngay:**
- https://toilalode.github.io/Blox-Fruit-Fake/
- **Trường hợp dự phòng:**
- https://blox-fruit-fake.netlify.app/ ( có thể cập nhật ít thông cảm)
- hoặc tại
- https://blox-fruit-fake.ai.studio (có thể cập nhật ít, thông cảm)
             
---

- 📱 Hỗ trợ cài như PWA (thêm vào màn hình chính) và đóng gói thành app Android
- 📲 Thiết kế mobile-first, chơi hoàn toàn bằng cảm ứng

---

## 🛠️ Công nghệ

**Frontend**
- HTML5 Canvas 2D + JavaScript thuần (không dùng framework/engine ngoài), gói gọn trong 1 file `index.html`
- Lưu tiến trình qua `localStorage` (ghi ngay lập tức, đồng bộ) + server (đồng bộ nhiều thiết bị)
- **Gộp ghi server**: nhiều lần `saveGame()` gọi liên tiếp (autosave 10s, sau mỗi hành động...) được **debounce** — chỉ gửi 1 request `/save` duy nhất sau khi im lặng vài giây, thay vì gửi mỗi lần gọi; những thao tác cần dữ liệu mới nhất ngay (trade, bán đồ, xuất mã save...) vẫn đẩy lên ngay lập tức. Khi tab bị ẩn/đóng, dùng `navigator.sendBeacon` để đẩy nốt bản đang chờ.
- Service Worker (`sw.js`) cache toàn bộ game để chơi được khi mất mạng
- PWA Manifest (`manifest.json`) — cài được như app trên điện thoại

**Backend**
- Cloudflare Worker (`worker.js`) — REST API cho save/load, leaderboard, trade, chat, guild, arena, season pass, admin
- KV Namespace `GAME_DATA` — lưu save data, ban list, quyền admin tạm thời
  - **1 key duy nhất mỗi người chơi**: `save:<tên>` chứa CẢ tiến trình game LẪN dữ liệu bảng xếp hạng (`_displayName`, `_lbAnonymous`, `_savedAt`) — không còn key `lb:` riêng như trước, giảm số lần ghi KV mỗi lần save từ 2 xuống còn 1
  - `/leaderboard/top` đọc thẳng từ prefix `save:`, không cần bảng dữ liệu tách riêng
- Durable Object `ROOM` (`GameRoom`) — phòng chơi chung qua WebSocket (vị trí, hành động, chat theo phòng)
- Durable Object `GLOBAL` (`GlobalState`) — sự kiện toàn server + trade (atomic, tránh race condition khi nhiều người thao tác cùng lúc)
- Durable Object `GUILD` (`GuildState`) — instance duy nhất toàn server, quản lý Guild, PvP Arena (matchmaking + kết quả), và Season Pass — cộng Gold/XP thẳng vào save data thật trong KV (nguồn sự thật duy nhất), không tin số liệu client gửi lên

---

## 📂 Cấu trúc thư mục

```
Blox-Fruit-Fake/
├── index.html         # Toàn bộ game (1 file HTML duy nhất, tự nhận diện môi trường online/app đóng gói)
├── sw.js                # Service Worker — cache offline cho bản web
├── manifest.json        # Khai báo PWA
├── icon.png               # Icon gốc cho app native
└── icons/                  # Icon nhiều kích thước cho PWA (36 → 512px)
```

```
Server-Blox-Fruit-Fake/
├── worker.js           # Cloudflare Worker — toàn bộ backend API + Durable Objects
├── wrangler.toml        # Cấu hình deploy Worker (KV binding, Durable Objects)
```

---

## ▶️ Chạy thử ở máy local

**Frontend** — không cần cài đặt gì thêm, chỉ cần 1 web server tĩnh bất kỳ (Service Worker yêu cầu `http(s)://`, không chạy được khi mở trực tiếp file `index.html` bằng `file://`):

```bash
# Ví dụ dùng Python có sẵn
python3 -m http.server 8080
```

Sau đó mở `http://localhost:8080` trên trình duyệt.

**Backend** — cần Cloudflare account + Wrangler CLI để deploy `worker.js`:

```bash
npm install -g wrangler
wrangler login -- device
wrangler login
wrangler secret put ADMIN_KEY      # đặt mật khẩu admin thật, chỉ server biết
wrangler deploy
```

Cấu hình bindings (KV `GAME_DATA`, Durable Objects `ROOM`/`GLOBAL`/`GUILD`) đã có sẵn trong `wrangler.toml`.

---

## 📦 Đóng gói thành app Android

File `index.html` tự nhận diện khi chạy trong app đóng gói (`file://`, Cordova/Capacitor) và **tự bỏ qua** Service Worker — chỉ cần copy `index.html` vào thư mục `www/` của project build (dùng `config.xml` đính kèm), không cần chỉnh sửa gì thêm.

---

## 🗒️ Ghi chú

Game đang trong giai đoạn phát triển/beta, các hệ thống gameplay (skill, boss, mastery, sự kiện, guild, arena, season pass...) sẽ còn tiếp tục được cập nhật.

Khi cập nhật `index.html`/`manifest.json`/icon, nhớ tăng số phiên bản `CACHE_NAME` trong `sw.js` (ví dụ `v2` → `v3`) để trình duyệt người chơi tự tải lại bản mới thay vì dùng cache cũ.

Khi cập nhật `worker.js`, nhớ `wrangler deploy` lại để thay đổi có hiệu lực trên server thật.
