package com.example.daisyling.model.protocol

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Solve the problem of GsonConverterFactory parsing exception.
 * Created by Emily on 9/30/21
 */
class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return label@ Converter { body: ResponseBody ->
            if (body.contentLength() == 0L)
                return@Converter null
            delegate.convert(body)
        } as Converter<ResponseBody, Any?>
    }
}