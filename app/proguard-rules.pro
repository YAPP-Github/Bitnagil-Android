-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# [Kakao SDK 프로가드 규칙]
# https://developers.kakao.com/docs/ko/android/getting-started#project-pro-guard
-keep class com.kakao.sdk.**.model.* { <fields>; }

# https://github.com/square/okhttp/pull/6792
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# refrofit2 (with r8 full mode)
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# [Kotlinx Serialization]
# 모든 @Serializable 클래스의 직렬화 로직 및 serializer 메서드 유지
-keepattributes Annotation, InnerClasses, Signature, Exceptions
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# [Domain/Data Layer Enums]
# API 응답 매핑 및 리플렉션 방어를 위해 Enum 상수 이름 유지
-keepclassmembers enum com.threegap.bitnagil.domain.**.model.** {
    <fields>;
}
-keepclassmembers enum com.threegap.bitnagil.data.**.model.** {
    <fields>;
}

# [Compose Navigation Type-Safe Arguments]
# 네비게이션 인자 클래스 및 Route 객체 보존
-keep class com.threegap.bitnagil.presentation.**.model.navarg.** { *; }
-keep class com.threegap.bitnagil.Route** { *; }
# HomeRoute는 navigation.home 패키지로 Route** 패턴 밖이므로 별도 보호
# Compose Navigation이 내부적으로 serializer descriptor name을 route ID로 사용하므로
# deep link 추가 시 repackaging으로 빌드마다 route string이 달라지는 문제 방어
-keep class com.threegap.bitnagil.navigation.home.HomeRoute** { *; }

# [Kotlin Result - Retrofit 반환 타입]
# suspend fun foo(): Result<T> 형태의 Retrofit 인터페이스에서
# R8이 kotlin.Result의 제네릭 타입 인자를 지워 call adapter 탐색 실패 방어
-keep class kotlin.Result { *; }

# [Network Libraries]
# Retrofit 및 OkHttp 어노테이션 유지
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
