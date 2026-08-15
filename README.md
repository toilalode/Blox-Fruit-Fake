BLOX FRUIT FAKE — WEBVIEW
============================
Game hành động 2D chơi trực tiếp trên trình duyệt (HTML5 Canvas), lấy cảm hứng từ Blox Fruits — ăn Trái Ác Quỷ, luyện Mastery, biến hình, cân quái vật/Boss, quay Gacha, giao dịch, PvP Arena, Guild và chơi cùng người khác trong phòng chung.

🔗 **Chơi ngay:**
https://blox-fruit-fake.netlify.app/
             hoặc tại 
https://toilalode.github.io/Blox-Fruit-Fake/
---

## ✨ Tính năng chính

**Chiến đấu & biến hình**
- ⚔️ Hệ thống Trái Ác Quỷ: **Dragon** (Đông/Tây, có dạng Hybrid → Full khi đầy Fury), **Kitsune**, **Empyrean Kitsune** (chỉ có dạng Full)
- 🗡️ Vũ khí **Dark Blade**, 2 phiên bản:
  - **Dark Blade Event** — phần thưởng khi cả nhóm hạ Boss Long Vương ở Sự Kiện Ẩn (đảo Cửu Vĩ), đủ M1/Z/X/C/M2, hệ số nhân hitbox/damage riêng, C nâng cấp lưỡi trắng
  - **Dark Blade** (thường) — mua trong Cửa Hàng bằng Gold, chỉ có M1/Z/X, không C/M2, không hệ số nhân đặc biệt
- 👊 2 chế độ chiến đấu: **Võ Thường** & **Huyết Quỷ Thuật** — mỗi chế độ luyện Mastery **riêng biệt**, đổi qua lại không mất tiến trình
- 📈 Mastery tách riêng theo từng loại Trái (ăn trái nào tính riêng trái đó) và theo từng lối đánh
- 🐉 Boss: **Long Vương** (6 skill, ultimate QTE ở ≤15% HP) và Boss **Cửu Vĩ Hồ Ly Vương** ở Sự Kiện Ẩn (đảo Cửu Vĩ)
- 🎯 Bộ đếm sát thương cộng dồn, tự ẩn sau vài giây không đánh trúng

**Gacha, đồ đạc & giao dịch**
- 🎰 Gacha quay Trái Ác Quỷ ngẫu nhiên, có lượt miễn phí + trả tiền khi hết lượt
- 🎒 Hệ thống Balo Tạm Thời (trái đang cầm, mất khi thoát) và Balo Lưu Trữ (rương, lưu vĩnh viễn)
- 🔁 Trade 2 chiều: xem trước đồ của nhau (trái/tiền/kiếm/súng) rồi mới xác nhận — gồm cả tặng trực tiếp (gift) và tạo đề nghị trao đổi (offer)
- 💰 Bán vật phẩm lấy Gold theo bảng giá server quy định (`/item/sell`)

**Nhiều người chơi**
- 🌐 Phòng chơi chung qua WebSocket (Cloudflare Durable Objects) — thấy người chơi khác theo thời gian thực
- 💬 5 chế độ chat: Có kiểm duyệt / Không kiểm duyệt / Ẩn danh (phòng chung) + Chat Riêng (DM) + Chat Nhóm
- 🏆 Bảng xếp hạng toàn server theo Level/Gold/số Boss đã hạ (có thể bật Ẩn danh trên bảng xếp hạng)
- ⚔️ **Đấu trường (Arena PvP)**: thách đấu người chơi khác, server tính sát thương dựa trên chỉ số thật (không tin số client gửi lên), có forfeit
- 🛡️ **Guild**: tạo/giải tán/mời/kick/rời/đổi role, Guild XP cộng dồn từ chiến thắng PvP, bảng xếp hạng Guild riêng
- 🎫 **Season Pass**: XP theo mùa, 2 track thưởng Free/Premium, admin cấu hình/reset mùa

