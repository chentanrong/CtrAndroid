package ctr.common.service

import android.os.Environment
import java.io.File

/**
 * Created by Sunjie on 2017/12/11.
 */

open class CommonPathService  {
    companion object {
        const val DIRECTORY_YLT = "ctr"
        const val DIRECTORY_APP = "custum"
        const val DIRECTORY_TASK = "task"
        const val DIRECTORY_DB = "database"
        const val DIRECTORY_LIC = "license"
        const val DIRECTORY_CONFIG = "config"
        const val DIRECTORY_LOG = "log"
        const val DIRECTORY_BACKUP = "backup"
        const val DIRECTORY_EXPORT = "export"
        const val DIRECTORY_PHOTO = "photo"
        const val DIRECTORY_LOCATION = "location"
        const val FILE_LIC = "yltpad.lic"
        const val FILE_DB = "data.sqlite"
        const val PROJECT_PHOTO = "task"
        const val TEMPLATE = "template"
        const val CONFIG_DB= "config.db"
        const val TPADCOMMON_YLTMD= "tPadCommon1.yltmd"
    }

    open fun getOrMakeDir(file: File): File {
        return if (file.exists() || file.mkdirs()) {
            file
        } else file
    }

    open val YLTDir: File
        get() {
            return getOrMakeDir(File(Environment.getExternalStorageDirectory(), DIRECTORY_YLT))
        }

    open val appDir: File
        get() = getOrMakeDir(File(YLTDir, DIRECTORY_APP))

    open val projectDir: File //yulintu -> packageName -> database
        get() {
            return getOrMakeDir(File(appDir, DIRECTORY_DB))
        }
    open val databaseDir: File //yulintu -> packageName -> database -> 具体某个任务包名
        get() {
            return getOrMakeDir(File(projectDir,DIRECTORY_TASK))
        }
    open val licenseDir: File
        get() {
            return getOrMakeDir(File(appDir, DIRECTORY_LIC))
        }
    open val logDir: File
        get() {
            return getOrMakeDir(File(appDir, DIRECTORY_LOG))
        }
    open val locationDir: File
        get() {
            return getOrMakeDir(File(appDir, DIRECTORY_LOCATION))
        }

    open val licenseFile: File
        get() {
            return File(licenseDir, FILE_LIC)
        }
    open val databaseFile: File
        get() {
            return File(databaseDir, FILE_DB)
        }


    open val photoDir: File //yulintu -> packageName -> database -> 具体某个任务包名 -> photo
        get() {
            return getOrMakeDir(File(databaseDir, DIRECTORY_PHOTO))
        }


    open val projectPhotoDir: File //yulintu -> packageName -> database -> 具体某个任务包名 -> photo ->projectPhoto
        get() {
            return getOrMakeDir(File(photoDir, PROJECT_PHOTO))
        }
}