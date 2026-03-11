package com.nonggle.common.download

import android.content.Context
import java.io.File
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.io.FileInputStream
import java.io.IOException

object DownloadFileSaver {

    sealed interface SaveToDownloadsResult {
        data class Success(
            val uri: Uri?,
            val absolutePath: String?
        ) : SaveToDownloadsResult

        data class Error(
            val message: String,
            val throwable: Throwable? = null
        ) : SaveToDownloadsResult
    }

    /**
     * file: 이미 로컬에 생성된 파일
     * displayName: Downloads 폴더에 저장될 파일명 (예: "resume.pdf")
     * mimeType: 예: "application/pdf"
     */
    fun saveFileToDownloads(
        context: Context,
        file: File,
        displayName: String,
        mimeType: String = "application/pdf",
    ): SaveToDownloadsResult {
        if (!file.exists() || !file.isFile) {
            return SaveToDownloadsResult.Error("저장할 파일이 존재하지 않거나 유효하지 않습니다.")
        }

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveUsingMediaStore(
                    context = context,
                    file = file,
                    displayName = displayName,
                    mimeType = mimeType
                )
            } else {
                saveToPublicDownloads(
                    context = context,
                    file = file,
                    displayName = displayName
                )
            }
        } catch (e: Exception) {
            SaveToDownloadsResult.Error(
                message = "다운로드 폴더 저장 중 오류가 발생했습니다.",
                throwable = e
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveUsingMediaStore(
        context: Context,
        file: File,
        displayName: String,
        mimeType: String
    ): SaveToDownloadsResult {
        val resolver = context.contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val itemUri = resolver.insert(collection, contentValues)
            ?: return SaveToDownloadsResult.Error("MediaStore 항목 생성에 실패했습니다.")

        try {
            resolver.openOutputStream(itemUri)?.use { outputStream ->
                FileInputStream(file).use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            } ?: return SaveToDownloadsResult.Error("출력 스트림을 열 수 없습니다.")

            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(itemUri, contentValues, null, null)

            return SaveToDownloadsResult.Success(
                uri = itemUri,
                absolutePath = null
            )
        } catch (e: Exception) {
            resolver.delete(itemUri, null, null)
            return SaveToDownloadsResult.Error(
                message = "MediaStore 저장 중 오류가 발생했습니다.",
                throwable = e
            )
        }
    }

    // Android 9 이하에서 다운로드 기능 사용을 위해
    private fun saveToPublicDownloads(
        context: Context,
        file: File,
        displayName: String
    ): SaveToDownloadsResult {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            return SaveToDownloadsResult.Error(
                "Downloads 폴더 저장을 위해 외부 저장소 읽기 권한이 필요합니다."
            )
        }

        val downloadsDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )

        if (!downloadsDir.exists() && !downloadsDir.mkdirs()) {
            return SaveToDownloadsResult.Error("Downloads 폴더를 생성할 수 없습니다.")
        }

        val destinationFile = File(downloadsDir, displayName)

        return try {
            FileInputStream(file).use { inputStream ->
                destinationFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            SaveToDownloadsResult.Success(
                uri = Uri.fromFile(destinationFile),
                absolutePath = destinationFile.absolutePath
            )
        } catch (e: IOException) {
            SaveToDownloadsResult.Error(
                message = "Downloads 폴더 복사 중 오류가 발생했습니다.",
                throwable = e
            )
        }
    }
}