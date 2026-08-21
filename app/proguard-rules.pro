# Quy tắc R8/ProGuard cho bản release.
#
# QUAN TRỌNG: BaseWebViewActivity CÓ dùng addJavascriptInterface() — bridge tên
# "AndroidStats" (method updateStats(String json)) để trang game (JS) báo số
# liệu Kill/HP/Mana lên cho Widget màn hình chính hiển thị (xem saveGameProgress()
# trong index.html, gọi mỗi 4 giây). Nếu không giữ rule bên dưới, R8 sẽ đổi tên/
# xoá mất method này ở bản Release (minifyEnabled true) -> JS gọi
# window.AndroidStats.updateStats(...) âm thầm thất bại (bị try/catch nuốt lỗi
# ở phía JS nên KHÔNG crash), Widget vĩnh viễn không có dữ liệu mới — trong khi
# bản Debug (không bật R8) vẫn chạy đúng bình thường nên rất dễ bỏ sót lỗi này.
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Giữ lại thông tin dòng code trong crash log để dễ đọc stack trace khi debug
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
