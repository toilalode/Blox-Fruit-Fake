// sw.js — Cache trang để chơi được OFFLINE khi mất mạng (dùng cho bản index.html ONLINE / GitHub Pages).
// Đổi CACHE_NAME mỗi lần bạn cập nhật index.html/manifest/icon để trình duyệt lấy bản mới thay vì bản cache cũ.
const CACHE_NAME = 'blox-fruit-fake-v4';
const FILES_TO_CACHE = [
  './',
  './index.html',
  './manifest.json',
  './icons/icon-36.png',
  './icons/icon-48.png',
  './icons/icon-72.png',
  './icons/icon-96.png',
  './icons/icon-144.png',
  './icons/icon-192.png',
  './icons/icon-512.png',
];

// Khi cài đặt: tải trước và lưu các file cần thiết vào cache.
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) =>
      // addAll thất bại toàn bộ nếu 1 file lỗi -> dùng từng cái + bỏ qua lỗi để không chặn cài đặt Service Worker.
      Promise.all(
        FILES_TO_CACHE.map((url) => cache.add(url).catch((err) => console.warn('Không cache được', url, err)))
      )
    )
  );
  self.skipWaiting(); // kích hoạt Service Worker mới ngay, không cần đóng hết tab cũ
});

// Khi kích hoạt: xoá cache phiên bản cũ (nếu CACHE_NAME đã đổi).
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((keys) =>
      Promise.all(keys.filter((k) => k !== CACHE_NAME).map((k) => caches.delete(k)))
    )
  );
  self.clients.claim();
});

// Chiến lược: Network First, fallback Cache khi mất mạng.
// (Ưu tiên bản mới nhất từ mạng nếu có; nếu fetch lỗi/mất mạng -> tự chuyển dùng bản đã cache.)
self.addEventListener('fetch', (event) => {
  if (event.request.method !== 'GET') return;
  event.respondWith(
    fetch(event.request)
      .then((res) => {
        // Cập nhật cache với bản mới nhất mỗi khi tải được thành công
        const resClone = res.clone();
        caches.open(CACHE_NAME).then((cache) => cache.put(event.request, resClone));
        return res;
      })
      .catch(() =>
        // Mất mạng -> lấy từ cache; nếu không có, fallback về index.html (cho SPA)
        caches.match(event.request).then((cached) => cached || caches.match('./index.html'))
      )
  );
});
