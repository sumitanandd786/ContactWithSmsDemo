package com.contactwithsmsdemo.model

public class Sms {
    @JvmField var _id: String? = ""
    @JvmField var _personName: String? = ""
    @JvmField var _address: String? = ""
    @JvmField var _msg: String? = ""

    @JvmField var _readState: String? = "" //"0" for have not read sms and "1" for have read sms
    @JvmField var _time: String? = ""
    @JvmField var _folderName: String? = ""

    fun setId(valueId: String) {
        _id = valueId
    }

    fun setPersonName(valueName: String) {
        _personName = valueName
    }

    fun setAddress(valueAddress: String) {
        _address = valueAddress
    }
    fun setMsg(valueMsg: String) {
        _msg = valueMsg
    }
    fun setReadState(valueReadState: String) {
        _readState = valueReadState
    }
    fun setTime(valueTime: String) {
        _time = valueTime
    }
    fun setFolderName(valueFolderName: String) {
        _folderName = valueFolderName
    }

    fun getId(): String {
        return _id.toString()
    }

    fun getPersonName(): String {
        return _personName.toString()
    }

    fun getAddress(): String {
        return _address.toString()
    }

    fun getMsg(): String {
        return _msg.toString()
    }

    fun getReadState(): String {
        return _readState.toString()
    }

    fun getTime(): String {
        return _time.toString()
    }

    fun getFolderName(): String {
        return _folderName.toString()
    }

}