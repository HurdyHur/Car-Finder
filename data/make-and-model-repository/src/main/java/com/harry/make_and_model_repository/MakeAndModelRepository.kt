package com.harry.make_and_model_repository

import com.harry.make_and_model_repository.model.Make

interface MakeAndModelRepository {

    fun getMakes(): List<Make>

}
