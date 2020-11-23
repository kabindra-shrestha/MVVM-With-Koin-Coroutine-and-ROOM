package com.kabindra.sample.repository.user

import com.kabindra.sample.db.model.UserData
import com.kabindra.sample.util.apiHandle.ApiResult

interface UserRepository {
    suspend fun getUser(): ApiResult<List<UserData>>
}
