package ptit.vietpq.qr_scanner.data.di

import ptit.vietpq.qr_scanner.data.repository.BarCodeResultRepositoryImpl
import ptit.vietpq.qr_scanner.data.repository.FileCacheBitmapRepositoryImpl
import ptit.vietpq.qr_scanner.data.repository.ProcessImageBarCodeRepositoryImpl
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.BarCodeResultRepository
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.FileCacheBitmapRepository
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.repository.ProcessImageBarCodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
  @Binds
  fun bindProcessImageBarCodeRepository(repository: ProcessImageBarCodeRepositoryImpl): ProcessImageBarCodeRepository

  @Binds
  fun bindFileCacheRepository(repository: FileCacheBitmapRepositoryImpl): FileCacheBitmapRepository

  @Binds
  fun bindBarCodeResultRepository(repository: BarCodeResultRepositoryImpl): BarCodeResultRepository
}
