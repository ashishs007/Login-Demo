package com.login.api.response


class Resource<T>(status: Status, data: T?, message: String?) {

    var status: Status = Status.IDLE
    var message: String? = ""
    var data: T? = null
    init {
        this.status = status
        this.message = message
        this.data = data
    }
    fun <T> success(data: T?): Resource<T?> {
        return Resource(Status.SUCCESS, data, "")
    }

    fun <T> error(msg: String?, data: T?): Resource<T?> {
        return Resource(Status.ERROR, data, msg)
    }

    fun <T> loading(data: T?): Resource<T?> {
        return Resource(Status.LOADING, data, "")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val resource = other as Resource<*>?
        if (status !== resource!!.status) {
            return false
        }
        if (if (message != null) message != resource!!.message else resource!!.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + if (data != null) data!!.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}