**Trải nghiệm chung**
- ⚙️ Bảng Cài Đặt đầy đủ: Zoom camera, FPS/Ping, vị trí Joystick, độ trong suốt nút, rung haptic, auto-save, xuất/nhập Save
- 📴 Tự động chuyển **Offline** khi mất mạng — chơi tiếp bình thường, không mất dữ liệu, tự đồng bộ lại khi có mạng
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
├── worker.js           # Cloudflare Worker — toàn bộ backend API + Durable Objects
├── wrangler.toml        # Cấu hình deploy Worker (KV binding, Durable Objects)
├── sw.js                # Service Worker — cache offline cho bản web
├── manifest.json        # Khai báo PWA
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

Cấu hình bindings (KV `GAME_DATA`, Durable Objects `ROOM`/`GLOBAL`/`GUILD`) đã có sẵn trong `wrangler.toml`.

---

## 📦 Đóng gói thành app Android

File `index.html` tự nhận diện khi chạy trong app đóng gói (`file://`, Cordova/Capacitor) và **tự bỏ qua** Service Worker — chỉ cần copy `index.html` vào thư mục `www/` của project build (dùng `config.xml` đính kèm), không cần chỉnh sửa gì thêm.

---

## 🔐 Admin

Chỉ các tên phiên trong danh sách admin cố định (**"toilalode"** và các tên dự phòng đã đăng ký khác) mới có thể mở khoá Admin Panel. Khi phát hiện phiên đang dùng 1 trong các tên này, game sẽ hiện popup yêu cầu nhập **Admin Key** (mật khẩu thật, lưu trên server qua secret `ADMIN_KEY`) — nhập đúng mới dùng được lệnh admin/sự kiện toàn server. Có thể cấp quyền admin tạm thời (giới hạn lệnh) cho người chơi khác qua lệnh `/addplayer`.

**Lệnh admin có sẵn** (qua `/admin/command`, thao tác trực tiếp lên save data thật trong KV): `setLevel`, `setStat`, `setMoney`, `setFightingStyle`, `setFruit`, `tpIsland`, `setCamera`, `setHp`, `setMana`, `setGachaTickets`, `addFruitBag`, `addFruitTemp`, `ban`, `banHwid`, `unban`, `checkBan`, `unbanIp`, `unbanDevice`, `kick`, `unkick`, `addTempAdmin`, `removeTempAdmin`, `addEvent`, `broadcast`, `giveAll`, `deleteAccount`.

**Miễn nhiễm ban/kick:** mọi tài khoản admin (cố định hoặc tạm thời) đều được server tự động bỏ qua khi có lệnh `ban`/`banHwid`/`kick` nhắm vào — kể cả khi lệnh đó đến từ 1 admin khác. Việc này được kiểm tra ở phía server (`worker.js`), không phải chỉ ẩn nút trên giao diện, nên không thể bị lách qua bằng cách gọi thẳng API. Nếu 1 admin từng bị ban/kick trước khi được cấp quyền, lệnh admin tiếp theo sẽ tự bỏ qua các khoá cũ đó (không cần unban thủ công).

**Chẩn đoán ban:** dùng nút 🔍 **Tra ban** (hoặc lệnh `/checkban <tên>`) trong Admin Panel để xem chính xác 1 người đang bị khoá theo tên, IP hay thiết bị (deviceId) — hữu ích khi 2 tài khoản khác tên nhưng cùng máy/mạng vô tình dính ban chéo do `banHwid`. Gỡ thẳng theo IP/thiết bị cụ thể bằng `/unbanip <ip>` hoặc `/unbandevice <deviceId>` mà không cần biết tên tài khoản gốc đã bị ban.

---

## 🗒️ Ghi chú

Game đang trong giai đoạn phát triển/beta, các hệ thống gameplay (skill, boss, mastery, sự kiện, guild, arena, season pass...) sẽ còn tiếp tục được cập nhật.

Khi cập nhật `index.html`/`manifest.json`/icon, nhớ tăng số phiên bản `CACHE_NAME` trong `sw.js` (ví dụ `v2` → `v3`) để trình duyệt người chơi tự tải lại bản mới thay vì dùng cache cũ.

Khi cập nhật `worker.js`, nhớ `wrangler deploy` lại để thay đổi có hiệu lực trên server thật.
