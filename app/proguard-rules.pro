-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# [Kakao SDK 버그 방어]
# AccessTokenInterceptor에서 ClientErrorCause 상수를 리플렉션(getField)으로 찾기 때문에 난독화에서 제외함
-keep enum com.kakao.sdk.common.model.ClientErrorCause {
    *;
}

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
