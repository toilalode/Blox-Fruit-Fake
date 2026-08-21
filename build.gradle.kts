import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

// === KÝ APP TỰ ĐỘNG (để build 1 lệnh ra file đã ký, không cần mở Wizard) ===
// Đọc thông tin keystore từ file keystore.properties (KHÔNG commit file này vào git).
// Nếu file chưa tồn tại (máy mới, chưa tạo keystore) thì bỏ qua và build sẽ dùng
// debug-signing như bình thường -> vẫn build được ngay, không bị lỗi Gradle Sync.
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
val hasReleaseSigning = keystorePropertiesFile.exists()
if (hasReleaseSigning) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.bloxfruit.fake"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.bloxfruit.fake"
        // minSdk 21 = hỗ trợ từ Android 5.0 (2014) trở lên. Để thấp như vậy giúp cài
        // được trên máy cũ/giá rẻ còn phổ biến ở VN, đổi lại code phải tự lo tương
        // thích ngược (đã có trong BaseWebViewActivity: check Build.VERSION.SDK_INT
        // trước khi dùng API mới). Nếu số liệu người dùng thực tế cho thấy không còn
        // ai dùng máy dưới Android 8 (SDK 26) nữa thì có thể nâng lên để đơn giản code.
        minSdk = 21
        // targetSdk: Google Play định kỳ nâng yêu cầu tối thiểu (thường mỗi năm 1 lần
        // vào khoảng giữa năm) - nên kiểm tra lại giá trị này trước mỗi lần nộp app
        // mới/update lên Play Store, xem giá trị có còn đáp ứng yêu cầu hiện hành không.
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            // Bật thu gọn code (R8) + xoá tài nguyên không dùng tới cho bản release:
            // giảm dung lượng APK/AAB, khó dịch ngược hơn. App chỉ là 1 WebView đơn
            // giản, không dùng reflection nên an toàn khi bật; nếu sau này thêm code
            // phức tạp mà bị lỗi lạ ở bản release (nhưng debug chạy được), thử tắt
            // isMinifyEnabled về false trước để loại trừ nguyên nhân do R8.
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    compileOptions {
        // Nâng từ Java 8 lên Java 25 (LTS mới nhất) — yêu cầu JDK 25 trở lên để build.
        // minSdk 21 (Android 5.0) vẫn chạy được app biên dịch bằng Java 25 vì D8/R8
        // desugar bytecode xuống mức API 21 hỗ trợ (Android không giới hạn bởi
        // "Java version" theo cách JVM thường làm — chỉ giới hạn bởi API Level).
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    // Phải khớp với compileOptions ở trên, nếu không Kotlin và Java sẽ biên dịch
    // ra bytecode target khác nhau -> Gradle Sync báo lỗi "inconsistent JVM target".
    kotlinOptions {
        jvmTarget = "25"
    }

    // BẮT BUỘC từ AGP 8.0 trở lên: mặc định KHÔNG tự sinh class BuildConfig nữa
    // (phải bật thủ công). UpdateChecker.kt có dùng BuildConfig.VERSION_CODE
    // và BuildConfig.VERSION_NAME để so sánh bản đang chạy với version.json trên
    // mạng — thiếu dòng này, Gradle Sync có thể qua được nhưng Build sẽ báo lỗi
    // "cannot find symbol: class BuildConfig" ngay khi biên dịch UpdateChecker.kt.
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")

    // === Thêm PHÒNG HỜ theo yêu cầu — hiện project CHƯA dùng tới class nào của
    // 3 thư viện dưới đây (đã rà code xác nhận), nên không thêm cũng không lỗi
    // build. Giữ sẵn ở đây để sau này thêm tính năng mới (nút giao diện Material,
    // kéo-để-làm-mới, v.v.) thì không cần sửa Gradle nữa, dùng luôn.
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.1")
}
