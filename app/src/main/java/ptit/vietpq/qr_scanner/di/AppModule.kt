package ptit.vietpq.qr_scanner.di

import android.content.Context
import android.content.SharedPreferences
import coil.ImageLoader
import coil.disk.DiskCache
import coil.util.DebugLogger
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import ptit.vietpq.qr_scanner.data.datalocal.sharepref.SharePreferenceProvider
import ptit.vietpq.qr_scanner.di.AppModule.Companion.CoilImageLoaderConfigs.OK_HTTP_CLIENT_CONNECT_TIMEOUT
import ptit.vietpq.qr_scanner.di.AppModule.Companion.CoilImageLoaderConfigs.OK_HTTP_CLIENT_READ_TIMEOUT
import ptit.vietpq.qr_scanner.di.AppModule.Companion.CoilImageLoaderConfigs.OK_HTTP_CLIENT_WRITE_TIMEOUT
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.logger.CoilEventListenerLogger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Duration
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun imageLoader(
    @ApplicationContext appContext: Context,
    okHttpClient: OkHttpClient,
    coilEventListenerLogger: CoilEventListenerLogger,
  ): ImageLoader = ImageLoader
    .Builder(appContext)
    .logger(
     null
    )
    .okHttpClient(okHttpClient)
    .eventListener(coilEventListenerLogger)
    .diskCache {
      DiskCache
        .Builder()
        .directory(appContext.cacheDir.resolve("image_cache"))
        .maxSizePercent(CoilImageLoaderConfigs.MAX_SIZE_PERCENT_DISK_CACHE)
        .minimumMaxSizeBytes(CoilImageLoaderConfigs.MINIMUM_MAX_SIZE_BYTES_DISK_CACHE)
        .maximumMaxSizeBytes(CoilImageLoaderConfigs.MAXIMUM_MAX_SIZE_BYTES_DISK_CACHE)
        .build()
    }.build()

  @Provides
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient
    .Builder()
    .readTimeout(OK_HTTP_CLIENT_READ_TIMEOUT)
    .writeTimeout(OK_HTTP_CLIENT_WRITE_TIMEOUT)
    .connectTimeout(OK_HTTP_CLIENT_CONNECT_TIMEOUT)
    .addInterceptor(httpLoggingInterceptor)
    .build()

  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()

    interceptor.level =
      HttpLoggingInterceptor.Level.BODY

    return interceptor
  }

  @Provides
  fun provideBarCodeScan(): BarcodeScanner {
    val options = BarcodeScannerOptions.Builder().build()
    return BarcodeScanning.getClient(options)
  }

  @Provides
  @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .add(KotlinJsonAdapterFactory())
    .build()

  @Singleton
  @Provides
  fun sharedPreference(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
    SharePreferenceProvider.NAME_SHARE_PREFERENCE,
    Context.MODE_PRIVATE,
  )


  companion object {
    private object CoilImageLoaderConfigs {
      const val MAX_SIZE_PERCENT_DISK_CACHE = 0.02 // 2% of the device's free disk space
      const val MINIMUM_MAX_SIZE_BYTES_DISK_CACHE = 20L * 1024 * 1024 // 20MB min
      const val MAXIMUM_MAX_SIZE_BYTES_DISK_CACHE = 250L * 1024 * 1024 // 250MB max

      val OK_HTTP_CLIENT_READ_TIMEOUT = Duration.ofSeconds(30)!!
      val OK_HTTP_CLIENT_WRITE_TIMEOUT = Duration.ofSeconds(30)!!
      val OK_HTTP_CLIENT_CONNECT_TIMEOUT = Duration.ofSeconds(30)!!
    }
  }
}
