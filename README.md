BLOX FRUIT FAKE — FRUIT WARRIORS
============================
Game hành động 2D chơi trực tiếp trên trình duyệt (HTML5 Canvas), lấy cảm hứng từ Blox Fruits — ăn Trái Ác Quỷ, luyện Mastery, biến hình, cân quái vật/Boss, quay Gacha, giao dịch và chơi cùng người khác trong phòng chung.

🔗 **Chơi ngay:** https://toilalode.github.io/Blox-Fruit-Fake/

---

## ✨ Tính năng chính

**Chiến đấu & biến hình**
- ⚔️ Hệ thống Trái Ác Quỷ: **Dragon** (Đông/Tây, có dạng Hybrid → Full khi đầy Fury), **Kitsune**, **Empyrean Kitsune** (chỉ có dạng Full)
- 🗡️ Vũ khí **Dark Blade** — phần thưởng sau khi hạ Boss trong Sự Kiện Ẩn, 5 skill riêng (M1, Z, X, C, H)
- 👊 2 chế độ chiến đấu: **Võ Thường** & **Huyết Quỷ Thuật** — mỗi chế độ luyện Mastery **riêng biệt**, đổi qua lại không mất tiến trình
- 📈 Mastery tách riêng theo từng loại Trái (ăn trái nào tính riêng trái đó) và theo từng lối đánh
- 🐉 Boss theo cốt truyện: **Long Vương** (6 skill, ultimate QTE ở ≤15% HP) và Boss trong Sự Kiện Ẩn ở đảo **Cửu Vĩ**
- 🎯 Bộ đếm sát thương cộng dồn, tự ẩn sau vài giây không đánh trúng

**Gacha, đồ đạc & giao dịch**
- 🎰 Gacha quay Trái Ác Quỷ ngẫu nhiên, có lượt miễn phí + trả tiền khi hết lượt
- 🎒 Hệ thống Balo Tạm Thời (trái đang cầm, mất khi thoát) và Balo Lưu Trữ (rương, lưu vĩnh viễn)
- 🔁 Trade 2 chiều: xem trước đồ của nhau (trái/tiền/kiếm/súng) rồi mới xác nhận

**Nhiều người chơi**
- 🌐 Phòng chơi chung qua WebSocket (Cloudflare Durable Objects) — thấy người chơi khác theo thời gian thực
- 💬 5 chế độ chat: Có kiểm duyệt / Không kiểm duyệt / Ẩn danh (phòng chung) + Chat Riêng (DM) + Chat Nhóm
- 🏆 Bảng xếp hạng toàn server theo Level/Gold/số Boss đã hạ

**Admin**
- 🔑 Xác thực Admin Key thật qua server (không tin tên phiên client)
- 🛠️ Admin Panel với đầy đủ nút lệnh (set level/tiền/HP/mana/trái/võ/vé gacha, TP đảo, ban/kick, cấp quyền admin tạm thời...) hoặc gõ lệnh `/...` trực tiếp trong ô chat
- 🌍 Bật/tắt sự kiện toàn server (x50 sát thương, Sự Kiện Ẩn) — ai cũng thấy banner khi đang diễn ra; bật **Sự Kiện Ẩn** sẽ cho xuất hiện ngay NPC Bí Cảnh Cửu Vĩ tại Đảo Cửu Vĩ (không cần đợi đúng giờ tròn)
- 🚫 Chống gian lận: ban theo tên/HWID/IP, kick tạm thời khỏi phòng chung
- 🔍 Chẩn đoán & gỡ ban chi tiết: tra chính xác 1 người đang bị khoá theo tên/IP/thiết bị nào (`checkBan`), gỡ thẳng theo IP hoặc thiết bị cụ thể (`unbanIp`/`unbanDevice`) mà không cần biết tên gốc đã bị ban
- 🛡️ **Admin được kháng ban & kháng kick**: không ai (kể cả admin khác) có thể ban/kick một tài khoản đang nằm trong danh sách admin (cố định hoặc được cấp tạm thời) — server chặn thẳng ở lệnh, không chỉ ẩn ở giao diện

**Trải nghiệm chung**
- ⚙️ Bảng Cài Đặt đầy đủ: Zoom camera, FPS/Ping, vị trí Joystick, độ trong suốt nút, rung haptic, auto-save, xuất/nhập Save
- 📴 Tự động chuyển **Offline** khi mất mạng — chơi tiếp bình thường, không mất dữ liệu, tự đồng bộ lại khi có mạng
- 📱 Hỗ trợ cài như PWA (thêm vào màn hình chính) và đóng gói thành app Android
- 📲 Thiết kế mobile-first, chơi hoàn toàn bằng cảm ứng

---

## 🛠️ Công nghệ

