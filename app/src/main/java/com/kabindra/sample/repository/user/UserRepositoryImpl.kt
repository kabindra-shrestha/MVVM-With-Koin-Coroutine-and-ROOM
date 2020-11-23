package com.kabindra.sample.repository.user

import android.content.Context
import com.kabindra.sample.SampleApi
import com.kabindra.sample.db.UserDao
import com.kabindra.sample.db.model.UserData
import com.kabindra.sample.util.NetworkManager.isOnline
import com.kabindra.sample.util.TAG
import com.kabindra.sample.util.apiHandle.ApiHandle.apiHandleError
import com.kabindra.sample.util.apiHandle.ApiHandle.apiHandleSuccess
import com.kabindra.sample.util.apiHandle.ApiResult
import com.kabindra.sample.util.loggerDebug
import com.kabindra.sample.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: SampleApi,
    private val context: Context,
    private val dao: UserDao
) :
    UserRepository {

    override suspend fun getUser(): ApiResult<List<UserData>> {
        if (isOnline(context)) {
            return try {
                val response = api.getUser()
                if (response.isSuccessful) {
                    //save the data
                    response.body()?.let {
                        withContext(Dispatchers.IO) {
                            dao.deleteAllUser()
                            dao.add(it)
                        }
                    }
                    apiHandleSuccess(response)
                } else {
                    apiHandleError(response)
                }
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        } else {
            //check in db if the data exists
            val data = getUserDataFromDatabase()
            return if (data.isNotEmpty()) {
                loggerDebug(TAG + "from db")
                withContext(Dispatchers.IO) {
                    dao.deleteAllUser()
                    dao.add(data)
                }
                ApiResult.Success(data)
            } else
            //no network
                context.noNetworkConnectivityError()
        }
    }

    private suspend fun getUserDataFromDatabase(): List<UserData> {
        return withContext(Dispatchers.IO) {
            dao.findAll()
        }
    }

/*
This is another way of implementing where the source of data is db and api but we can always fetch from db
which will be updated with the latest data from api and also change findAll() return type to
LiveData<List<UserData>>
*/
    /* val data = dao.findAll()

     suspend fun getAllUserData() {
         withContext(Dispatchers.IO) {
             val response = api.getAllUser()
             response.body()?.let {
                 withContext(Dispatchers.IO) { dao.add(it) }
             }
         }
     }*/

}