# [Retrofit Service Interfaces]
# R8이 제네릭 시그니처를 제거하면 ResultCallAdapterFactory가 Call<Result<T>>의
# 타입 인자를 런타임에 읽지 못해 "Unable to create call adapter" 에러 발생
-keep interface com.threegap.bitnagil.data.**.service.** { *; }
