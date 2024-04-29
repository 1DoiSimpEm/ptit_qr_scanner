package ptit.vietpq.qr_scanner.data.di

import android.content.Context
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.data.datalocal.AppQrDataBase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.asExecutor

@Module
@InstallIn(SingletonComponent::class)
object RoomDataBaseModule {

  @Provides
  @Singleton
  fun provideRoomDb(
      @ApplicationContext appContext: Context,
      moshi: Moshi,
      appCoroutineDispatchers: AppCoroutineDispatchers,
  ): AppQrDataBase = AppQrDataBase.getInstance(
    context = appContext,
    moshi = moshi,
    queryExecutor = appCoroutineDispatchers.io.asExecutor(),
  )
}
