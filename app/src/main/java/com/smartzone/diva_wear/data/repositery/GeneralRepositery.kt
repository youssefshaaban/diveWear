package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.*
import com.smartzone.diva_wear.utilis.FileUtils
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GeneralRepositery(private val apiHelper: APIHelper) {
    fun getCity(): Single<ResponseCity> {
        return apiHelper.getCity()
    }

    fun sendFeadBack(mobile:String,name:String,message:String): Single<ResponseSendFedback> {
        return apiHelper.sendFeedBack(mobile,name,message)
    }

    fun uploadImage(files: ArrayList<File>): Single<ResponseUploadImage> {
        val list = ArrayList<MultipartBody.Part>()
        for ((index, value) in files.withIndex()) {
            val filePart =
                createRequestForFile("parameters[$index]", value, FileUtils.getMimeType(value)!!)
            list.add(filePart)
        }
        return apiHelper.uploadFiles(list)
    }

    private fun createRequestForFile(
        partName: String,
        file: File,
        fileType: String
    ): MultipartBody.Part {
        // create RequestBody instance from file
        val requestFile = RequestBody.create(
            MediaType.parse(fileType),
            file
        )
        // MultipartBody.Part is used to send also the actual file name

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun getSetting(): Single<ResponseSetting> {
        return apiHelper.getSettings()
    }
}