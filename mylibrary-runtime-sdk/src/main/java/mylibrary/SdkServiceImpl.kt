/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mylibrary

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@RequiresApi(Build.VERSION_CODES.O)
class SdkServiceImpl(private val context: Context) : SdkService {
    override suspend fun getMessage(): String = "Hello from Privacy Sandbox!"

    override suspend fun createFile(sizeInMb: Int): String {
        val path = Paths.get(
            context.applicationContext.dataDir.path, "file.txt"
        )
        withContext(Dispatchers.IO) {
            Files.deleteIfExists(path)
            Files.createFile(path)
            val buffer = ByteArray(sizeInMb * 1024 * 1024)
            Files.write(path, buffer)
        }

        val file = File(path.toString())
        val actualFileSize: Long = file.length() / (1024 * 1024)
        return "Created $actualFileSize MB file successfully"
    }

    override suspend fun getSuperBanner(request: SdkBannerRequest) =
        SdkSandboxedUiAdapterImpl(context, request)
}