**Frontend**
- HTML5 Canvas 2D + JavaScript thuần (không dùng framework/engine ngoài), gói gọn trong 1 file `index.html`
- Lưu tiến trình qua `localStorage` (offline) + server (đồng bộ nhiều thiết bị)
- Service Worker (`sw.js`) cache toàn bộ game để chơi được khi mất mạng
- PWA Manifest (`manifest.json`) — cài được như app trên điện thoại

**Backend**
- Cloudflare Worker (`worker.js`) — REST API cho save/load, leaderboard, trade, chat, admin
- KV Namespace `GAME_DATA` — lưu save data, ban list, quyền admin tạm thời
- Durable Object `GameRoom` — phòng chơi chung qua WebSocket (vị trí, hành động, chat theo phòng)
- Durable Object `GlobalState` — sự kiện toàn server + trade (atomic, tránh race condition khi nhiều người thao tác cùng lúc)

---

## 📂 Cấu trúc thư mục

```
Blox-Fruit-Fake/
├── index.html         # Toàn bộ game (1 file HTML duy nhất, tự nhận diện môi trường online/app đóng gói)
├── worker.js           # Backend Cloudflare Worker (API + WebSocket + Durable Objects)
├── wrangler.toml       # Cấu hình deploy Worker (bindings KV/Durable Objects, secret ADMIN_KEY)
├── sw.js                # Service Worker — cache offline cho bản web
├── manifest.json        # Khai báo PWA
├── config.xml            # Cấu hình build app native (Cordova)
├── icon.png               # Icon gốc cho app native
└── icons/                  # Icon nhiều kích thước cho PWA (36 → 512px)
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
wrangler login
wrangler secret put ADMIN_KEY      # đặt mật khẩu admin thật, chỉ server biết
wrangler deploy
```

Cấu hình bindings (KV `GAME_DATA`, Durable Objects `ROOM`/`GLOBAL`) đã có sẵn trong `wrangler.toml`.

---

## 📦 Đóng gói thành app Android

File `index.html` tự nhận diện khi chạy trong app đóng gói (`file://`, Cordova/Capacitor) và **tự bỏ qua** Service Worker — chỉ cần copy `index.html` vào thư mục `www/` của project build (dùng `config.xml` đính kèm), không cần chỉnh sửa gì thêm.

---

## 🔐 Admin

Chỉ các tên phiên trong danh sách admin cố định (**"toilalode"** và các tên dự phòng đã đăng ký khác) mới có thể mở khoá Admin Panel. Khi phát hiện phiên đang dùng 1 trong các tên này, game sẽ hiện popup yêu cầu nhập **Admin Key** (mật khẩu thật, lưu trên server qua secret `ADMIN_KEY`) — nhập đúng mới dùng được lệnh admin/sự kiện toàn server. Có thể cấp quyền admin tạm thời (giới hạn lệnh) cho người chơi khác qua lệnh `/addplayer`.

**Miễn nhiễm ban/kick:** mọi tài khoản admin (cố định hoặc tạm thời) đều được server tự động bỏ qua khi có lệnh `ban`/`banHwid`/`kick` nhắm vào — kể cả khi lệnh đó đến từ 1 admin khác. Việc này được kiểm tra ở phía server (`worker.js`), không phải chỉ ẩn nút trên giao diện, nên không thể bị lách qua bằng cách gọi thẳng API. Nếu 1 admin từng bị ban/kick trước khi được cấp quyền, lệnh admin tiếp theo sẽ tự bỏ qua các khoá cũ đó (không cần unban thủ công).

**Chẩn đoán ban:** dùng nút 🔍 **Tra ban** (hoặc lệnh `/checkban <tên>`) trong Admin Panel để xem chính xác 1 người đang bị khoá theo tên, IP hay thiết bị (deviceId) — hữu ích khi 2 tài khoản khác tên nhưng cùng máy/mạng vô tình dính ban chéo do `banHwid`. Gỡ thẳng theo IP/thiết bị cụ thể bằng `/unbanip <ip>` hoặc `/unbandevice <deviceId>` mà không cần biết tên tài khoản gốc đã bị ban.

---

## 🗒️ Ghi chú

Game đang trong giai đoạn phát triển/beta, các hệ thống gameplay (skill, boss, mastery, sự kiện...) sẽ còn tiếp tục được cập nhật.

**Cập nhật gần nhất:** admin được kháng ban/kick (server-side); thêm công cụ chẩn đoán & gỡ ban theo IP/thiết bị (`checkBan`/`unbanIp`/`unbanDevice`); sửa lỗi bật "Sự Kiện Ẩn" không làm NPC Bí Cảnh Cửu Vĩ xuất hiện ngoài khung giờ tròn.

Khi cập nhật `index.html`/`manifest.json`/icon, nhớ tăng số phiên bản `CACHE_NAME` trong `sw.js` (ví dụ `v2` → `v3`) để trình duyệt người chơi tự tải lại bản mới thay vì dùng cache cũ.

Khi cập nhật `worker.js`, nhớ `wrangler deploy` lại để thay đổi có hiệu lực trên server thật.
