package ptit.vietpq.qr_scanner.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ptit.vietpq.qr_scanner.core.AppCoroutineDispatchers
import ptit.vietpq.qr_scanner.core.AppCoroutineScope
import ptit.vietpq.qr_scanner.core.DefaultAppCoroutineDispatchers
import ptit.vietpq.qr_scanner.core.DefaultAppCoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreModule {
  @Binds
  @Singleton
  fun appCoroutineDispatchers(impl: DefaultAppCoroutineDispatchers): AppCoroutineDispatchers

  @Binds
  @Singleton
  fun appCoroutineScope(impl: DefaultAppCoroutineScope): AppCoroutineScope
}
