package com.yjotdev.login.domain.usecase.string

import javax.inject.Inject
import com.yjotdev.login.domain.repository.StringRepository

class GetStringUseCase @Inject constructor(
    private val stringRepository: StringRepository
) {
    /** Obtener string mediante caso de uso **/
    operator fun invoke(resId: Int): String {
        return stringRepository.getString(resId)
    }

    /** Obtener string con argumentos mediante caso de uso **/
    operator fun invoke(resId: Int, vararg args: Any): String {
        return stringRepository.getString(resId, *args)
    }
}