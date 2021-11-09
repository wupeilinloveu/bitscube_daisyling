package com.example.local_music.data.base

import com.example.local_music.data.constant.MessageType

/**
 * Created by Emily on 11/1/21
 */
class BaseBean {
    var messageType: MessageType
    var msg: String? = null

    constructor(messageType: MessageType, msg: String?) {
        this.messageType = messageType
        this.msg = msg
    }

    constructor(messageType: MessageType) {
        this.messageType = messageType
    }
}